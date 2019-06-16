package com.kepependudukan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public MainActivity() {

    }

    private DrawerLayout drawer;

    //
    private HalamanUtamaFragment halamanUtamaFragment = new HalamanUtamaFragment();
    private TambahDataFragment tambahDataFragment = new TambahDataFragment();
    private TampilDataPenduduk tampilDataPenduduk = new TampilDataPenduduk();
    //

    private Fragment fragmentCurrent;
    private int currentMenuItem;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//           // w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }


        setContentView(R.layout.activity_main);

        //find the related fragment on activity restart

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //akhir tambahan

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXXXXXXX", "click");
                onBackPressed();
            }
        });

        drawer.addDrawerListener(toggle);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        //currentMenuItem = R.id.nav_camera;//default first item
        currentMenuItem = R.id.nav_home; //default first item
        this.getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        if (fragmentCurrent instanceof HalamanUtamaFragment) {
                            currentMenuItem = R.id.nav_home;
                            toggle.setDrawerIndicatorEnabled(false);
                            //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                        } else if (fragmentCurrent instanceof TambahDataFragment) {
                            currentMenuItem = R.id.nav_tambah_penduduk;
                            toggle.setDrawerIndicatorEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                        } else {
                            currentMenuItem = R.id.nav_tampil_penduduk;
                            toggle.setDrawerIndicatorEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        }
                        navigationView.setCheckedItem(currentMenuItem);
                    }
                });


        if (savedInstanceState == null) {
            //addFragment(cameraFragment);
            addFragment(halamanUtamaFragment);

        } else {
            toggle.setDrawerIndicatorEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        hideKeyboard(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentCurrent == halamanUtamaFragment) {

                Log.d("Kiyaa", "current fragment : halaman utama" + fragmentCurrent.toString());
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //super.onBackPressed();
                tampilDialog();
            } else {
                // Log.d("Kiyaa", "current fragment : selain halaman utama" + fragmentCurrent.toString());
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                replaceFragment(halamanUtamaFragment);
                toggle.setDrawerIndicatorEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragmentSelected = null;
        int id = item.getItemId();
        if (id == currentMenuItem) {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
        if (id == R.id.nav_home) {
            fragmentSelected = halamanUtamaFragment;
        } else if (id == R.id.nav_tambah_penduduk) {
            fragmentSelected = tambahDataFragment;
        } else if (id == R.id.nav_tampil_penduduk) {
            //fragmentSelected = tampilDataFragment;
            fragmentSelected = tampilDataPenduduk;
        }

        currentMenuItem = id;
        replaceFragment(fragmentSelected);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addFragment(Fragment fragment) {
        fragmentCurrent = fragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).commit();
        navigationView.setCheckedItem(currentMenuItem);
        hideKeyboard(this);
    }

    //private void replaceFragment(Fragment fragment) {
    public void replaceFragment(Fragment fragment) {
        fragmentCurrent = fragment;
        navigationView.setCheckedItem(currentMenuItem);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        hideKeyboard(this);
    }

    public void tampilDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.myDialogAlert);
        builder.setCancelable(false);
        builder.setMessage("Apakah anda ingin keluar dari Aplikasi ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //hide keyboard
    public static void hideKeyboard(Context ctx) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        //cek if no view has focus
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}