package com.kepependudukan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kepependudukan.R;
import com.kepependudukan.api.API;
import com.kepependudukan.api.RequestHandler;
import com.kepependudukan.model.ModelPenduduk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterDataPenduduk extends RecyclerView.Adapter<AdapterDataPenduduk.PendudukViewHolder> implements Filterable {

    private Context context;

    public List<ModelPenduduk> pendudukList;
    private List<ModelPenduduk> pendudukListFiltered;

    private AdapterDataPendudukListener listener;

    private List<ModelPenduduk> detailPendudukList = new ArrayList<>();

    private View dialogView;

    private String txt_nik, txt_nama, txt_jen_kel, txt_tmpt_lahir, txt_tgl_lahir, txt_agama,
            txt_jenjang_pendidikan, txt_pekerjaan, txt_status_perkawinan, txt_kewarganegaraan, txt_provinsi, txt_kode_pos,
            txt_kabupaten_kota, txt_kecamatan, txt_desa_kelurahan, txt_rt, txt_rw, txt_alamat;

    private TextView v_nik, v_nama, v_jen_kel, v_tmpt_lahir, v_tgl_lahir, v_agama,
            v_jenjang_pendidikan, v_pekerjaan, v_status_perkawinan, v_kewarganegaraan, v_provinsi, v_kode_pos,
            v_kabupaten_kota, v_kecamatan, v_desa_kelurahan, v_rt, v_rw, v_alamat;

    @NonNull
    @Override
    public PendudukViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.penduduk_row, viewGroup, false);

        return new PendudukViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PendudukViewHolder holder, final int position) {
        final ModelPenduduk penduduk = pendudukListFiltered.get(position);
        holder.nik_warga.setText(penduduk.getNik_warga());
        holder.nama_warga.setText(penduduk.getNama_warga());
        holder.jen_kel.setText(penduduk.getJen_kel());
        holder.desa_kelurahan.setText(penduduk.getDesa_kelurahan());
    }

    private void ambilDetailPenduduk(JSONArray detailPenduduk) throws JSONException {
        v_nik = this.dialogView.findViewById(R.id.det_nik_warga);
        v_nama = this.dialogView.findViewById(R.id.det_nama_warga);
        v_jen_kel = this.dialogView.findViewById(R.id.det_jen_kel);
        v_tmpt_lahir = this.dialogView.findViewById(R.id.det_tmpt_lahir);
        v_tgl_lahir = this.dialogView.findViewById(R.id.det_tgl_lahir);
        v_agama = this.dialogView.findViewById(R.id.det_agama);
        v_jenjang_pendidikan = this.dialogView.findViewById(R.id.det_jenjang_pendidikan);
        v_pekerjaan = this.dialogView.findViewById(R.id.det_pekerjaan);
        v_status_perkawinan = this.dialogView.findViewById(R.id.det_status_perkawinan);
        v_kewarganegaraan = this.dialogView.findViewById(R.id.det_kewarganegaraan);
        v_provinsi = this.dialogView.findViewById(R.id.det_provinsi);
        v_kode_pos = this.dialogView.findViewById(R.id.det_kodepos);
        v_kabupaten_kota = this.dialogView.findViewById(R.id.det_kabupaten_kota);
        v_kecamatan = this.dialogView.findViewById(R.id.det_kecamatan);
        v_desa_kelurahan = this.dialogView.findViewById(R.id.det_desa_kelurahan);
        v_rt = this.dialogView.findViewById(R.id.det_rt);
        v_rw = this.dialogView.findViewById(R.id.det_rw);
        v_alamat = this.dialogView.findViewById(R.id.det_alamat);

        detailPendudukList.clear();

        Log.d("Kiyaa", "size detailPenduduk : " + String.valueOf(detailPenduduk.length()));

        for (int i = 0; i < detailPenduduk.length(); i++) {
            JSONObject obj = detailPenduduk.getJSONObject(i);

            detailPendudukList.add(new ModelPenduduk(
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
            txt_nik = detailPendudukList.get(i).getNik_warga();
            txt_nama = detailPendudukList.get(i).getNama_warga();
            txt_jen_kel = detailPendudukList.get(i).getJen_kel();
            txt_tmpt_lahir = detailPendudukList.get(i).getTmpt_lahir();
            txt_tgl_lahir = detailPendudukList.get(i).getTgl_lahir();
            txt_agama = detailPendudukList.get(i).getAgama();
            txt_jenjang_pendidikan = detailPendudukList.get(i).getPendidikan();
            txt_pekerjaan = detailPendudukList.get(i).getPekerjaan();
            txt_status_perkawinan = detailPendudukList.get(i).getStatus_perkawinan();
            txt_kewarganegaraan = detailPendudukList.get(i).getKewarganegaraan();
            txt_provinsi = detailPendudukList.get(i).getProvinsi();
            txt_kode_pos = detailPendudukList.get(i).getKode_pos();
            txt_kabupaten_kota = detailPendudukList.get(i).getKabupaten_kota();
            txt_kecamatan = detailPendudukList.get(i).getKecamatan();
            txt_desa_kelurahan = detailPendudukList.get(i).getDesa_kelurahan();
            txt_rt = detailPendudukList.get(i).getRt();
            txt_rw = detailPendudukList.get(i).getRw();
            txt_alamat = detailPendudukList.get(i).getAlamat();

            //Log.d("Kiyaa", "variabel x : " + txt_kewarganegaraan);
        }
        v_nik.setText(txt_nik);
        v_nama.setText(txt_nama);
        v_jen_kel.setText(txt_jen_kel);
        v_tmpt_lahir.setText(txt_tmpt_lahir);
        v_tgl_lahir.setText(txt_tgl_lahir);
        v_agama.setText(txt_agama);
        v_jenjang_pendidikan.setText(txt_jenjang_pendidikan);
        v_pekerjaan.setText(txt_pekerjaan);
        v_status_perkawinan.setText(txt_status_perkawinan);
        v_kewarganegaraan.setText(txt_kewarganegaraan);
        v_provinsi.setText(txt_provinsi);
        v_kode_pos.setText(txt_kode_pos);
        v_kabupaten_kota.setText(txt_kabupaten_kota);
        v_kecamatan.setText(txt_kecamatan);
        v_desa_kelurahan.setText(txt_desa_kelurahan);
        v_rt.setText(txt_rt);
        v_rw.setText(txt_rw);
        v_alamat.setText(txt_alamat);

        //super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                //return null;
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    pendudukListFiltered = pendudukList;

                } else {
                    List<ModelPenduduk> filteredList = new ArrayList<>();
                    for (ModelPenduduk row : pendudukList) {
                        if (row.getNik_warga().contains(charSequence) ||
                                row.getNama_warga().toUpperCase().contains(charString.toUpperCase()) ||
                                row.getJen_kel().toUpperCase().contains(charString.toUpperCase()) ||
                                row.getDesa_kelurahan().toUpperCase().contains(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }
                    pendudukListFiltered = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = pendudukListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //pendudukListFiltered=(ArrayList<ModelPenduduk>) filterResults.values;
                Log.d("Kiyaa","publish");
                pendudukListFiltered = (ArrayList<ModelPenduduk>) filterResults.values;
                AdapterDataPenduduk.this.notifyDataSetChanged();
            }
        };
    }

    class AmbilDetailPenduduk extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;
        //ProgressDialog loading;

        AmbilDetailPenduduk(String url, HashMap<String, String> params, int requestCode) {
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
                    //Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    //tampilPenduduk(obj.getJSONArray("penduduk"));
                    ambilDetailPenduduk(obj.getJSONArray("penduduk"));
                    Log.d("Kiyaa", obj.toString());
                } else {
                    //Toast.makeText(TampilDataPenduduk.this.getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        //return pendudukList.size();
        return pendudukListFiltered.size();
    }

    public class PendudukViewHolder extends RecyclerView.ViewHolder {
        public TextView nik_warga, nama_warga, jen_kel, desa_kelurahan;

        public PendudukViewHolder(View view) {
            super(view);
            nik_warga = view.findViewById(R.id.nik_warga);
            nama_warga = view.findViewById(R.id.nama_warga);
            jen_kel = view.findViewById(R.id.jen_kel);
            desa_kelurahan = view.findViewById(R.id.desa_keluarahan);

            //edited
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterDataPendudukSelected(pendudukListFiltered.get(getAdapterPosition()));
                    Log.d("Kiyaa", "selected klik " + pendudukListFiltered.get(getAdapterPosition()).getNik_warga());
                    dialogView = (View.inflate(PendudukViewHolder.super.itemView.getContext(), R.layout.tampil_detail, null));
                    String nik = pendudukListFiltered.get(getAdapterPosition()).getNik_warga();

                    //add item detail penduduk into dialogView
                    Log.d("Kiyaa", "Klik : " + nik);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(PendudukViewHolder.super.itemView.getRootView().getContext(), R.style.myDialogAlert);

                    AmbilDetailPenduduk a = new AmbilDetailPenduduk(API.URL_READ_DET_PENDUDUK + nik, null, API.CODE_GET_REQUEST);
                    a.execute();

                    builder.setView(dialogView);
                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();

                    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CLOSE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    public AdapterDataPenduduk(Context context, List<ModelPenduduk> pendudukList, AdapterDataPendudukListener listener) {
        this.context = context;
        this.listener = listener;
        this.pendudukList = pendudukList;
        this.pendudukListFiltered = pendudukList;
    }

    public interface AdapterDataPendudukListener {
        void onAdapterDataPendudukSelected(ModelPenduduk modelPenduduk);
    }
}
