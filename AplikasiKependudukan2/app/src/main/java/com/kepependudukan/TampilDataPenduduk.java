package com.kepependudukan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kepependudukan.adapter.AdapterDataPenduduk;
import com.kepependudukan.api.API;
import com.kepependudukan.api.RequestHandler;
import com.kepependudukan.app.AppController;
import com.kepependudukan.model.DataAgama;
import com.kepependudukan.model.DataKewarganegaraan;
import com.kepependudukan.model.DataPendidikan;
import com.kepependudukan.model.DataPerkawinan;
import com.kepependudukan.model.ModelPenduduk;
import com.kepependudukan.swipe.SwipeController;
import com.kepependudukan.swipe.SwipeControllerActions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TampilDataPenduduk extends Fragment implements AdapterDataPenduduk.AdapterDataPendudukListener {

    public View dialogView;

    public String txt_nik, txt_nama, txt_jenKel, txt_tmptLahir, txt_tglLahir, txt_agama,
            txt_pendidikan, txt_pekerjaan, txt_statusPerkawinan, txt_kewarganegaraan, txt_provinsi, txt_kodePos,
            txt_kabupatenKota, txt_kecamatan, txt_desaKelurahan, txt_rt, txt_rw, txt_alamat;
    public EditText eNik, eNama, eTmptLahir, eTglLahir, ePekerjaan, eProvinsi,
            eKodePos, eKabupatenKota, eKecamatan, eDesaKelurahan, eRt, eRw, eAlamat;

    private int a, b, c, d;

    public RadioGroup rgJenKel;
    public RadioButton rbL, rbP;
    public Spinner sAgama, sPendidikan, sStatusPerkawinan, sKewarganegaraan;

    private List<ModelPenduduk> pendudukList;
    List<ModelPenduduk> listEdit = new ArrayList<>();
    List<DataAgama> listAgama = new ArrayList<>();
    List<DataPendidikan> listPendidikan = new ArrayList<>();
    List<DataPerkawinan> listPerkawinan = new ArrayList<>();
    List<DataKewarganegaraan> listKewarganegaraan = new ArrayList<>();

    RecyclerView recyclerView;

    public Calendar myCalendar;
    public DatePickerDialog.OnDateSetListener date;

    public TampilDataPenduduk() {
        setRetainInstance(true);
    }

    public AdapterDataPenduduk mAdapter;
    private SearchView searchView;

    SwipeController swipeController = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Tampil Data Penduduk");

        final View rootView = inflater.inflate(R.layout.tampil_data_penduduk, container, false);
        recyclerView = (rootView).findViewById(R.id.recyclerView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        pendudukList = new ArrayList<>();
        setupRecyclerView();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Kiyaa", "Query : " + query);
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d("Kiyaa", "Query" + query);
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(final int position) {
                //super.onRightClicked(position);
                Log.d("Kiyaa", "Hapus Data : " + pendudukList.get(position).getNik_warga());

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.myDialogAlert);
                builder.setTitle("Hapus Data " + pendudukList.get(position).getNik_warga() + " ?");
                builder.setMessage("Apakah anda yakin hapus data ini ?");
                //builder.setIcon(R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String nik = pendudukList.get(position).getNik_warga();
                        Log.d("Kiyaa", pendudukList.get(position).getNik_warga() + " berhasil dihapus");
                        Log.d("Kiyaa", API.URL_DELETE_PENDUDUK);

                        AmbilData a = new AmbilData(API.URL_DELETE_PENDUDUK + nik, null, API.CODE_GET_REQUEST);
                        a.execute();

                        mAdapter.pendudukList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Kiyaa", "Cancel");
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onLeftClicked(final int position) {
                //super.onLeftClicked(position);
                Log.d("Kiyaa", "Edit Data : " + pendudukList.get(position).getNik_warga());

                //for handle view edit penduduk
                dialogView = (View.inflate(getContext(), R.layout.layout_edit_data, null));

                //parameter untuk memanggil data berdasarkan nik yang sesuai dengan item di recyclerView
                final String nik = pendudukList.get(position).getNik_warga();

                //get all data from db
                setPendudukList(nik);

                //membuat dialog untuk menampilkan dialog edit
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.myDialogAlert);
                builder.setView(dialogView);
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();

                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Kiyaa", "cancel update");
                        dialog.cancel();
                    }
                });

                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Kiyaa", "button update cliked");
                        //getData dari form untuk kemudian dilakukan proses update
                        String e_nik, e_nama, e_jenkel, e_tmptLahir, e_tglLahir, e_agama, e_pendidikan, e_pekerjaan,
                                e_perkawinan, e_kewarganegaraan, e_provinsi, e_kodepos, e_kabupatenKota, e_kecamatan,
                                e_desaKelurahan, e_rt, e_rw, e_alamat;

                        e_nik = eNik.getText().toString().trim();
                        e_nama = eNama.getText().toString().trim().toUpperCase();
                        e_jenkel = txt_jenKel.toString().trim();
                        e_tmptLahir = eTmptLahir.getText().toString().trim().toUpperCase();
                        e_tglLahir = eTglLahir.getText().toString().trim();
                        e_agama = txt_agama.trim();
                        e_pendidikan = txt_pendidikan.trim();
                        e_pekerjaan = ePekerjaan.getText().toString().trim().toUpperCase();
                        e_perkawinan = txt_statusPerkawinan.trim();
                        e_kewarganegaraan = txt_kewarganegaraan.trim();
                        e_provinsi = eProvinsi.getText().toString().trim().toUpperCase();
                        e_kodepos = eKodePos.getText().toString().trim();
                        e_kabupatenKota = eKabupatenKota.getText().toString().trim().toUpperCase();
                        e_kecamatan = eKecamatan.getText().toString().trim().toUpperCase();
                        e_desaKelurahan = eDesaKelurahan.getText().toString().trim().toUpperCase();
                        e_rt = eRt.getText().toString().trim();
                        e_rw = eRw.getText().toString().trim();
                        e_alamat = eAlamat.getText().toString().trim().toUpperCase();

                        Log.d("Hmmm", "parameter edit : " + e_nik + " " + e_nama + " " + e_jenkel + " " +
                                e_tmptLahir + " " + e_tglLahir + " " + e_agama + " " + e_pendidikan + " " +
                                e_pekerjaan + " " + e_perkawinan + " " + e_kewarganegaraan + " " + e_provinsi + " " +
                                e_kodepos + " " + e_kabupatenKota + " " + e_kecamatan + " " + e_desaKelurahan + " " +
                                e_rt + " " + e_rw + " " + e_alamat);
                        //map untuk parameter edit
                        HashMap<String, String> params = new HashMap<>();
                        params.put("nik_warga", e_nik);
                        params.put("nama_warga", e_nama);
                        params.put("jen_kel", e_jenkel);
                        params.put("tmpt_lahir", e_tmptLahir);
                        params.put("tgl_lahir", e_tglLahir);
                        params.put("agama", e_agama);
                        params.put("pendidikan", e_pendidikan);
                        params.put("pekerjaan", e_pekerjaan);
                        params.put("status_perkawinan", e_perkawinan);
                        params.put("kewarganegaraan", e_kewarganegaraan);
                        params.put("provinsi", e_provinsi);
                        params.put("kode_pos", e_kodepos);
                        params.put("kabupaten_kota", e_kabupatenKota);
                        params.put("kecamatan", e_kecamatan);
                        params.put("desa_kelurahan", e_desaKelurahan);
                        params.put("rt", e_rt);
                        params.put("rw", e_rw);
                        params.put("alamat", e_alamat);

                        PerformNetworkRequest req = new PerformNetworkRequest(API.URL_UPDATE_PENDUDUK, params, API.CODE_POST_REQUEST);
                        req.execute();

                        setAdapterDataPenduduk();

                    }
                });
                dialog.show();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                swipeController.onDraw(c);
            }
        });

        //recyclerView.setAdapter(mAdapter);
        setAdapterDataPenduduk();


    }

    //call data untuk di fetch saat dialog edit muncul
    private void callData() {
        listAgama.clear();
        listPendidikan.clear();
        listPerkawinan.clear();
        listKewarganegaraan.clear();

        //creating volley request list agama
        JsonArrayRequest jArr_agama = new JsonArrayRequest(API.url_agama, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Kiyaa", response.toString());

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
                List<String> label = new ArrayList<>();
                for (int i = 0; i < listAgama.size(); i++) {
                    label.add(listAgama.get(i).getAgama());
                }

                //create spinner
                ArrayAdapter<String> spinnerAgama = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.list_agama, R.id.agama, label);
                sAgama.setAdapter(spinnerAgama);
                sAgama.setSelection(a);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Kiyaa", "Error : " + error.getMessage());
            }
        });

        //creating volley request list pendidikan
        JsonArrayRequest jArr_pendidikan = new JsonArrayRequest(API.url_pendidikan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Kiyaa", response.toString());

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
                List<String> label = new ArrayList<>();
                for (int i = 0; i < listPendidikan.size(); i++) {
                    label.add(listPendidikan.get(i).getPendidikan());
                }

                //create spinner
                ArrayAdapter<String> spinnerPendidikan = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.list_pendidikan, R.id.pendidikan, label);
                sPendidikan.setAdapter(spinnerPendidikan);
                sPendidikan.setSelection(b);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Kiyaa", "Error : " + error.getMessage());
            }
        });

        //creating volley request list status perkawinan
        JsonArrayRequest jArr_perkawinan = new JsonArrayRequest(API.url_status_perkawinan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Kiyaa", response.toString());

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
                List<String> label = new ArrayList<>();
                for (int i = 0; i < listPerkawinan.size(); i++) {
                    label.add(listPerkawinan.get(i).getStatus_perkawinan());
                }

                //create spinner
                ArrayAdapter<String> spinnerPerkawinan = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.list_status_perkawinan, R.id.status_perkawinan, label);
                sStatusPerkawinan.setAdapter(spinnerPerkawinan);
                sStatusPerkawinan.setSelection(c);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Kiyaa", "Error : " + error.getMessage());
            }
        });

        //creating volley request list kewarganegaraan
        JsonArrayRequest jArr_kewarganegaraan = new JsonArrayRequest(API.url_kewarganegaraan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Kiyaa", response.toString());

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
                List<String> label = new ArrayList<>();
                for (int i = 0; i < listKewarganegaraan.size(); i++) {
                    label.add(listKewarganegaraan.get(i).getKewarganegaraan());
                }

                //create spinner
                ArrayAdapter<String> spinnerKewarganegaraan = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.list_kewarganegaraan, R.id.kewarganegaraan, label);
                sKewarganegaraan.setAdapter(spinnerKewarganegaraan);
                sKewarganegaraan.setSelection(d);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Kiyaa", "Error : " + error.getMessage());
            }
        });

        //
        AppController.getInstance().addToRequestQueue(jArr_agama);
        AppController.getInstance().addToRequestQueue(jArr_pendidikan);
        AppController.getInstance().addToRequestQueue(jArr_perkawinan);
        AppController.getInstance().addToRequestQueue(jArr_kewarganegaraan);
    }


    public void tampilListPenduduk(JSONArray list) throws JSONException {
        //memanngil list spinner
        callData();

        //EditText
        eNik = dialogView.findViewById(R.id.eNIK);
        eNik.setEnabled(false);
        eNama = dialogView.findViewById(R.id.eNamaPenduduk);
        eTmptLahir = dialogView.findViewById(R.id.eTmptLahir);
        eTglLahir = dialogView.findViewById(R.id.eTglLahir);
        ePekerjaan = dialogView.findViewById(R.id.ePekerjaan);
        eProvinsi = dialogView.findViewById(R.id.eProvinsi);
        eKodePos = dialogView.findViewById(R.id.eKodePos);
        eKabupatenKota = dialogView.findViewById(R.id.eKabupatenKota);
        eKecamatan = dialogView.findViewById(R.id.eKecamatan);
        eDesaKelurahan = dialogView.findViewById(R.id.eDesaKelurahan);
        eRt = dialogView.findViewById(R.id.eRT);
        eRw = dialogView.findViewById(R.id.eRW);
        eAlamat = dialogView.findViewById(R.id.eAlamat);

        //Radio
        rgJenKel = dialogView.findViewById(R.id.rbJenKel);
        rgJenKel.clearCheck();
        rbL = dialogView.findViewById(R.id.rbLaki);
        rbP = dialogView.findViewById(R.id.rbPerempuan);

        //spinner
        sAgama = dialogView.findViewById(R.id.spinner_agama);
        sPendidikan = dialogView.findViewById(R.id.spinner_pendidikan);
        sStatusPerkawinan = dialogView.findViewById(R.id.spinner_status_perkawinan);
        sKewarganegaraan = dialogView.findViewById(R.id.spinner_kewarganegaraan);

        //action for Datepicker
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                eTglLahir.setText(sdf.format(myCalendar.getTime()));
                Log.d("Kiyaa", "Tanggal Lahir : " + sdf.format(myCalendar.getTime()));
            }
        };

        eTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar kal = Calendar.getInstance();
                Date tanggal;
                String format = "yyyy-MM-dd";
                SimpleDateFormat df = new SimpleDateFormat(format, Locale.US);
                try {
                    tanggal = df.parse(eTglLahir.getText().toString().trim());
                    Log.d("Kiya ", "format parse string ke tanggal : " + tanggal.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int thn = Integer.valueOf(eTglLahir.getText().toString().substring(0, 4));
                int bln = Integer.valueOf(eTglLahir.getText().toString().substring(5, 7));
                int hr = Integer.valueOf(eTglLahir.getText().toString().substring(8, 10));

                Log.d("Kiyaa", "substring tgl : " + thn + " " + bln + " " + hr);
                new DatePickerDialog(Objects.requireNonNull(getActivity()), date, thn,
                        bln - 1, hr).show();

            }
        });

        //action from spinner Agama
        sAgama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_agama = listAgama.get(position).getId_agama();
                Log.d("Kiyaa", "item yang dipilih : " + txt_agama + " dengan id : " + txt_agama);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //action from spinner pendidikan
        sPendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_pendidikan = listPendidikan.get(position).getId_pendidikan();
                Log.d("Kiyaa", "item yang dipilih : " + txt_pendidikan + " dengan id : " + txt_pendidikan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //action from spinner status perkawinan
        sStatusPerkawinan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_statusPerkawinan = listPerkawinan.get(position).getId_status_perkawinan();
                Log.d("Kiyaa", "item yang dipilih : " + txt_statusPerkawinan + " dengan id : " + txt_statusPerkawinan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //action from spinner kewarganegaraan
        sKewarganegaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_kewarganegaraan = listKewarganegaraan.get(position).getId_kewarganegaraan();
                Log.d("Kiyaa", "item yang dipilih : " + txt_kewarganegaraan + " dengan id : " + txt_kewarganegaraan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //action from radio
        rgJenKel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbL.isChecked()) {
                    txt_jenKel = "1";
                    Log.d("Kiyaa", "Jenkel : " + txt_jenKel);
                } else {
                    txt_jenKel = "2";
                    Log.d("Kiyaa", "Jenkel : " + txt_jenKel);
                }
            }
        });

        //
        listEdit.clear();
        for (int i = 0; i < list.length(); i++) {
            JSONObject obj = list.getJSONObject(i);
            listEdit.add(new ModelPenduduk(
                    obj.getString("nik_warga"),
                    obj.getString("nama_warga"),
                    obj.getString("jen_kel"),
                    obj.getString("tmpt_lahir"),
                    obj.getString("tgl_lahir"),
                    obj.getString("agama"),
                    obj.getString("pendidikan"),
                    obj.getString("pekerjaan"),
                    obj.getString("status_perkawinan"),
                    obj.getString("kewarganegaraan"),
                    obj.getString("provinsi"),
                    obj.getString("kode_pos"),
                    obj.getString("kabupaten_kota"),
                    obj.getString("kecamatan"),
                    obj.getString("desa_kelurahan"),
                    obj.getString("rt"),
                    obj.getString("rw"),
                    obj.getString("alamat")
            ));

            txt_nik = listEdit.get(i).getNik_warga();
            txt_nama = listEdit.get(i).getNama_warga();
            txt_tmptLahir = listEdit.get(i).getTmpt_lahir();
            txt_tglLahir = listEdit.get(i).getTgl_lahir();
            txt_pekerjaan = listEdit.get(i).getPekerjaan();
            txt_provinsi = listEdit.get(i).getProvinsi();
            txt_kodePos = listEdit.get(i).getKode_pos();
            txt_kabupatenKota = listEdit.get(i).getKabupaten_kota();
            txt_kecamatan = listEdit.get(i).getKecamatan();
            txt_desaKelurahan = listEdit.get(i).getDesa_kelurahan();
            txt_rt = listEdit.get(i).getRt();
            txt_rw = listEdit.get(i).getRw();
            txt_alamat = listEdit.get(i).getAlamat();
            //untuk spinner
            a = Integer.valueOf(listEdit.get(i).getAgama());
            b = Integer.valueOf(listEdit.get(i).getPendidikan());
            c = Integer.valueOf(listEdit.get(i).getStatus_perkawinan());
            d = Integer.valueOf(listEdit.get(i).getKewarganegaraan());
            //untuk radio
            txt_jenKel = listEdit.get(i).getJen_kel();
        }

        eNik.setText(txt_nik);
        eNama.setText(txt_nama);
        eTmptLahir.setText(txt_tmptLahir);
        eTglLahir.setText(txt_tglLahir);
        ePekerjaan.setText(txt_pekerjaan);
        eProvinsi.setText(txt_provinsi);
        eKodePos.setText(txt_kodePos);
        eKabupatenKota.setText(txt_kabupatenKota);
        eKecamatan.setText(txt_kecamatan);
        eDesaKelurahan.setText(txt_desaKelurahan);
        eRt.setText(txt_rt);
        eRw.setText(txt_rw);
        eAlamat.setText(txt_alamat);
        //untuk radio
        if (txt_jenKel.equals("1")) {
            rbL.setChecked(true);
        } else {
            rbP.setChecked(true);
        }
    }

    //mengisi array pendudukList dari JSON untuk kemudian ditampilkan pada recyclerView
    public void tampilPenduduk(JSONArray penduduk) throws JSONException {
        pendudukList.clear();

        for (int i = 0; i < penduduk.length(); i++) {
            JSONObject obj = penduduk.getJSONObject(i);

            pendudukList.add(new ModelPenduduk(
                    obj.getString("nik_warga"),
                    obj.getString("nama_warga"),
                    obj.getString("jen_kel"),
                    obj.getString("desa_kelurahan")
            ));
        }
        mAdapter = new AdapterDataPenduduk(TampilDataPenduduk.this.getContext(), pendudukList, TampilDataPenduduk.this);
        recyclerView.setAdapter(mAdapter);

    }

    //untuk memanggil data dari db untuk ditampilkan pada recyclerView
    private void setAdapterDataPenduduk() {
        AmbilData a = new AmbilData(API.URL_READ_PENDUDUK, null, API.CODE_GET_REQUEST);
        a.execute();
    }

    //untuk memanggil data dari db untuk ditampilkan pada dialog edit
    private void setPendudukList(String nik) {
        ListDataEdit a = new ListDataEdit(API.URL_GET_PENDUDUK + nik, null, API.CODE_GET_REQUEST);
        a.execute();
    }

    @Override
    public void onAdapterDataPendudukSelected(ModelPenduduk modelPenduduk) {
        //Toast.makeText(getContext(),"Selected : "+modelPenduduk.getNik_warga()+", "+modelPenduduk.getNama_warga(), Toast.LENGTH_LONG).show();

        Log.d("Kiyaa", "onAdapterPendudukSelected");
    }

    //JSON req dari db untuk kemudian ditampilkan di recyclerView
    class AmbilData extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        AmbilData(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //loading = ProgressDialog.show(getActivity(), "Fetching... ", "Wait...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //loading.dismiss();
            try {
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {
                    Toast.makeText(TampilDataPenduduk.this.getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    tampilPenduduk(obj.getJSONArray("penduduk"));
                    Log.d("Kiyaa", obj.toString());
                } else {
                    Toast.makeText(TampilDataPenduduk.this.getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler rh = new RequestHandler();
            //String s = rh.sendGetRequest(API.URL_READ_PENDUDUK);
            //return s;
            if (requestCode == API.CODE_POST_REQUEST)
                return rh.sendPostRequest(url, params);

            if (requestCode == API.CODE_GET_REQUEST)
                return rh.sendGetRequest(url);
            return null;
        }
    }

    //JSON req dari db untuk kemudian ditampilkan di dialog edit
    class ListDataEdit extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;
        //ProgressDialog loading;

        ListDataEdit(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //loading = ProgressDialog.show(getActivity(), "Fetching... ", "Wait...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //loading.dismiss();
            try {
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {
                    Toast.makeText(TampilDataPenduduk.this.getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    //tampilPenduduk(obj.getJSONArray("penduduk"));
                    tampilListPenduduk(obj.getJSONArray("penduduk"));
                    Log.d("Kiyaa", obj.toString());
                } else {
                    Toast.makeText(TampilDataPenduduk.this.getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler rh = new RequestHandler();
            //String s = rh.sendGetRequest(API.URL_READ_PENDUDUK);
            //return s;
            if (requestCode == API.CODE_POST_REQUEST)
                return rh.sendPostRequest(url, params);

            if (requestCode == API.CODE_GET_REQUEST)
                return rh.sendGetRequest(url);
            return null;
        }
    }

    class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
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
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {

                    //recyclerView.invalidate();

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
}
