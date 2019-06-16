package com.kepependudukan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kepependudukan.adapter.AdapterAgama;
import com.kepependudukan.adapter.AdapterKewarganegaraan;
import com.kepependudukan.adapter.AdapterPendidikan;
import com.kepependudukan.adapter.AdapterStatusPerkawinan;
import com.kepependudukan.api.API;
import com.kepependudukan.api.RequestHandler;
import com.kepependudukan.app.AppController;

import com.kepependudukan.model.DataAgama;
import com.kepependudukan.model.DataKewarganegaraan;
import com.kepependudukan.model.DataPendidikan;
import com.kepependudukan.model.DataPerkawinan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class TambahDataFragment extends Fragment {

    String jen_kel, agama, pendidikan, statusperkawinan, kewarganegaraan;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    //EditText txt_tanggal;
    EditText eNik, eNama, eTmptLahir, eTglLahir, ePekerjaan, eProvinsi, eKodePos,
            eKabupatenKota, eKecamatan, eDesaKelurahan, eRt, eRw, eAlamat;
    Spinner spinner_agama, spinner_pendidikan, spinner_status_perkawinan, spinner_kewarganegaraan;

    Button btnTambah;
    RadioGroup rg;
    RadioButton rbL, rbP;

    ProgressDialog pDialog;
    ProgressBar progressBar;
    AdapterAgama adapterAgama;
    AdapterPendidikan adapterPendidikan;
    AdapterStatusPerkawinan adapterStatusPerkawinan;
    AdapterKewarganegaraan adapterKewarganegaraan;

    List<DataAgama> listAgama = new ArrayList<>();
    List<DataPendidikan> listPendidikan = new ArrayList<>();
    List<DataPerkawinan> listPerkawinan = new ArrayList<>();
    List<DataKewarganegaraan> listKewarganegaraan = new ArrayList<>();

    boolean isUpdating = false;


    private static final String TAG = TambahDataFragment.class.getSimpleName();

    public TambahDataFragment() {
        // Required empty public constructor
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        Log.d("Kiyaa", "ini onCreateView()");
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Tambah Data Penduduk");

        final View rootView = inflater.inflate(R.layout.fragment_tambah_data, container, false);
        callData();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Kiyaa", "onAttach()");
        //callData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull final View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        Log.d("Kiyaa", "apa benar dipanggil setelah onCreateView() ?");

        eNik = (rootView).findViewById(R.id.eNIK);
        eNama = (rootView).findViewById(R.id.eNamaPenduduk);
        eTmptLahir = (rootView).findViewById(R.id.eTmptLahir);
        eTglLahir = (rootView).findViewById(R.id.eTglLahir);
        ePekerjaan = (rootView).findViewById(R.id.ePekerjaan);
        eProvinsi = (rootView).findViewById(R.id.eProvinsi);
        eKodePos = (rootView).findViewById(R.id.eKodePos);
        eKabupatenKota = (rootView).findViewById(R.id.eKabupatenKota);
        eKecamatan = (rootView).findViewById(R.id.eKecamatan);
        eDesaKelurahan = (rootView).findViewById(R.id.eDesaKelurahan);
        eRt = (rootView).findViewById(R.id.eRT);
        eRw = (rootView).findViewById(R.id.eRW);
        eAlamat = (rootView).findViewById(R.id.eAlamat);

        //txt_tanggal = (rootView).findViewById(R.id.eTglLahir);
        rg = (rootView).findViewById(R.id.rbJenKel);
        rbL = (rootView).findViewById(R.id.rbLaki);
        rbP = (rootView).findViewById(R.id.rbPerempuan);

        spinner_agama = (rootView).findViewById(R.id.spinner_agama);
        spinner_pendidikan = (rootView).findViewById(R.id.spinner_pendidikan);
        spinner_status_perkawinan = (rootView).findViewById(R.id.spinner_status_perkawinan);
        spinner_kewarganegaraan = (rootView).findViewById(R.id.spinner_kewarganegaraan);

        rg.setFocusable(true);
        rg.setFocusableInTouchMode(true);
        rg.clearCheck();

        progressBar = (rootView).findViewById(R.id.progressBar);
        btnTambah = (rootView).findViewById(R.id.btnTambahPenduduk);
        btnTambah.setEnabled(false);

        //blok untuk spinner

        //spinner agama
        spinner_agama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                agama = listAgama.get(position).getId_agama();

                Log.d("Kiyaa", "item yang dipilih : " + agama + " dengan id : " + agama);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        adapterAgama = new AdapterAgama(getActivity(), listAgama);
        spinner_agama.setAdapter(adapterAgama);

        //spinner pendidikan
        spinner_pendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                pendidikan = listPendidikan.get(position).getId_pendidikan();

                Log.d("Kiyaa", "item yang dipilih " + pendidikan + " dengan id : " + pendidikan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        adapterPendidikan = new AdapterPendidikan(getActivity(), listPendidikan);
        spinner_pendidikan.setAdapter(adapterPendidikan);

        //spinner status perkawinan
        spinner_status_perkawinan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                statusperkawinan = listPerkawinan.get(position).getId_status_perkawinan();

                Log.d("Kiyaa", "item yang dipilih " + statusperkawinan + " dengan id : " + statusperkawinan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        adapterStatusPerkawinan = new AdapterStatusPerkawinan(getActivity(), listPerkawinan);
        spinner_status_perkawinan.setAdapter(adapterStatusPerkawinan);

        //spinner kewarganegaraan
        spinner_kewarganegaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                kewarganegaraan = listKewarganegaraan.get(position).getId_kewarganegaraan();

                Log.d("Kiyaa", "item yang dipilih " + kewarganegaraan + " dengan id : " + kewarganegaraan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        adapterKewarganegaraan = new AdapterKewarganegaraan(getActivity(), listKewarganegaraan);
        spinner_kewarganegaraan.setAdapter(adapterKewarganegaraan);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        eTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Objects.requireNonNull(getActivity()), date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbLaki:
                        jen_kel = "1";
                        Log.d("Kiyaa", "Jenis kelamin : " + jen_kel + " dipilih");
                        break;

                    case R.id.rbPerempuan:
                        jen_kel = "2";
                        Log.d("Kiyaa", "Jenis kelamin : " + jen_kel + " dipilih");
                        break;
                }
            }
        });

        //text watcher
        eAlamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    btnTambah.setEnabled(false);
                } else {
                    btnTambah.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Kiyaa", "button tambah di klik, terus tinggal panggil methodnys");

                if (isUpdating) {
                    Log.d("Kiyaa", "isUpdating bernilai true");

                } else {
                    Log.d("Kiyaa", "isUpdating bernilai false");

                    createPenduduk();

                }
            }
        });

    }

    private void createPenduduk() {
        String Nama, tmptLahir, tglLahir, pekerjaan, provinsi, kabupatenkota, kecamatan, desakelurahan, alamat,
                NIK, kodepos, rt, rw;

        NIK = eNik.getText().toString();
        Nama = eNama.getText().toString().trim().toUpperCase();

        tmptLahir = eTmptLahir.getText().toString().trim().toUpperCase();
        tglLahir = eTglLahir.getText().toString().trim();
        pekerjaan = ePekerjaan.getText().toString().trim().toUpperCase();
        provinsi = eProvinsi.getText().toString().trim().toUpperCase();
        kodepos = eKodePos.getText().toString().trim();
        kabupatenkota = eKabupatenKota.getText().toString().trim().toUpperCase();
        kecamatan = eKecamatan.getText().toString().trim().toUpperCase();
        desakelurahan = eDesaKelurahan.getText().toString().trim().toUpperCase();
        rt = eRt.getText().toString().trim();
        rw = eRw.getText().toString().trim();
        alamat = eAlamat.getText().toString().trim().toUpperCase();

        if (TextUtils.isEmpty(NIK)) {
            eNik.setError("NIK tidak boleh kosong !");
            eNik.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Nama)) {
            eNama.setError("Nama tidak boleh kosong !");
            eNama.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tmptLahir)) {
            eTmptLahir.setError("Tempat Lahir tidak boleh kosong !");
            eTmptLahir.requestFocus();
        }


        //if validation passes

        Log.d("Kiyaa", "Nik - alamat : " + NIK + " " + Nama + " " + jen_kel + " " + tmptLahir + " " + tglLahir + " " + agama + " "
                + pendidikan + " " + pekerjaan + " " + statusperkawinan + " " + kewarganegaraan + " " + provinsi + " " + kodepos + " " + kabupatenkota + " "
                + kecamatan + " " + desakelurahan + " " + rt + " " + rw + " " + alamat);

        HashMap<String, String> params = new HashMap<>();
        params.put("nik_warga", NIK);
        params.put("nama_warga", Nama);
        params.put("jen_kel", jen_kel);
        params.put("tmpt_lahir", tmptLahir);
        params.put("tgl_lahir", tglLahir);
        params.put("agama", agama);
        params.put("pendidikan", pendidikan);
        params.put("pekerjaan", pekerjaan);
        params.put("status_perkawinan", statusperkawinan);
        params.put("kewarganegaraan", kewarganegaraan);
        params.put("provinsi", provinsi);
        params.put("kode_pos", kodepos);
        params.put("kabupaten_kota", kabupatenkota);
        params.put("kecamatan", kecamatan);
        params.put("desa_kelurahan", desakelurahan);
        params.put("rt", rt);
        params.put("rw", rw);
        params.put("alamat", alamat);


        //memanggil API createPenduduk
        PerformNetworkRequest request = new PerformNetworkRequest(API.URL_CREATE_PENDUDUK, params, API.CODE_POST_REQUEST);
        request.execute();

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        eTglLahir.setText(sdf.format(myCalendar.getTime()));
        Log.d("Kiyaa", "Tanggal Lahir : " + sdf.format(myCalendar.getTime()));
    }

    private void callData() {

        listAgama.clear();
        listPendidikan.clear();
        listPerkawinan.clear();
        listKewarganegaraan.clear();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();

        //creating volley request list agama
        JsonArrayRequest jArr_agama = new JsonArrayRequest(API.url_agama, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                //buat parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataAgama item = new DataAgama();
                        item.setId_agama(obj.getString(API.TAG_ID_AGAMA));
                        item.setAgama(obj.getString(API.TAG_AGAMA));

                        listAgama.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterAgama.notifyDataSetChanged();
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error : " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr_agama);

        //creating volley request list pendidikan
        JsonArrayRequest jArr_pendidikan = new JsonArrayRequest(API.url_pendidikan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                //buat parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataPendidikan item = new DataPendidikan();
                        item.setId_pendidikan(obj.getString(API.TAG_ID_PENDIDIKAN));
                        item.setPendidikan(obj.getString(API.TAG_PENDIDIKAN));

                        listPendidikan.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterPendidikan.notifyDataSetChanged();
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error : " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr_pendidikan);

        //creating volley request list status perkawinan
        JsonArrayRequest jArr_status_perkawinan = new JsonArrayRequest(API.url_status_perkawinan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                //buat parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataPerkawinan item = new DataPerkawinan();
                        item.setId_status_perkawinan(obj.getString(API.TAG_ID_STATUS_PERKAWINAN));
                        item.setStatus_perkawinan(obj.getString(API.TAG_STATUS_PERKAWINAN));

                        listPerkawinan.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterStatusPerkawinan.notifyDataSetChanged();
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error : " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr_status_perkawinan);

        //creating volley request list kewarganegaraan
        JsonArrayRequest jArr_kewarganegaraan = new JsonArrayRequest(API.url_kewarganegaraan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                //buat parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataKewarganegaraan item = new DataKewarganegaraan();
                        item.setId_kewarganegaraan(obj.getString(API.TAG_ID_KEWARGANEGARAAN));
                        item.setKewarganegaraan(obj.getString(API.TAG_KEWARGANEGARAAN));

                        listKewarganegaraan.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterKewarganegaraan.notifyDataSetChanged();
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error : " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr_kewarganegaraan);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        //string url we need to send the request
        String url;

        //parameter
        HashMap<String, String> params;

        //kode request untuk mendefiniskan apakah GET atau POST
        int requestCode;

        //constructor class untuk menginsialisasi nilai
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {

                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();

                    TampilDataPenduduk tampilDataPenduduk = new TampilDataPenduduk();
                    Fragment tampil = tampilDataPenduduk;
                    ((MainActivity) getActivity()).replaceFragment(tampil);


                } else {
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == API.CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);

            if (requestCode == API.CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    // akhir blok class Async
}