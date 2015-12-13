package com.door2field.entryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    private TextView mTvMsg            = null;
    private static int MSG_WHAT        = 1;
    private static int MSG_INTERVAL_US = 100000;
    private String pMsgAll             = null;
    private int MsgLength              = 0;
    private int WordIdx                = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        initMsg();
    }

    public void initMsg() {
        mTvMsg    = (TextView)findViewById(R.id.MyTextView4Message);
        /*
         * @attention
         * should be checked
         */
        pMsgAll   = new String(mTvMsg.getText().toString());
        MsgLength = mTvMsg.length();
        mTvMsg.setText("");
    }

    public void enterMainWorld(View v) {
        Log.e("tagSubW", "enterMainWorld c4lled!");
        startActivity(new Intent(SubActivity.this, MainActivity.class));
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

    public void updateMsg(View v) {
        Log.e("tagSubW", "countUp c4lled!");

        handler.sendEmptyMessage(MSG_WHAT);
    }

    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            if (WordIdx < MsgLength) {
                if (msg.what == MSG_WHAT) {
                    mTvMsg.setText(pMsgAll.substring(0, WordIdx + 1));
                    handler.sendEmptyMessageDelayed(MSG_WHAT, MSG_INTERVAL_US / 1000);
                    WordIdx++;
                } else {
                    super.dispatchMessage(msg);
                }
            } else {
                pMsgAll = null;
            }
        }
    };
}
