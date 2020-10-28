package com.example.app_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView label1;
    private Button btn;
    private Button btn6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        label1 = (TextView)findViewById(R.id.label1);
        btn =(Button)findViewById(R.id.button);
        label1.setText("Click to start");
        btn6 = (Button)findViewById(R.id.button6);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_activity2();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_activity3();
            }
        });


    }
    private void switch_activity2(){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
    private void switch_activity3(){
        Intent intent = new Intent(this,MainActivity3.class);
        startActivity(intent);
    }
}