package com.example.yemekproje;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TarifListesiFragment extends Fragment {

    private RecipeAdapter adapter;
    private List<Recipe> fullRecipeList;
    private String category;
    private FirebaseFirestore db;

    public static TarifListesiFragment newInstance(String categoryName) {
        TarifListesiFragment fragment = new TarifListesiFragment();
        Bundle args = new Bundle();
        args.putString("categoryName", categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("categoryName");
        }
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recipe_list, container, false);

        TextView txtTitle = view.findViewById(R.id.txtCategoryTitle);
        SearchView searchView = view.findViewById(R.id.searchViewRecipes);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewRecipes);

        if (category != null) {
            txtTitle.setText(category + " Tarifleri");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fullRecipeList = new ArrayList<>();
        
        adapter = new RecipeAdapter(fullRecipeList, null);
        recyclerView.setAdapter(adapter);

        loadStaticRecipes();
        loadUserRecipesFromFirebase();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return view;
    }

    private void loadUserRecipesFromFirebase() {
        if (category == null) return;
        db.collection("all_recipes").whereEqualTo("category", category).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Recipe firebaseRecipe = document.toObject(Recipe.class);
                            if (firebaseRecipe.getUserId() == null || firebaseRecipe.getUserId().isEmpty()) continue;
                            
                            boolean exists = false;
                            for(Recipe r : fullRecipeList) {
                                if(r.getName().equalsIgnoreCase(firebaseRecipe.getName())) {
                                    exists = true;
                                    break;
                                }
                            }
                            if(!exists) fullRecipeList.add(firebaseRecipe);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadStaticRecipes() {
        if (category == null) return;
        String cleanCat = category.trim();
        if (cleanCat.equalsIgnoreCase("Çorbalar")) fullRecipeList.addAll(RecipeData.getCorbalar());
        else if (cleanCat.equalsIgnoreCase("Ana Yemekler")) fullRecipeList.addAll(RecipeData.getAnaYemekler());
        else if (cleanCat.equalsIgnoreCase("Salatalar")) fullRecipeList.addAll(RecipeData.getSalatalar());
        else if (cleanCat.equalsIgnoreCase("Ara Sıcaklar")) fullRecipeList.addAll(RecipeData.getAraSicaklar());
        else if (cleanCat.equalsIgnoreCase("Tatlılar")) fullRecipeList.addAll(RecipeData.getTatlilar());
        adapter.notifyDataSetChanged();
    }

    private void filter(String text) {
        List<Recipe> filteredList = new ArrayList<>();
        Locale tr = Locale.forLanguageTag("tr");
        for (Recipe item : fullRecipeList) {
            if (item.getName().toLowerCase(tr).contains(text.toLowerCase(tr))) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}
