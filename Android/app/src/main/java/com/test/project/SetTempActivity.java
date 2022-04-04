package com.test.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SetTempActivity extends AppCompatActivity {

    int stat;
    int temp;
    SeekBar seekBar;
    TextView tv_nowtemp;
    TextView tv_target;
    Button btn_tempback;
    Button btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        seekBar=findViewById(R.id.seekBar);
        tv_nowtemp =findViewById(R.id.tv_nowtemp);
        tv_target = findViewById(R.id.tv_target);
        btn_save =findViewById(R.id.btn_save);
        btn_tempback=findViewById(R.id.btn_tempback);

        Intent intent_get = getIntent();

        stat =intent_get.getIntExtra("status",4); // ON
        temp = intent_get.getIntExtra("temp",30);
        tv_nowtemp.setText(String.format("기존 온도 : %d",temp));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_target.setText(String.format("설정 온도 : %d",seekBar.getProgress()));
            }
        });


        btn_tempback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetTempActivity.this,page3Activity.class);
                startActivity(intent);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temp == seekBar.getProgress() ){
                    Toast.makeText(SetTempActivity.this, "기존 온도와 동일합니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    new GetPage().getPage("http://10.10.141.54:8080/android/boilerUpdate/status=" + stat + "/temp="+ seekBar.getProgress());
                }
            }
        });




    }
}