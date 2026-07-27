package com.example.yemekproje;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AnaMenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_menu);

        bottomNav = findViewById(R.id.bottom_navigation);

        // İLK AÇILIŞ VEYA DÜZENLEME KONTROLÜ
        handleIntent(getIntent());

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) selectedFragment = new HomeFragment();
            else if (id == R.id.nav_favorite) selectedFragment = new FavoritesFragment();
            else if (id == R.id.nav_add_recipe) selectedFragment = new AddRecipeFragment();
            else if (id == R.id.nav_person) selectedFragment = new ProfileFragment();

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra("open_fragment")) {
            String fragmentName = intent.getStringExtra("open_fragment");
            if ("add_recipe".equals(fragmentName)) {
                AddRecipeFragment editFragment = new AddRecipeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("edit_recipe_name", intent.getStringExtra("edit_recipe_name"));
                bundle.putString("edit_recipe_details", intent.getStringExtra("edit_recipe_details"));
                bundle.putString("edit_recipe_ingredients", intent.getStringExtra("edit_recipe_ingredients"));
                bundle.putString("edit_recipe_instructions", intent.getStringExtra("edit_recipe_instructions"));
                bundle.putString("edit_recipe_main", intent.getStringExtra("edit_recipe_main"));
                bundle.putString("edit_recipe_video", intent.getStringExtra("edit_recipe_video"));
                bundle.putString("edit_recipe_image", intent.getStringExtra("edit_recipe_image"));
                bundle.putString("edit_recipe_category", intent.getStringExtra("edit_recipe_category"));
                editFragment.setArguments(bundle);

                bottomNav.setSelectedItemId(R.id.nav_add_recipe);
                loadFragment(editFragment);
            } else if ("profile".equals(fragmentName)) {
                bottomNav.setSelectedItemId(R.id.nav_person);
                loadFragment(new ProfileFragment());
            }
        } else {
            // Varsayılan açılış
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
                loadFragment(new HomeFragment());
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss();
    }
}