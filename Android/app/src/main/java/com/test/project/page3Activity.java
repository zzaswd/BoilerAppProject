package com.test.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class page3Activity extends AppCompatActivity {

    TextView tv_status;
    TextView tv_temp;
    Button btn_logout;
    Button btn_setstat;
    Button btn_settemp;

    int stat;
    int temp;
    static List<Member> data;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        btn_setstat = findViewById(R.id.btn_setstat);
        btn_settemp = findViewById(R.id.btn_settemp);
        btn_logout = findViewById(R.id.btn_logout);
        tv_status =findViewById(R.id.tv_status);
        tv_temp = findViewById(R.id.tv_temp);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String page = "http://10.10.141.54:8080/android/boilerData";

                try {

                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder stringBuilder = new StringBuilder();

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
                                stringBuilder.append(line + "\n");
                            }
                            bufferedReader.close();
                        }
                        conn.disconnect();
                    }

                    Gson gson = new Gson();

                    Type type = new TypeToken<List<Member>>() {}.getType();
                    data = gson.fromJson(String.valueOf(stringBuilder),type);

                    int status = data.get(0).getStatus();
                    String date = data.get(0).getDate();
                    temp = data.get(0).getTemp();

                    if(status == 1){  // ON?
                        stat = 1;
                    }
                    else stat = 0;
                    System.out.println("stat = " +stat);

                    if(stat == 1)
                        tv_println_status("  :  " + "ON");
                    else tv_println_status("  :  " + "OFF");
                    tv_println_temp("  :  " + temp);
                    //tv_println("\nRecyclerView로 구현");


                    //rv_println(String.valueOf(stringBuilder));

                    //boardAdapter = new BoardAdapter(data);
                    //recyclerView.setAdapter(boardAdapter);
                    //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } //End of run()

        }); //End of Thread
        thread.start();

        btn_setstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(page3Activity.this,SubActivity.class);
                intent.putExtra("status",stat);
                intent.putExtra("temp",temp);
                startActivity(intent);
            }
        });

        btn_settemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(page3Activity.this,SetTempActivity.class);
                intent.putExtra("status",stat);
                intent.putExtra("temp",temp);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(page3Activity.this,LoginActivity.class);
                startActivity(intent3);

            }
        });


    }
    public void tv_println_status(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv_status.append(data + "\n");
            }
        });
    }

    public void tv_println_temp(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv_temp.append(data+"\n");
            }
        });
    }

}