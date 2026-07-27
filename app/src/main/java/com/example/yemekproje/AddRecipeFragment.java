package com.example.yemekproje;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddRecipeFragment extends Fragment {

    private EditText editName, editDetails, editIngredients, editInstructions, editVideoUrl, editMainIngredient;
    private Spinner spinnerCategory;
    private ImageView imgRecipe;
    private Button btnAdd, btnSelectImage;
    private ProgressBar progressBar;
    
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String base64Image = "";
    private boolean isEditMode = false;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    imgRecipe.setImageURI(imageUri);
                    base64Image = encodeImage(imageUri);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        initViews(view);

        String[] categories = {"Çorbalar", "Ana Yemekler", "Salatalar", "Ara Sıcaklar", "Tatlılar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // DÜZENLEME MODU KONTROLÜ
        Bundle args = getArguments();
        if (args != null && args.containsKey("edit_recipe_name")) {
            isEditMode = true;
            fillFieldsForEdit(args);
            btnAdd.setText("Güncellemeyi Kaydet");
        }

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });

        btnAdd.setOnClickListener(v -> saveRecipe());

        return view;
    }

    private void initViews(View view) {
        editName = view.findViewById(R.id.editAddRecipeName);
        editDetails = view.findViewById(R.id.editAddRecipeDetails);
        editIngredients = view.findViewById(R.id.editAddIngredients);
        editInstructions = view.findViewById(R.id.editAddInstructions);
        editVideoUrl = view.findViewById(R.id.editAddVideoUrl);
        editMainIngredient = view.findViewById(R.id.editAddMainIngredient);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        imgRecipe = view.findViewById(R.id.imgAddRecipe);
        btnSelectImage = view.findViewById(R.id.btnPreviewImage); 
        btnAdd = view.findViewById(R.id.btnAddRecipeToFirebase);
        progressBar = view.findViewById(R.id.progressBarAddRecipe);
        btnSelectImage.setText("Galeriden Fotoğraf Seç");
    }

    private void fillFieldsForEdit(Bundle args) {
        editName.setText(args.getString("edit_recipe_name"));
        editName.setEnabled(false); // İsmi değiştirtmiyoruz (Firestore döküman ID'si çünkü)
        editDetails.setText(args.getString("edit_recipe_details"));
        editIngredients.setText(args.getString("edit_recipe_ingredients"));
        editInstructions.setText(args.getString("edit_recipe_instructions"));
        editMainIngredient.setText(args.getString("edit_recipe_main"));
        editVideoUrl.setText(args.getString("edit_recipe_video"));
        base64Image = args.getString("edit_recipe_image", "");
        
        if (!base64Image.isEmpty()) {
            try {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgRecipe.setImageBitmap(decodedByte);
            } catch (Exception e) {}
        }
    }

    private String encodeImage(Uri uri) {
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] bytes = baos.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) { return ""; }
    }

    private void saveRecipe() {
        String name = editName.getText().toString().trim();
        if (name.isEmpty()) return;

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        btnAdd.setEnabled(false);

        Recipe r = new Recipe();
        r.setName(name);
        r.setDetails(editDetails.getText().toString().trim());
        r.setIngredients(editIngredients.getText().toString().trim());
        r.setInstructions(editInstructions.getText().toString().trim());
        r.setMainIngredient(editMainIngredient.getText().toString().trim());
        r.setCategory(spinnerCategory.getSelectedItem().toString());
        r.setVideoUrl(editVideoUrl.getText().toString().trim());
        r.setImageUrl(base64Image);
        r.setUserId(mAuth.getCurrentUser().getUid());

        db.collection("all_recipes").document(name).set(r)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), isEditMode ? "Güncellendi!" : "Eklendi!", Toast.LENGTH_SHORT).show();
                    if (isEditMode) getParentFragmentManager().popBackStack();
                    else clearFields();
                })
                .addOnCompleteListener(t -> {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    btnAdd.setEnabled(true);
                });
    }

    private void clearFields() {
        editName.setText(""); editDetails.setText(""); editIngredients.setText("");
        editInstructions.setText(""); editMainIngredient.setText(""); editVideoUrl.setText("");
        imgRecipe.setImageResource(R.drawable.ic_spoon); base64Image = "";
    }
}