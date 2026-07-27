package com.example.yemekproje;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    CardView cardCorba, cardAnaYemek, cardSalata, cardAraSicak, cardTatli, cardTarifUret;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Kartları bağla
        cardCorba = view.findViewById(R.id.cardCorba);
        cardAnaYemek = view.findViewById(R.id.cardAnaYemek);
        cardSalata = view.findViewById(R.id.cardSalata);
        cardAraSicak = view.findViewById(R.id.cardAraSicak);
        cardTatli = view.findViewById(R.id.cardTatli);
        cardTarifUret = view.findViewById(R.id.cardTarifUret);

        searchView = view.findViewById(R.id.searchView);

        // Arama aktif hale getir
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrele(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrele(newText);
                return true;
            }
        });

        // Kart Tıklamaları - Artik Fragment açıyoruz!
        cardCorba.setOnClickListener(v -> openCategory("Çorbalar"));
        cardAnaYemek.setOnClickListener(v -> openCategory("Ana Yemekler"));
        cardSalata.setOnClickListener(v -> openCategory("Salatalar"));
        cardAraSicak.setOnClickListener(v -> openCategory("Ara Sıcaklar"));
        cardTatli.setOnClickListener(v -> openCategory("Tatlılar"));

        cardTarifUret.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(getActivity(), MaterialSelectActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void openCategory(String categoryName) {
        TarifListesiFragment fragment = TarifListesiFragment.newInstance(categoryName);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null) // Geri tuşuyla ana sayfaya dönmek için
                .commit();
    }

    // 🔥 ARAMA FONKSİYONU
    private void filtrele(String text) {
        text = text.toLowerCase();
        if (text.isEmpty()) {
            cardCorba.setVisibility(View.VISIBLE);
            cardAnaYemek.setVisibility(View.VISIBLE);
            cardSalata.setVisibility(View.VISIBLE);
            cardAraSicak.setVisibility(View.VISIBLE);
            cardTatli.setVisibility(View.VISIBLE);
            cardTarifUret.setVisibility(View.VISIBLE);
            return;
        }
        cardCorba.setVisibility("çorba".contains(text) ? View.VISIBLE : View.GONE);
        cardAnaYemek.setVisibility("ana".contains(text) || "yemek".contains(text) ? View.VISIBLE : View.GONE);
        cardSalata.setVisibility("salata".contains(text) ? View.VISIBLE : View.GONE);
        cardAraSicak.setVisibility("ara".contains(text) || "sıcak".contains(text) ? View.VISIBLE : View.GONE);
        cardTatli.setVisibility("tatlı".contains(text) ? View.VISIBLE : View.GONE);
        cardTarifUret.setVisibility("üret".contains(text) || "tarif".contains(text) ? View.VISIBLE : View.GONE);
    }
}