package com.example.yemekproje;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yemekproje.TarifDetayActivity.ShoppingItem;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private List<ShoppingItemWithId> items;
    private OnItemDeleteListener deleteListener;

    public interface OnItemDeleteListener {
        void onDeleteClick(ShoppingItemWithId item, int position);
    }

    public static class ShoppingItemWithId {
        public String docId;
        public ShoppingItem item;
        public ShoppingItemWithId(String docId, ShoppingItem item) { this.docId = docId; this.item = item; }
    }

    public ShoppingAdapter(List<ShoppingItemWithId> items, OnItemDeleteListener deleteListener) {
        this.items = items;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingItemWithId current = items.get(position);
        holder.txtName.setText(current.item.recipeName);
        holder.txtIngredients.setText(current.item.ingredients);
        
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(current, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtIngredients;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtShoppingRecipeName);
            txtIngredients = itemView.findViewById(R.id.txtShoppingIngredients);
            btnDelete = itemView.findViewById(R.id.btnDeleteShopping);
        }
    }
}
