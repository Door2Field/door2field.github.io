package com.door2field.entryapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class BackGroundActivity extends AppCompatActivity {

    private TextView mTvSensorValueAzimuth = null;
    private TextView mTvSensorValuePitch   = null;
    private TextView mTvSensorValueRoll    = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg);

        mTvSensorValueAzimuth = (TextView)findViewById(R.id.MyTextView4SensorValueAzimuth);
        mTvSensorValuePitch   = (TextView)findViewById(R.id.MyTextView4SensorValuePitch);
        mTvSensorValueRoll    = (TextView)findViewById(R.id.MyTextView4SensorValueRoll);
    }

    @Override
    public void onResume() {
        super.onResume();
        initSensor();
    }
    @Override
    public void onPause() {
        super.onPause();

        ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).unregisterListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String format(BigDecimal value) {
        DecimalFormat df = new DecimalFormat(",0000.000000");
        return df.format(value);
    }

    private void displaysSensorValues(float[] values) {
        if (values.length >= 1) {
            BigDecimal bd0 = new BigDecimal(Float.toString(values[0]));
            BigDecimal bd1 = new BigDecimal(Float.toString(values[1]));
            BigDecimal bd2 = new BigDecimal(Float.toString(values[2]));
            mTvSensorValueAzimuth.setText(format(bd0));
            mTvSensorValuePitch.setText(format(bd1));
            mTvSensorValueRoll.setText(format(bd2));
        }
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            displaysSensorValues(event.values);
        }
    };

    private void initSensor() {
        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        int sensorType  = Sensor.TYPE_ORIENTATION;
        int sensorDelay = SensorManager.SENSOR_DELAY_FASTEST;

        List<Sensor> list = sensorManager.getSensorList(sensorType);

        for (Sensor sensor : list) {
            if (sensor.getType() == sensorType) {
                sensorManager.registerListener(listener, sensor, sensorDelay);
                break;
            }
        }
    }

    public void enterMainWorld(View v) {
        Log.e("tagBGW", "enterMainWorld c4lled!");
        startActivity(new Intent(BackGroundActivity.this, MainActivity.class));
    }
}
