package com.test.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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


public class LoginActivity extends AppCompatActivity {

    static List<MemberId> data1;

    Handler handler = new Handler();

    TextView tv_id;
    TextView tv_pw;
    Button btn_register;
    Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_id = findViewById(R.id.tv_id);
        tv_pw = findViewById(R.id.tv_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String page1 = "http://10.10.141.54:8080/android/IDData";

                try {

                    URL url1 = new URL(page1);
                    HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                    StringBuilder stringBuilder1 = new StringBuilder();

                    if(conn != null){

                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        conn.setUseCaches(false);
                        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){

                            // 입출력 스트림 연결하여 저장.
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                            while(true){
                                // 라인별로 받아와서 스트링별로 나눈다.
                                String line = bufferedReader.readLine();
                                if(line == null) break;
                                stringBuilder1.append(line + "\n");
                            }
                            bufferedReader.close();
                        };
                        conn.disconnect();
                    }

                    Gson gson1 = new Gson();

                    Type type = new TypeToken<List<MemberId>>() {}.getType();
                    data1 = gson1.fromJson(String.valueOf(stringBuilder1),type);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } //End of run()

        }); //End of Thread
        thread.start();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int compare = -1;
                int id = data1.get(0).getID();
                for(int i = 0; i<id;i++) {
                    String userid = data1.get(i).getUserId();
                    String userpw = data1.get(i).getUserpw();

                    if (tv_id.getText().toString().equals(userid) && tv_pw.getText().toString().equals(userpw)) {
                        compare = 1;
                        System.out.println("맞아따");
                    }
                }
                System.out.println(compare);

                if(compare == 1 ){
                    Intent intent = new Intent(LoginActivity.this,page3Activity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "ID나 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_reg = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_reg);
            }
        });

    }

}



