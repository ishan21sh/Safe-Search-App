package com.projects.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetName extends AppCompatActivity {

    EditText category;
    Button getSuggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        category = findViewById(R.id.category);
        getSuggestion = findViewById(R.id.getSuggestion);

        getSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_str = category.getText().toString();
                if(category_str!=null && category_str.length()!=0){
                    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            Intent i = new Intent(GetName.this,MainActivity.class);
                            i.putExtra("category",category_str);
                            startActivity(i);
                        }
                    else {
                        Toast.makeText(GetName.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(GetName.this,"Field cannot be empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}