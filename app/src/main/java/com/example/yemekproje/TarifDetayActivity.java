package com.example.yemekproje;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bu sınıf, seçilen bir tarifin tüm detaylarını gösteren ekrandır.
 * Porsiyon hesaplama, sesli okuma ve zamanlayıcı gibi akıllı özellikleri barındırır.
 */
public class TarifDetayActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private RecyclerView recyclerComments;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private EditText editComment;
    private ImageButton btnSend, btnSpeak, btnMinus, btnPlus;
    private TextView txtNoComments, txtPortionCount, txtIngredients, txtInstructions, txtTitle;
    private ImageView imgFood;
    private Button btnWatchVideo, btnAddShopping;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    
    private String yemekAdi, orijinalMalzemeler, orijinalHazirlanis;
    private TextToSpeech tts; // Sesli okuma nesnesi
    private int currentPortion = 4; // Varsayılan porsiyon sayısı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarif_detay);

        // Firebase ve TTS (Sesli Okuma) başlatılıyor
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        tts = new TextToSpeech(this, this);

        // Geri butonu ekle
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Tarif Detayı");
        }

        initViews(); // Tasarım elemanlarını bağla
        getIntentData(); // Önceki ekrandan gelen verileri al ve yerleştir
    }

    private void initViews() {
        txtTitle = findViewById(R.id.txtDetailTitle);
        imgFood = findViewById(R.id.imgDetailFood);
        txtIngredients = findViewById(R.id.txtDetailIngredients);
        txtInstructions = findViewById(R.id.txtDetailInstructions);
        txtPortionCount = findViewById(R.id.txtPortionCount);
        txtNoComments = findViewById(R.id.txtNoComments);
        btnWatchVideo = findViewById(R.id.btnWatchVideo);
        btnSpeak = findViewById(R.id.btnSpeak);
        btnMinus = findViewById(R.id.btnPortionMinus);
        btnPlus = findViewById(R.id.btnPortionPlus);
        btnAddShopping = findViewById(R.id.btnAddToShoppingList);
        recyclerComments = findViewById(R.id.recyclerComments);
        editComment = findViewById(R.id.editComment);
        btnSend = findViewById(R.id.btnSendComment);
    }

    /**
     * Önceki ekrandan (Adapter) gelen tarif bilgilerini Intent üzerinden çeker.
     */
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent == null) return;

        yemekAdi = intent.getStringExtra("recipe_name");
        orijinalMalzemeler = intent.getStringExtra("recipe_ingredients");
        orijinalHazirlanis = intent.getStringExtra("recipe_instructions");
        String imageUrl = intent.getStringExtra("recipe_image_url");
        String videoUrl = intent.getStringExtra("recipe_video_url");
        String drawableName = intent.getStringExtra("recipe_drawable_name");

        if (yemekAdi == null) yemekAdi = "Bilinmeyen Tarif";

        // Yazıları set et
        if (txtTitle != null) txtTitle.setText(yemekAdi);
        if (txtIngredients != null) txtIngredients.setText(orijinalMalzemeler != null ? orijinalMalzemeler : "Malzeme bilgisi yok.");
        if (txtInstructions != null) txtInstructions.setText(orijinalHazirlanis != null ? orijinalHazirlanis : "Hazırlanış bilgisi yok.");

        // --- RESİM YÜKLEME ---
        if (imgFood != null) {
            try {
                if (drawableName != null && !drawableName.isEmpty()) {
                    int resId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
                    imgFood.setImageResource(resId != 0 ? resId : R.drawable.ic_launcher_background);
                } else if (imageUrl != null && !imageUrl.isEmpty()) {
                    if (imageUrl.length() > 200) {
                        byte[] decoded = Base64.decode(imageUrl, Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                        imgFood.setImageBitmap(bmp);
                    } else {
                        Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_launcher_background).into(imgFood);
                    }
                }
            } catch (Exception e) {
                imgFood.setImageResource(R.drawable.ic_launcher_background);
            }
        }

        // Video butonu görünürlüğü
        if (videoUrl != null && !videoUrl.isEmpty() && btnWatchVideo != null) {
            btnWatchVideo.setVisibility(View.VISIBLE);
            btnWatchVideo.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))));
        }

        // Diğer butonların dinleyicileri
        if (btnSpeak != null) btnSpeak.setOnClickListener(v -> speakRecipe());
        if (btnAddShopping != null) btnAddShopping.setOnClickListener(v -> addToShoppingList());
        
        setupPortionControls(); // Porsiyon düğmelerini kur
        setupTimerFeature(); // Akıllı zamanlayıcıyı kur
        setupComments(); // Yorum sistemini kur
    }

    /**
     * Porsiyon artı/eksi butonlarının mantığını yönetir.
     */
    private void setupPortionControls() {
        if (btnPlus != null) btnPlus.setOnClickListener(v -> { currentPortion++; updatePortionUI(); });
        if (btnMinus != null) btnMinus.setOnClickListener(v -> { if (currentPortion > 1) { currentPortion--; updatePortionUI(); } });
    }

    /**
     * Porsiyon değiştiğinde malzeme miktarlarını yeniden hesaplar.
     */
    private void updatePortionUI() {
        if (txtPortionCount != null) txtPortionCount.setText(String.valueOf(currentPortion));
        if (txtIngredients != null && orijinalMalzemeler != null) {
            float ratio = (float) currentPortion / 4.0f; // 4 kişiliğe göre oranla
            txtIngredients.setText(scaleIngredients(orijinalMalzemeler, ratio));
        }
    }

    /**
     * Metin içindeki sayıları Regex ile bulup verilen oranda çarpar.
     */
    private String scaleIngredients(String text, float ratio) {
        if (text == null) return "";
        StringBuilder sb = new StringBuilder();
        Pattern p = Pattern.compile("(\\d+(\\.\\d+)?)"); // Sayıları bulmak için Regex
        for (String line : text.split("\n")) {
            Matcher m = p.matcher(line);
            if (m.find()) {
                try {
                    float val = Float.parseFloat(m.group(1));
                    line = line.replaceFirst(Pattern.quote(m.group(1)), String.format(Locale.US, "%.1f", val * ratio));
                } catch (Exception e) {}
            }
            sb.append(line).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Hazırlanış metni içindeki "dk" veya "dakika" yazılarını tıklanabilir yapar
     * ve tıklandığında telefonun zamanlayıcısını (timer) ayarlar.
     */
    private void setupTimerFeature() {
        if (txtInstructions != null && orijinalHazirlanis != null) {
            txtInstructions.setOnClickListener(v -> {
                Pattern p = Pattern.compile("(\\d+)\\s*(dakika|dk)");
                Matcher m = p.matcher(orijinalHazirlanis);
                if (m.find()) {
                    int minutes = Integer.parseInt(m.group(1));
                    new AlertDialog.Builder(this)
                            .setTitle("Zamanlayıcı")
                            .setMessage(minutes + " dakika kurulsun mu?")
                            .setPositiveButton("Evet", (d, w) -> {
                                Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                                        .putExtra(AlarmClock.EXTRA_LENGTH, minutes * 60)
                                        .putExtra(AlarmClock.EXTRA_SKIP_UI, false);
                                startActivity(intent);
                            }).setNegativeButton("Hayır", null).show();
                }
            });
        }
    }

    /**
     * Text-To-Speech (TTS) kullanarak yemeğin adını sesli okur.
     */
    private void speakRecipe() {
        if (tts != null && yemekAdi != null) {
            tts.speak("Okunan Tarif: " + yemekAdi, TextToSpeech.QUEUE_FLUSH, null, "TTS");
        }
    }

    /**
     * Yemeği ve malzemelerini kullanıcının "Alışveriş Listesi" koleksiyonuna ekler.
     */
    private void addToShoppingList() {
        if (mAuth.getCurrentUser() == null) return;
        db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("shopping_list")
                .add(new ShoppingItem(yemekAdi, orijinalMalzemeler, System.currentTimeMillis()))
                .addOnSuccessListener(v -> Toast.makeText(this, "Alışveriş listesine eklendi!", Toast.LENGTH_SHORT).show());
    }

    // TTS dili Türkçe olarak ayarlanıyor
    @Override public void onInit(int status) { if (status == TextToSpeech.SUCCESS) tts.setLanguage(new Locale("tr", "TR")); }

    /**
     * Firebase Firestore'dan bu yemeğe ait yorumları anlık olarak çeker.
     */
    private void setupComments() {
        if (recyclerComments == null) return;
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerComments.setAdapter(commentAdapter);
        loadComments();
        
        // Yorum gönderme butonu
        if (btnSend != null) {
            btnSend.setOnClickListener(v -> {
                String text = editComment.getText().toString().trim();
                if (!text.isEmpty() && yemekAdi != null) {
                    db.collection("all_recipes").document(yemekAdi).collection("comments").add(new Comment("Kullanıcı", text, System.currentTimeMillis()));
                    editComment.setText("");
                }
            });
        }
    }

    private void loadComments() {
        if (yemekAdi == null) return;
        db.collection("all_recipes").document(yemekAdi).collection("comments").orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((v, e) -> {
                    if (v != null) {
                        commentList.clear();
                        for (QueryDocumentSnapshot d : v) commentList.add(d.toObject(Comment.class));
                        commentAdapter.notifyDataSetChanged();
                        if (txtNoComments != null) txtNoComments.setVisibility(commentList.isEmpty() ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override protected void onDestroy() { if (tts != null) { tts.stop(); tts.shutdown(); } super.onDestroy(); }
    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) { if (item.getItemId() == android.R.id.home) onBackPressed(); return true; }

    // Alışveriş listesi öğesi için veri modeli
    public static class ShoppingItem {
        public String recipeName, ingredients; public long timestamp;
        public ShoppingItem() {}
        public ShoppingItem(String r, String i, long t) { this.recipeName = r; this.ingredients = i; this.timestamp = t; }
    }
}