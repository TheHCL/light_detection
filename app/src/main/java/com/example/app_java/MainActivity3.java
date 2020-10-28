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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity3 extends AppCompatActivity implements SensorEventListener {
    private Button btn5;
    private Sensor light;
    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btn5  = (Button)findViewById(R.id.button5);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);


        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_activity();
            }
        });

    }

    private void switch_activity(){
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


            if ((event.values[0]>100)&&(event.values[0]<300)){
                float br = 192/(float)255;
                flashLightOff();

            }
            if (event.values[0]>300){
                float br = 255/(float)255;
                flashLightOn();

            }
            if (event.values[0]<100){
                float br = 50/(float)255;
                flashLightOff();
            }

        }
    }






    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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


}
