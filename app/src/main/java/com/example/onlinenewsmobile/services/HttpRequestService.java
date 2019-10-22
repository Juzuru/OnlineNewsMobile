package com.example.onlinenewsmobile.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class HttpRequestService {

    public static String getContent(String url) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = new URL(url).openConnection().getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Bitmap getImageBitmap(String url){
        InputStream is = null;

        try {
            URLConnection connection  = new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            is = connection.getInputStream();

            return BitmapFactory.decodeStream(is);
        } catch (NetworkOnMainThreadException e) {
            try {
                return new LoadImage().execute().get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static class LoadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            InputStream is = null;

            try {
                try {
                    is = new URL(strings[0]).openConnection().getInputStream();
                } catch (NetworkOnMainThreadException e) {

                }
                return BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public static StringBuilder executeRequest(String... strings) {
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            request.setRequestMethod(strings[1]);

            StringBuilder sb = new StringBuilder();
            if (strings[1].equals("GET")){
                request.connect();


                InputStream is= request.getInputStream();
                InputStreamReader ist = new InputStreamReader(is, "UTF-8");



                BufferedReader br = new BufferedReader(ist);
                String line;
                while ((line = br.readLine()) != null){
                    sb.append(line);
                }
                br.close();
            } else if (strings[1].equals("POST") || strings[1].equals("PUT")) {

                request.setRequestProperty("Content-Type", "application/json");
                request.setRequestProperty("Accept","application/json");

                request.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(request.getOutputStream(), "UTF-8"));
                bw.write("{\"facebookId\":\"231234124\"}, {\"name\":\"toan\"}");
                bw.flush();
                bw.close();

                request.connect();
            } else if (strings[1].equals("DELETE")) {
                request.connect();
            }

            request.disconnect();
            return sb;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static String executeGet(URL url) {
        HttpURLConnection request = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            request = (HttpURLConnection) url.openConnection();
            request.connect();

            is = request.getInputStream();
            isr = new InputStreamReader(is, "UTF-8");

            br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
                if (request != null) {
                    request.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    public static boolean executePost(URL url, String data) {
        HttpURLConnection request = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

        try {
            request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("POST");

            request.setDoOutput(true);
            request.setRequestProperty("Content-Type", "application/json");
            request.setRequestProperty("Accept","application/json");

            osw = new OutputStreamWriter(request.getOutputStream(), "UTF-8");
            bw = new BufferedWriter(osw);
            bw.write(data);
            bw.flush();
            bw.close();

            request.connect();
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (request != null) {
                    request.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
