package com.door2field.entryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int m_Count = 0;

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
    }

    public void enterSubWorld(View v) {
        Log.e("tag", "    enterSubWorld c4lled!");
        startActivity(new Intent(MainActivity.this, SubActivity.class));
    }

    public void countUp(View v) {
        Log.e("tag","    countUp c4lled!");
        if (m_Count < Integer.MAX_VALUE)
            m_Count++;
        else {
            Log.e("tag","NOTREACHED");
            Log.e("tag","m_Count is too large.");
        }
        TextView countView = (TextView) findViewById(R.id.myTextView2CountUp);
        countView.setText(String.valueOf(m_Count));
    }

    public void countDown(View v) {
        Log.e("tag", "    countDown c4lled!");
        if (m_Count > 0)
            m_Count--;
        TextView countView = (TextView) findViewById(R.id.myTextView2CountUp);
        countView.setText(String.valueOf(m_Count));
    }

    public void resetCounter(View v) {
        Log.e("tag", "    resetCounter c4lled!");
        m_Count = 0;
        TextView countView = (TextView) findViewById(R.id.myTextView2CountUp);
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
}
