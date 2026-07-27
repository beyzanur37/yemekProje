package com.example.yemekproje;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView txtFullName, txtEmail, txtRecipeCount, txtFavCount, txtCommentCount;
    private ImageView imgProfile;
    private FloatingActionButton fabEditImg;
    private Button btnLogout;
    private LinearLayout layoutRecipes;
    private RecyclerView recyclerShopping;
    private TextView txtEmptyShopping;
    private ShoppingAdapter shoppingAdapter;
    private List<ShoppingAdapter.ShoppingItemWithId> shoppingList;
    
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private Uri imageUri;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    uploadProfileImage();
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        initViews(view);

        if (mAuth.getCurrentUser() != null) {
            loadUserData();
        }

        // --- TARİFLERİME TIKLANDIĞINDA YENİ PENCERE AÇ ---
        layoutRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyRecipesActivity.class);
            startActivity(intent);
        });
        
        fabEditImg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        btnLogout.setOnClickListener(v -> showLogoutDialog());

        return view;
    }

    private void initViews(View view) {
        imgProfile = view.findViewById(R.id.imgProfile);
        fabEditImg = view.findViewById(R.id.fabEditProfileImg);
        txtFullName = view.findViewById(R.id.txtProfileFullName);
        txtEmail = view.findViewById(R.id.txtProfileEmail);
        txtRecipeCount = view.findViewById(R.id.txtRecipeCount);
        txtFavCount = view.findViewById(R.id.txtFavCount);
        txtCommentCount = view.findViewById(R.id.txtCommentCount);
        btnLogout = view.findViewById(R.id.btnLogout);
        layoutRecipes = (LinearLayout) txtRecipeCount.getParent();
        recyclerShopping = view.findViewById(R.id.recyclerShopping);
        txtEmptyShopping = view.findViewById(R.id.txtEmptyShopping);
    }

    private void loadUserData() {
        String uid = mAuth.getCurrentUser().getUid();
        db.collection("users").document(uid).get().addOnSuccessListener(doc -> {
            if (doc.exists()) {
                txtFullName.setText(doc.getString("name") + " " + doc.getString("surname"));
                txtEmail.setText(mAuth.getCurrentUser().getEmail());
                String pImg = doc.getString("profileImage");
                if (pImg != null && !pImg.isEmpty()) Glide.with(this).load(pImg).into(imgProfile);
            }
        });
        
        // İstatistikleri çek
        db.collection("all_recipes").whereEqualTo("userId", uid).get()
                .addOnSuccessListener(snap -> txtRecipeCount.setText(String.valueOf(snap.size())));
        
        db.collection("users").document(uid).collection("favorites").get()
                .addOnSuccessListener(snap -> txtFavCount.setText(String.valueOf(snap.size())));
                
        loadShoppingList(uid);
    }
    
    private void loadShoppingList(String uid) {
        shoppingList = new ArrayList<>();
        shoppingAdapter = new ShoppingAdapter(shoppingList, (item, position) -> {
            db.collection("users").document(uid).collection("shopping_list").document(item.docId)
              .delete().addOnSuccessListener(aVoid -> loadShoppingList(uid));
        });
        recyclerShopping.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerShopping.setAdapter(shoppingAdapter);

        db.collection("users").document(uid).collection("shopping_list")
          .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
          .get()
          .addOnSuccessListener(snap -> {
              shoppingList.clear();
              for (QueryDocumentSnapshot d : snap) {
                  TarifDetayActivity.ShoppingItem si = d.toObject(TarifDetayActivity.ShoppingItem.class);
                  shoppingList.add(new ShoppingAdapter.ShoppingItemWithId(d.getId(), si));
              }
              shoppingAdapter.notifyDataSetChanged();
              txtEmptyShopping.setVisibility(shoppingList.isEmpty() ? View.VISIBLE : View.GONE);
          });
    }

    private void uploadProfileImage() {
        if (imageUri == null) return;
        String uid = mAuth.getCurrentUser().getUid();
        StorageReference ref = storage.getReference().child("profile_images/" + uid);
        ref.putFile(imageUri).addOnSuccessListener(t -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
            db.collection("users").document(uid).update("profileImage", uri.toString());
            Glide.with(this).load(uri).into(imgProfile);
        }));
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(getContext()).setTitle("Çıkış").setMessage("Emin misiniz?").setPositiveButton("Evet", (d, w) -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }).setNegativeButton("Hayır", null).show();
    }
}