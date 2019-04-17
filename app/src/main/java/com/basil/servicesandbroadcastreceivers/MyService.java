package com.basil.servicesandbroadcastreceivers;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    IBinder mIBinder = new LocalBinder();

    boolean reBinded;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
    }

    /*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 10; i++){
            Toast.makeText(this, "" + i, Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Service Binded", Toast.LENGTH_SHORT).show();
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "Service Unbinded", Toast.LENGTH_SHORT).show();
        return reBinded;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }

    int getNum(){
        return 15+20;
    }

    class LocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }
}
