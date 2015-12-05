package com.door2field.entryapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateService extends Service {
    public static final String INTENT_ACTION = "myService_Update";
    private Timer timer;
    private Handler handler;

    public UpdateService() {
        Log.e("tagUpdateService", "UpdateService c4lled!");
    }

    @Override
    public void onCreate() {
        Log.e("tagUpdateService", "onCreate c4lled!");
        super.onCreate();
        handler = new Handler();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("tagUpdateService", "onStart c4lled!");
        super.onStart(intent, startId);
        updateTask(150);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("tagUpdateService", "onBind c4lled!");
        return ImyServiceBinder;
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    public void updateTask(long period) {
        Log.e("tagUpdateService", "updateTask c4lled!");

        if (timer != null)
            timer.cancel();

        timer = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        sendBroadcast(new Intent(INTENT_ACTION));
                    }
                });
            }
        };
        timer.schedule(task, 0, period);
    }

    private final ImyService.Stub ImyServiceBinder = new ImyService.Stub() {
        public void stopService() throws RemoteException {
            Log.e("tagUpdateService", "stopService c4lled!");
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    };
}
