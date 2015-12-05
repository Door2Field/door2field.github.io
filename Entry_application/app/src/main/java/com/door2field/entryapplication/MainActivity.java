package com.door2field.entryapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int m_Count = 0;

    private ImyService binder;

    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("tagMainW", "onServiceConnected c4lled!");
            binder = ImyService.Stub.asInterface(service);
        }
        public void onServiceDisconnected(ComponentName name) {
            Log.e("tagMainW", "onServiceDisconnected c4lled!");
            binder = null;
        }
    };

    private TextView mTvSensorValue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button start_btn = (Button)findViewById(R.id.myButton2StartService);
        start_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateService.class);
                startService(intent);
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });

        Button stop_btn = (Button)findViewById(R.id.myButton2StopService);
        stop_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (binder != null) {
                        binder.stopService();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mTvSensorValue = (TextView)findViewById(R.id.MyTextView4SensorValue);
    }

    public void enterSubWorld(View v) {
        Log.e("tagMainW", "enterSubWorld c4lled!");
        startActivity(new Intent(MainActivity.this, SubActivity.class));
    }

    public void enterBGWorld(View v) {
        Log.e("tagMainW", "enterBGWorld c4lled!");
        startActivity(new Intent(MainActivity.this, BackGroundActivity.class));
    }

    public void countUp(View v) {
        Log.e("tagMainW", "countUp c4lled!");
        if (m_Count < Integer.MAX_VALUE)
            m_Count++;
        else {
            Log.e("tagMainW", "NOTREACHED");
            Log.e("tagMainW", "m_Count is too large.");
        }
        TextView countView = (TextView) findViewById(R.id.myTextView4Counter);
        countView.setText(String.valueOf(m_Count));
    }

    public void countDown(View v) {
        Log.e("tagMainW", "countDown c4lled!");
        if (m_Count > 0)
            m_Count--;
        TextView countView = (TextView) findViewById(R.id.myTextView4Counter);
        countView.setText(String.valueOf(m_Count));
    }

    public void resetCounter(View v) {
        Log.e("tagMainW", "resetCounter c4lled!");
        m_Count = 0;
        TextView countView = (TextView) findViewById(R.id.myTextView4Counter);
        countView.setText(String.valueOf(m_Count));
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

        @Override
        public void onSensorChanged(SensorEvent event) {
            displaysSensorValues(event.values);
        }
    };

    private String format(BigDecimal value) {
        DecimalFormat df = new DecimalFormat(",000.00000");
        return df.format(value);
    }

    private void displaysSensorValues(float[] values) {
        if (values.length >= 1) {
            BigDecimal bd = new BigDecimal(Float.toString(values[0]));
            mTvSensorValue.setText(format(bd));
        }
    }

    @Override
    public void onResume() {
            super.onResume();
            initSensor();
    }

    private void initSensor() {
        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        int sensorType = Sensor.TYPE_ORIENTATION;
        int sensorDelay = SensorManager.SENSOR_DELAY_FASTEST;

        List<Sensor> list = sensorManager.getSensorList(sensorType);

        for (Sensor sensor : list) {
            if (sensor.getType() == sensorType) {
                sensorManager.registerListener(listener, sensor, sensorDelay);
                break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).unregisterListener(listener);
    }
}
