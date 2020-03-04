package com.example.bolkrtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    RecyclerView contentRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadContent lc = new LoadContent();
        lc.execute();
        contentRecycler= (RecyclerView) findViewById(R.id.contentRecycler);
    }


    public void parse_response(String response)
    {
        String TAG = "RESP";
        int total=0;
        try {
            JSONArray arr = new JSONArray(response);
            total = arr.length();
            Log.d(TAG, "parse_response: "+ total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray arr = new JSONArray(response);
            ArrayList<contentModel> contentList= new ArrayList<contentModel>();
            for (int i=0;i<arr.length();i++)
            {
                JSONObject cont = arr.getJSONObject(i);
                contentModel model = new contentModel();
                model.setId(cont.getInt("i"));
                Log.d(TAG, "parse_response: "+cont.getInt("i"));
                Log.d(TAG, "parse_response: "+i);
                model.setName(cont.getString("n"));
                try
                {
                    model.setCategory(cont.getString("a"));
                }
                catch (Exception JSONException)
                {
                    continue;
                }
                model.setLink(cont.getString("p"));

                contentList.add(model);
            }
            contentAdapter adapter = new contentAdapter(contentList, MainActivity.this);
            contentRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
            contentRecycler.setAdapter(adapter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    class LoadContent extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Please wait");
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                String urlString = "https://pascolan-config.s3.us-east-2.amazonaws.com/android/v1/prod/Category/hi/categoryV2.json";
                HttpURLConnection urlConnection = null;
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                String j = sb.toString();
                JSONArray response = null;
                response = new JSONArray(j);

                parse_response(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        }

    }








}
