package com.projects.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {


    private String required_JSON = new String();
    private JSONObject req_res = new JSONObject();
    private ArrayList<AppModel> apps = new ArrayList<AppModel>();
    private App_list_rv_adaptor adaptor;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String cat = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(cat+" apps");
        try {
            required_JSON = new MyTask().execute("https://permissions.adityamittl.repl.co/"+cat).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            req_res = new JSONObject(required_JSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keys = req_res.keys();
        recyclerView = findViewById(R.id.app_rv);
        while(keys.hasNext()) {
            String key = keys.next();
            try {
                int permissions = (int) req_res.getJSONArray(key).get(0);
                String link = (String) req_res.getJSONArray(key).get(1);
                ArrayList<String> perm_name =  new ArrayList<String>();
                JSONArray permission_name = (JSONArray) req_res.getJSONArray(key).get(2);
                for(int i=0;i<permission_name.length();i++){
                    perm_name.add(permission_name.get(i).toString());
                }
                AppModel app = new AppModel(key,link,permissions,perm_name);
                apps.add(app);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        apps.sort((o1,o2)->o1.getPermissions()-o2.getPermissions());
        linearLayoutManager = new LinearLayoutManager(this);
        adaptor = new App_list_rv_adaptor(this,apps);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adaptor);

    }
    public static String stream(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nojson";
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params)
        {
            String url = params[0];
            JSONObject json = null;
            try {
                return stream(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return "no json";
        }

        @Override
        protected void onPostExecute(String result)
        {
            Log.e("json",result);
            super.onPostExecute(result);
        }
    }

}