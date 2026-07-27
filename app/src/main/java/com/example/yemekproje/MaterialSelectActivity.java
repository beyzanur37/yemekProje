package com.example.yemekproje;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MaterialSelectActivity extends AppCompatActivity {

    private ChipGroup chipGroup;
    private EditText editAddMaterial;
    private MaterialButton btnAddMaterial, btnGenerate;
    private FirebaseFirestore db;
    private Set<String> materialSet; 
    private Locale tr = new Locale("tr", "TR");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_select);

        db = FirebaseFirestore.getInstance();
        materialSet = new HashSet<>();

        chipGroup = findViewById(R.id.chipGroupMaterials);
        editAddMaterial = findViewById(R.id.editAddMaterial);
        btnAddMaterial = findViewById(R.id.btnAddMaterialChip);
        btnGenerate = findViewById(R.id.btnGenerateRecipe);

        addClassicMaterials();
        loadMaterialsFromRecipes();

        btnAddMaterial.setOnClickListener(v -> {
            String material = editAddMaterial.getText().toString().trim();
            if (!material.isEmpty()) {
                if (materialSet.add(material.toLowerCase(tr))) {
                    addMaterialChip(material, true);
                }
                editAddMaterial.setText("");
            }
        });

        btnGenerate.setOnClickListener(v -> findMatchingRecipes());
    }

    private void addClassicMaterials() {
        String[] classics = {"Tuz", "Şeker", "Un", "Su", "Sıvı Yağ", "Tereyağı", "Yumurta", "Süt", "Karabiber", "Salça"};
        for (String s : classics) {
            if (materialSet.add(s.toLowerCase(tr))) {
                addMaterialChip(s, false);
            }
        }
    }

    private void loadMaterialsFromRecipes() {
        for (Recipe sr : RecipeData.getStaticRecipes()) {
            String searchSource = sr.getMainIngredient();
            if (searchSource != null && !searchSource.isEmpty()) {
                String[] ing = searchSource.split(",");
                for (String p : ing) {
                    String clean = p.trim().toLowerCase(tr);
                    if (!clean.isEmpty() && materialSet.add(clean)) {
                        addMaterialChip(clean, false);
                    }
                }
            }
        }

        db.collection("all_recipes").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                if (!doc.contains("userId") || doc.getString("userId") == null || doc.getString("userId").isEmpty()) continue;
                
                String mainIn = doc.getString("mainIngredient");
                if (mainIn != null && !mainIn.isEmpty()) {
                    String[] ing = mainIn.split(",");
                    for (String p : ing) {
                        String clean = p.trim().toLowerCase(tr);
                        if (!clean.isEmpty() && materialSet.add(clean)) {
                            addMaterialChip(clean, false);
                        }
                    }
                }
            }
        });
    }

    private void addMaterialChip(String text, boolean isChecked) {
        Chip chip = new Chip(this);
        String formattedText = text.substring(0, 1).toUpperCase(tr) + text.substring(1).toLowerCase(tr);
        chip.setText(formattedText);
        chip.setCheckable(true);
        chip.setChecked(isChecked);
        chip.setClickable(true);
        chipGroup.addView(chip);
    }

    private void findMatchingRecipes() {
        ArrayList<String> selected = new ArrayList<>();
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selected.add(chip.getText().toString().toLowerCase(tr).trim());
            }
        }

        if (selected.isEmpty()) {
            Toast.makeText(this, "Lütfen malzeme seçin!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, TarifListesiActivity.class);
        intent.putStringArrayListExtra("selected_materials", selected);
        startActivity(intent);
    }
}