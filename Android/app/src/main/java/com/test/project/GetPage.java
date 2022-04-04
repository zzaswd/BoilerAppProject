package com.test.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetPage{

    void getPage (String page) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                    if (conn != null) {
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        conn.setUseCaches(false);
                        new InputStreamReader(conn.getInputStream(), "UTF-8");

                        conn.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } //End of run()

        }); //End of Thread
        thread.start();

    }
}
