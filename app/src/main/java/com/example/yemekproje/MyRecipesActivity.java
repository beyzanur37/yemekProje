package com.example.yemekproje;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class MyRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecipesAdapter adapter;
    private List<Recipe> myRecipeList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbarMyRecipes);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Benim Tariflerim");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerMyRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecipeList = new ArrayList<>();
        
        adapter = new MyRecipesAdapter(myRecipeList);
        recyclerView.setAdapter(adapter);

        loadMyRecipes();
    }

    private void loadMyRecipes() {
        String uid = mAuth.getCurrentUser().getUid();
        db.collection("all_recipes").whereEqualTo("userId", uid)
                .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        myRecipeList.clear();
                        for (QueryDocumentSnapshot doc : value) myRecipeList.add(doc.toObject(Recipe.class));
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class MyRecipesAdapter extends RecyclerView.Adapter<MyRecipesAdapter.ViewHolder> {
        private List<Recipe> list;
        MyRecipesAdapter(List<Recipe> list) { this.list = list; }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_recipe, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Recipe r = list.get(position);
            holder.txtName.setText(r.getName());
            holder.txtCat.setText(r.getCategory());

            if (r.getImageUrl() != null && r.getImageUrl().length() > 200) {
                byte[] decoded = Base64.decode(r.getImageUrl(), Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                holder.img.setImageBitmap(bmp);
            } else if (r.getDrawableName() != null) {
                int resId = getResources().getIdentifier(r.getDrawableName(), "drawable", getPackageName());
                holder.img.setImageResource(resId != 0 ? resId : R.drawable.ic_launcher_background);
            }

            // DÜZENLE BUTONU (Burayı Güzelleştirdik)
            holder.btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(MyRecipesActivity.this, AnaMenuActivity.class);
                intent.putExtra("open_fragment", "add_recipe");
                intent.putExtra("edit_recipe_name", r.getName());
                intent.putExtra("edit_recipe_details", r.getDetails());
                intent.putExtra("edit_recipe_ingredients", r.getIngredients());
                intent.putExtra("edit_recipe_instructions", r.getInstructions());
                intent.putExtra("edit_recipe_main", r.getMainIngredient());
                intent.putExtra("edit_recipe_video", r.getVideoUrl());
                intent.putExtra("edit_recipe_image", r.getImageUrl());
                intent.putExtra("edit_recipe_category", r.getCategory());
                // Activity yığınını temizle ve AnaMenu'ye git
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });

            holder.btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(MyRecipesActivity.this)
                        .setTitle("Sil")
                        .setMessage(r.getName() + " silinsin mi?")
                        .setPositiveButton("Evet", (d, w) -> {
                            db.collection("all_recipes").document(r.getName()).delete();
                        }).setNegativeButton("Hayır", null).show();
            });
        }

        @Override public int getItemCount() { return list.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img; TextView txtName, txtCat; ImageButton btnEdit, btnDelete;
            ViewHolder(View v) {
                super(v);
                img = v.findViewById(R.id.imgMyRecipe);
                txtName = v.findViewById(R.id.txtMyRecipeName);
                txtCat = v.findViewById(R.id.txtMyRecipeCategory);
                btnEdit = v.findViewById(R.id.btnEditMyRecipe);
                btnDelete = v.findViewById(R.id.btnDeleteMyRecipe);
            }
        }
    }
}