package com.kepependudukan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;


public class HalamanUtamaFragment extends Fragment {

    public HalamanUtamaFragment() {
        // Required empty public constructor
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Halaman Utama");
        CardView tambah, tampil;

        final View rootView = inflater.inflate(R.layout.fragment_halaman_utama, container, false);

        tambah = (rootView).findViewById(R.id.shortcutTambah);
        tampil = (rootView).findViewById(R.id.shortcutTampil);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Kiyaa", "shortcut tambah klik");
                TambahDataFragment tambahDataFragment = new TambahDataFragment();
                Fragment tampil = tambahDataFragment;
                ((MainActivity) getActivity()).replaceFragment(tampil);

            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Kiyaa","Shortcut tampil klik");
                TampilDataPenduduk tampilDataPenduduk = new TampilDataPenduduk();
                Fragment tampil = tampilDataPenduduk;
                ((MainActivity) getActivity()).replaceFragment(tampil);

            }
        });

        return rootView;
    }
}