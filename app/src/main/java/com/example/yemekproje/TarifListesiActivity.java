package com.example.yemekproje;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Bu sınıf, tariflerin kategorilere göre veya malzeme eşleşmesine göre listelendiği ekrandır.
 */
public class TarifListesiActivity extends AppCompatActivity {

    private RecipeAdapter adapter; // Listeyi yöneten aracı (adapter)
    private List<Recipe> fullRecipeList; // Tüm tariflerin tutulduğu ana liste
    private FirebaseFirestore db; // Veritabanı bağlantısı
    private ArrayList<String> selectedMaterials; // Malzeme seçme ekranından gelen malzemeler
    private Locale tr = new Locale("tr", "TR"); // Türkçe karakter desteği için yerel ayar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Firebase başlatılıyor
        db = FirebaseFirestore.getInstance();
        
        // UI elemanları tanımlanıyor
        TextView txtTitle = findViewById(R.id.txtCategoryTitle);
        SearchView searchView = findViewById(R.id.searchViewRecipes);
        
        // Intent ile gelen veriler alınıyor (Malzeme listesi veya Kategori adı)
        selectedMaterials = getIntent().getStringArrayListExtra("selected_materials");
        String category = getIntent().getStringExtra("categoryName");

        // Başlık ayarlanıyor: Eğer malzeme seçilmişse "Sana Uygun", kategori seçilmişse o kategorinin adı
        if (selectedMaterials != null) {
            txtTitle.setText("Sana Uygun Tarifler");
        } else if (category != null) {
            txtTitle.setText(category + " Tarifleri");
        }

        // RecyclerView (Liste yapısı) ayarları
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fullRecipeList = new ArrayList<>();
        // Adapter bağlanıyor (Listener null: Varsayılan olarak detay sayfasını açar)
        adapter = new RecipeAdapter(fullRecipeList, null);
        recyclerView.setAdapter(adapter);

        // Veri yükleme mantığı: Malzeme seçilmişse eşleştirme algoritmasını çalıştır, yoksa kategoriye göre getir
        if (selectedMaterials != null) {
            loadMatchingRecipes();
        } else {
            loadCategoryRecipes(category);
        }

        // Arama çubuğu dinleyicisi: Harf yazıldıkça listeyi filtreler
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText); // Filtreleme fonksiyonunu çağır
                return true;
            }
        });
    }

    /**
     * Malzeme seçimine göre tarifleri Firebase ve statik verilerden çekip puanlayan metod.
     */
    private void loadMatchingRecipes() {
        // Hem kod içindeki (static) hem de Firebase'deki tarifleri birleştirir
        List<Recipe> allRecipes = new ArrayList<>(RecipeData.getStaticRecipes());
        
        db.collection("all_recipes").get().addOnSuccessListener(snaps -> {
            for (QueryDocumentSnapshot doc : snaps) {
                Recipe r = doc.toObject(Recipe.class);
                // Sadece kullanıcıların eklediği (UserId olan) tarifleri ekle (Duplicate önlemek için)
                if (r.getUserId() == null || r.getUserId().isEmpty()) continue;
                
                boolean exists = false;
                for(Recipe sr : allRecipes) {
                    if (sr.getName().equalsIgnoreCase(r.getName())) { exists = true; break; }
                }
                if (!exists) allRecipes.add(r);
            }
            
            // Puanlama algoritması başlar
            List<ScoredRecipe> list = new ArrayList<>();
            for (Recipe r : allRecipes) {
                double score = calculateScore(r, selectedMaterials); // Uyumluluk puanı hesapla
                if (score >= 50.0) list.add(new ScoredRecipe(r, score)); // %50 ve üstü uyumluysa ekle
            }
            
            // Yüksek puanlı olanı en üste getir (Sort)
            list.sort((a, b) -> Double.compare(b.score, a.score));
            
            fullRecipeList.clear();
            for (ScoredRecipe sr : list) fullRecipeList.add(sr.recipe);
            adapter.notifyDataSetChanged(); // Listeyi güncelle
        });
    }

    /**
     * Tarifin içindeki malzemelerle kullanıcının seçtiklerini kıyaslayıp puan üretir.
     */
    private double calculateScore(Recipe r, List<String> items) {
        int total = 0, match = 0;
        List<String> missingList = new ArrayList<>();
        
        // Malzeme kaynağını belirle (Ana malzeme öncelikli)
        String searchSource = r.getMainIngredient();
        if (searchSource == null || searchSource.isEmpty()) {
            searchSource = r.getIngredients(); 
        }
        
        if (searchSource == null) return 0;

        // Malzemeleri virgülle ayırıp tek tek kontrol et
        String[] ing = searchSource.split(",");
        for(String p : ing) {
            String pClean = p.trim().toLowerCase(tr);
            if(pClean.isEmpty()) continue;
            total++;
            boolean found = false;
            for(String s : items) { 
                if(isExact(pClean, s)) { match++; found = true; break; } 
            }
            // Bulunamazsa "Eksik Malzemeler" listesine ekle
            if (!found) missingList.add(p.trim());
        }
        
        // Eksik malzemeleri tarif nesnesine set et (UI'da gösterilmek üzere)
        if (!missingList.isEmpty()) {
            r.setMissingIngredients(android.text.TextUtils.join(", ", missingList));
        } else {
            r.setMissingIngredients(null);
        }
        
        if (total == 0) return 0;
        return ((double) match / total) * 100; // Başarı yüzdesini dön
    }

    /**
     * İki kelimenin birbirini kapsayıp kapsamadığını kontrol eden yardımcı metod.
     */
    private boolean isExact(String source, String target) {
        if (source.equals(target)) return true;
        
        // Noktalama işaretlerini temizle
        String sClean = source.replaceAll("[.,;!?]", " ");
        String tClean = target.replaceAll("[.,;!?]", " ");
        
        // Boşluklu kontrol yaparak "un" kelimesinin "sucuk" içinde geçmesini engelle
        String sPadded = " " + sClean + " ";
        String tPadded = " " + tClean + " ";
        
        return sPadded.contains(tPadded);
    }

    /**
     * Seçilen kategoriye göre tarifleri yükler.
     */
    private void loadCategoryRecipes(String category) {
        List<Recipe> allRecipes = new ArrayList<>();
        // Önce statik (kod içindeki) tariflerden kategoriye uyanları al
        if (category != null) {
            String cleanCat = category.trim();
            if (cleanCat.equalsIgnoreCase("Çorbalar")) allRecipes.addAll(RecipeData.getCorbalar());
            else if (cleanCat.equalsIgnoreCase("Ana Yemekler")) allRecipes.addAll(RecipeData.getAnaYemekler());
            else if (cleanCat.equalsIgnoreCase("Salatalar")) allRecipes.addAll(RecipeData.getSalatalar());
            else if (cleanCat.equalsIgnoreCase("Ara Sıcaklar")) allRecipes.addAll(RecipeData.getAraSicaklar());
            else if (cleanCat.equalsIgnoreCase("Tatlılar")) allRecipes.addAll(RecipeData.getTatlilar());
        }
        
        // Sonra Firebase'den o kategoriye ait tarifleri çek
        db.collection("all_recipes").get().addOnSuccessListener(snaps -> {
            for (QueryDocumentSnapshot doc : snaps) {
                Recipe r = doc.toObject(Recipe.class);
                if (r.getUserId() == null || r.getUserId().isEmpty()) continue;
                
                if (category == null || (r.getCategory() != null && r.getCategory().equalsIgnoreCase(category))) {
                    boolean exists = false;
                    for(Recipe sr : allRecipes) {
                        if (sr.getName().equalsIgnoreCase(r.getName())) { exists = true; break; }
                    }
                    if (!exists) allRecipes.add(r);
                }
            }
            fullRecipeList.clear();
            fullRecipeList.addAll(allRecipes);
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * Arama yapıldığında listeyi anlık olarak daraltan filtreleme fonksiyonu.
     */
    private void filter(String text) {
        List<Recipe> filtered = new ArrayList<>();
        for (Recipe r : fullRecipeList) {
            if (r.getName().toLowerCase(tr).contains(text.toLowerCase(tr))) filtered.add(r);
        }
        adapter.filterList(filtered);
    }

    // Skorlu tarifleri tutmak için küçük bir yardımcı sınıf
    private static class ScoredRecipe { 
        Recipe recipe; 
        double score; 
        ScoredRecipe(Recipe r, double s) { this.recipe = r; this.score = s; } 
    }
}