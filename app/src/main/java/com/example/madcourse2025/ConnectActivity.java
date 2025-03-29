package com.example.madcourse2025;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectActivity extends AppCompatActivity {
    private EditText ed_urlcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        ed_urlcontent = findViewById(R.id.ed_urlcontent);
        EditText ed_url = findViewById(R.id.ed_url);

        Button btn_fetch = findViewById(R.id.btn_fetch);
        btn_fetch.setOnClickListener(
                v -> {
                    RemoteContentTask RCT = new RemoteContentTask();
                    String url = ed_url.getText().toString();
                    RCT.execute(url);
                }
        );

        ed_url.addTextChangedListener(new TextWatcher()  {
                                          public void afterTextChanged(Editable s) { }
                                          public void beforeTextChanged(CharSequence s, int start, int count, int after)  { }
                                          public void onTextChanged(CharSequence s, int start, int before, int count)  {
                                              ed_urlcontent.setText("");
                                          }
                                      }
        );

        Button btn_fetchvolley = findViewById(R.id.btn_fetchvolley);
        btn_fetchvolley.setOnClickListener(
                v -> {
                    RequestQueue queue = Volley.newRequestQueue(ConnectActivity.this);
                    String url = ed_url.getText().toString();

                    StringRequest stringRequest = new StringRequest (Request.Method.GET, url,
                            response -> {
                                ed_urlcontent.setText(response);
                            },
                            error -> {
                                ed_urlcontent.setText(error.toString());
                            });
                    queue.add(stringRequest);
                }
        );
    }

    private class RemoteContentTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response;
            try {
                URL url = new URL(urls[0]);
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(false);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    try {
                        InputStream inputStream;
                        int status = urlConnection.getResponseCode();

                        if (status != HttpURLConnection.HTTP_OK)  {
                            inputStream = urlConnection.getErrorStream();
                        } else  {
                            inputStream = urlConnection.getInputStream();
                        }
                        response = readStream(inputStream);
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (java.io.IOException ioex) {
                    response = ioex.toString();
                    Log.e("ConnectActivity: ", "Java Exception: ", ioex);
                }
            } catch (MalformedURLException muex) {
                response = muex.toString();
                Log.e("ConnectActivity: ", "Malformed URL Exception ", muex);
            } catch (Exception e) {
                response = e.toString();
                Log.e("ConnectActivity: ", "UnknownException ", e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            ed_urlcontent.setText(result);
        }

        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }
    }
}