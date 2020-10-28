package com.example.app_java;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.os.Vibrator;



public class MainActivity2 extends AppCompatActivity implements SensorEventListener {
    private Button btn2;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Sensor light;
    private SensorManager sm;
    private Button btn3;
    public int count=0;
    private Button btn4;
    public int count1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);
        textView1 = (TextView)findViewById(R.id.textView1) ;
        textView2 = (TextView)findViewById(R.id.textView2) ;
        textView3 = (TextView)findViewById(R.id.textView3) ;
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchtoMain();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count%2==0){
                    btn3.setText("Torch: On");
                }
                if (count%2==1){
                    btn3.setText("Torch: Off");
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1++;
                if (count1%2==0){
                    btn4.setText("Vibrate: On");
                }
                if (count1%2==1){
                    btn4.setText("Vibrate: Off");
                }

            }
        });







    }




    private void switchtoMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    protected void onResume(){
        super.onResume();
        sm.registerListener(this,light,SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected  void onPause(){
        super.onPause();
        sm.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.equals(light)){
            String values1 = "Lx:"+String.valueOf(event.values[0]); //get light value in [0]
            //String values2 = "Y-axis:"+String.valueOf(event.values[1]);
            //String values3 = "Z-axis:"+String.valueOf(event.values[2]);
            textView1.setText(values1);
            textView2.setText("");
            textView3.setText("");
            if ((event.values[0]>100)&&(event.values[0]<300)){
                float br = 192/(float)255;
                changebr(br);
                flashLightOff();
            }
            if (event.values[0]>300){
                float br = 255/(float)255;
                changebr(br);
                flashLightOff();
            }
            if (event.values[0]<100){
                float br = 50/(float)255;
                changebr(br);
                if (count%2==0){
                    flashLightOn();

                }
                if (count%2==1){
                    flashLightOff();
                }
                if (count1%2==0){
                    vib();

                }






            }


        }

        //float brightness = 150 / (float)255;


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void changebr(float br){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = br;
        getWindow().setAttributes(lp);

    }

    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);

        } catch (CameraAccessException e) {
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
        }
    }

    private void vib(){
        Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);
    }
}