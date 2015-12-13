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

    private TextView mTvSensValAzimuth    = null;
    private TextView mTvSensValPitch      = null;
    private TextView mTvSensValRoll       = null;
    private TextView mTvSensValAzimuthDbg = null;
    private TextView mTvSensValPitchDbg   = null;
    private TextView mTvSensValRollDbg    = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg);

        mTvSensValAzimuth    = (TextView)findViewById(R.id.MyTextView4SensValAzimuth);
        mTvSensValPitch      = (TextView)findViewById(R.id.MyTextView4SensValPitch);
        mTvSensValRoll       = (TextView)findViewById(R.id.MyTextView4SensValRoll);
        mTvSensValAzimuthDbg = (TextView)findViewById(R.id.MyTextView4SensValAzimuthDbg);
        mTvSensValPitchDbg   = (TextView)findViewById(R.id.MyTextView4SensValPitchDbg);
        mTvSensValRollDbg    = (TextView)findViewById(R.id.MyTextView4SensValRollDbg);
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

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        final int MATRIX_SIZE = 16;
        float[] inM  = new float[MATRIX_SIZE];
        float[] outM = new float[MATRIX_SIZE];
        float[] tmpM = new float[MATRIX_SIZE];

        final int AXIS_NUM = 3;
        float[] SensValA    = new float[AXIS_NUM];
        float[] SensValMF   = new float[AXIS_NUM];
        float[] SensValO    = new float[AXIS_NUM];
        float[] SensValOdbg = new float[AXIS_NUM];

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch(event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    SensValA = event.values.clone();
                    break;

                case Sensor.TYPE_MAGNETIC_FIELD:
                    SensValMF = event.values.clone();
                    break;

                case Sensor.TYPE_ORIENTATION:
                    SensValOdbg = event.values.clone();
                    break;

                default:
                    break;
            }

            if (SensValA != null && SensValMF != null) {
                SensorManager.getRotationMatrix(inM, tmpM, SensValA, SensValMF);

                /*
                 * @todo
                 * temporary handling
                 */
                SensorManager.remapCoordinateSystem(inM, SensorManager.AXIS_X, SensorManager.AXIS_Y, outM);

                SensorManager.getOrientation(outM, SensValO);
                displaysSensValO(SensValO);
            }

            if (SensValOdbg != null) {
                displaysSensValOdbg(SensValOdbg);
            }
        }
    };

    private void displaysSensValO(float[] values) {
        if (values.length >= 1) {
            BigDecimal bd0 = new BigDecimal(Float.toString((float)(values[0] * 180 / Math.PI)));
            BigDecimal bd1 = new BigDecimal(Float.toString((float)(values[1] * 180 / Math.PI)));
            BigDecimal bd2 = new BigDecimal(Float.toString((float)(values[2] * 180 / Math.PI)));
            mTvSensValAzimuth.setText(format(bd0));
            mTvSensValPitch.setText(format(bd1));
            mTvSensValRoll.setText(format(bd2));
        }
    }

    private void displaysSensValOdbg(float[] values) {
        if (values.length >= 1) {
            BigDecimal bd0 = new BigDecimal(Float.toString(values[0]));
            BigDecimal bd1 = new BigDecimal(Float.toString(values[1]));
            BigDecimal bd2 = new BigDecimal(Float.toString(values[2]));
            mTvSensValAzimuthDbg.setText(format(bd0));
            mTvSensValPitchDbg.setText(format(bd1));
            mTvSensValRollDbg.setText(format(bd2));
        }
    }

    private String format(BigDecimal value) {
        DecimalFormat df = new DecimalFormat(",0000.000000");
        return df.format(value);
    }

    private void initSensor() {
        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        registListener(sensorManager, Sensor.TYPE_ORIENTATION, SensorManager.SENSOR_DELAY_FASTEST);
        registListener(sensorManager, Sensor.TYPE_MAGNETIC_FIELD, SensorManager.SENSOR_DELAY_FASTEST);
        registListener(sensorManager, Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void registListener(SensorManager sensorManager, int sensorType, int sensorDelay) {
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
