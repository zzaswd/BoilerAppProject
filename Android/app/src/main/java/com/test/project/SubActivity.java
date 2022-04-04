package com.test.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Delayed;

public class SubActivity extends AppCompatActivity {
    int stat;
    int temp;
    Button btn_back;
    Button btn_on;
    Button btn_off;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent_get = getIntent();
        stat =intent_get.getIntExtra("status",4); // ON
        temp = intent_get.getIntExtra("temp",30);

        //--------------------------------------------------------------------------------------------- Button On
        btn_on=findViewById(R.id.btn_on);
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click");
                System.out.println("stat" + stat);
                if(stat == 0){
                    stat =1;
                    new GetPage().getPage("http://10.10.141.54:8080/android/boilerUpdate/status=1/temp=" + temp);
                }
                else {
                    Toast.makeText(SubActivity.this, "이미 켜져있엉", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //--------------------------------------------------------------------------------------------- Button On



        //--------------------------------------------------------------------------------------------- Button Off
        btn_off=findViewById(R.id.btn_off);
        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stat == 1){
                    new GetPage().getPage("http://10.10.141.54:8080/android/boilerUpdate/status=0/temp="+ temp);
                }

                else {
                    stat =0;
                    Toast.makeText(SubActivity.this, "이미 꺼져있엉", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //-------------------------------------------

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this,page3Activity.class);
                startActivity(intent);
                 }
        });



    }
}


