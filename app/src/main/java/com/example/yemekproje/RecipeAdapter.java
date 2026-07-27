package com.example.yemekproje;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipeList, OnItemClickListener listener) {
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        Context context = holder.itemView.getContext();

        holder.name.setText(recipe.getName());
        holder.details.setText(recipe.getDetails());
        holder.txtFavCount.setText(String.valueOf(recipe.getFavoriteCount()));
        holder.txtCommentCount.setText(recipe.getCommentCount() + " yorum");

        // RESİM YÜKLEME
        if (recipe.getDrawableName() != null && !recipe.getDrawableName().isEmpty()) {
            int resId = context.getResources().getIdentifier(recipe.getDrawableName(), "drawable", context.getPackageName());
            holder.image.setImageResource(resId != 0 ? resId : R.drawable.ic_launcher_background);
        } else if (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) {
            if (recipe.getImageUrl().length() > 200) {
                try {
                    byte[] decoded = Base64.decode(recipe.getImageUrl(), Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                    holder.image.setImageBitmap(bmp);
                } catch (Exception e) { holder.image.setImageResource(R.drawable.ic_launcher_background); }
            } else {
                Glide.with(context).load(recipe.getImageUrl()).into(holder.image);
            }
        }

        holder.btnFavorite.setImageResource(recipe.isFavorite() ? R.drawable.ic_heart_filled : R.drawable.ic_heart_empty);

        holder.btnFavorite.setOnClickListener(v -> {
            boolean newStatus = !recipe.isFavorite();
            recipe.setFavorite(newStatus);
            recipe.setFavoriteCount(newStatus ? recipe.getFavoriteCount() + 1 : Math.max(0, recipe.getFavoriteCount() - 1));
            notifyItemChanged(position);
            saveFavoriteToFirebase(recipe, newStatus);
        });

        // --- EKSİK MALZEME BUTONU KONTROLÜ ---
        // Sadece "Tarif Üret" kısmından gelindiğinde (missingIngredients dolu olduğunda) gösterilir.
        if (recipe.getMissingIngredients() != null && !recipe.getMissingIngredients().isEmpty()) {
            holder.btnAddMissing.setVisibility(View.VISIBLE);
            holder.btnAddMissing.setOnClickListener(v -> {
                addToShoppingList(context, recipe.getName(), recipe.getMissingIngredients());
            });
        } else {
            holder.btnAddMissing.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(recipe);
            } else {
                Intent intent = new Intent(context, TarifDetayActivity.class);
                intent.putExtra("recipe_name", recipe.getName());
                intent.putExtra("recipe_details", recipe.getDetails());
                intent.putExtra("recipe_ingredients", recipe.getIngredients());
                intent.putExtra("recipe_instructions", recipe.getInstructions());
                intent.putExtra("recipe_video_url", recipe.getVideoUrl());
                intent.putExtra("recipe_drawable_name", recipe.getDrawableName());
                intent.putExtra("recipe_image_url", recipe.getImageUrl());
                context.startActivity(intent);
            }
        });
    }

    private void addToShoppingList(Context context, String recipeName, String ingredients) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (mAuth.getCurrentUser() == null) return;
        
        String userId = mAuth.getCurrentUser().getUid();
        TarifDetayActivity.ShoppingItem item = new TarifDetayActivity.ShoppingItem(recipeName, ingredients, System.currentTimeMillis());
        
        db.collection("users").document(userId).collection("shopping_list")
                .add(item)
                .addOnSuccessListener(v -> Toast.makeText(context, "Eksikler alışveriş listesine eklendi!", Toast.LENGTH_SHORT).show());
    }

    private void saveFavoriteToFirebase(Recipe recipe, boolean isFavorite) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) return;
        String userId = mAuth.getCurrentUser().getUid();
        if (isFavorite) {
            db.collection("users").document(userId).collection("favorites").document(recipe.getName()).set(recipe);
            db.collection("all_recipes").document(recipe.getName()).update("favoriteCount", FieldValue.increment(1));
        } else {
            db.collection("users").document(userId).collection("favorites").document(recipe.getName()).delete();
            db.collection("all_recipes").document(recipe.getName()).update("favoriteCount", FieldValue.increment(-1));
        }
    }

    @Override public int getItemCount() { return recipeList.size(); }
    public void filterList(List<Recipe> filteredList) { this.recipeList = filteredList; notifyDataSetChanged(); }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView name, details, txtFavCount, txtCommentCount;
        ImageView image;
        ImageButton btnFavorite;
        Button btnAddMissing; // XML'deki yeni buton
        
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtRecipeName);
            details = itemView.findViewById(R.id.txtRecipeDetail);
            txtFavCount = itemView.findViewById(R.id.txtFavoriteCount);
            txtCommentCount = itemView.findViewById(R.id.txtCommentCount);
            image = itemView.findViewById(R.id.imgRecipe);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            btnAddMissing = itemView.findViewById(R.id.btnAddMissing);
        }
    }
}