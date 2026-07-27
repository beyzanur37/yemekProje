package com.example.yemekproje;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> favoriteList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView txtEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        
        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        txtEmpty = view.findViewById(R.id.txtEmptyFavorites);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteList = new ArrayList<>();
        adapter = new RecipeAdapter(favoriteList, null);
        recyclerView.setAdapter(adapter);

        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        if (mAuth.getCurrentUser() == null) return;

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("favorites")
                .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        favoriteList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            favoriteList.add(doc.toObject(Recipe.class));
                        }
                        adapter.notifyDataSetChanged();
                        txtEmpty.setVisibility(favoriteList.isEmpty() ? View.VISIBLE : View.GONE);
                    }
                });
    }
}