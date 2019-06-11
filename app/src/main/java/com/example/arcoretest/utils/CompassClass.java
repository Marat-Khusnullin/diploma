package com.example.arcoretest.utils;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

public class CompassClass implements SensorEventListener {

    private float angle = 0f;
    private SensorManager mSensorManager;
    private Context context;


    public CompassClass(Context context) {
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        angle = Math.round(event.values[0]);
        pauseCompass();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void refreshAngle() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    public void pauseCompass() {
        mSensorManager.unregisterListener(this);
    }

    public float getAngle() {
        return angle;
    }

}
