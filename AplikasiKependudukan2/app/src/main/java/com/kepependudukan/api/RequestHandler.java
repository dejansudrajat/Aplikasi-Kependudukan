package com.kepependudukan.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {
    //Metode Untuk mengirim httpPostRequest
    //Metode ini mengambil 2 Argumen
    //Metode Pertama adalah URL dari Skrip yang digunakan untuk mengirimkan permintaan
    //Yang lainnya adalah HashMap dengan nilai pasangan nama yang berisi data yang akan dikirim dengan permintaan
    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {
        //Membuat URL
        URL url;

        //objek StringBuilder untuk menyimpan pesan diambil dari server
        StringBuilder sb = new StringBuilder();
        try {
            //insialisasi URL
            url = new URL(requestURL);

            //membuat koneksi HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //konfigurasi Koneksi
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //membuat keluaran Stream
            OutputStream os = conn.getOutputStream();

            //menulis parameter untuk permintaan kita menggunakan metode
            //getPostDataString yang didefinisikan di bawah ini
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                //reading server response
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequest(String requestURL) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }

        } catch (Exception e) {
        }
        return sb.toString();
    }

    public String sendGetRequestParam(String requestURL, String id) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }

        } catch (Exception e) {
        }
        return sb.toString();
    }

    public String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
