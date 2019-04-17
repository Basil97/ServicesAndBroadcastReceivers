package com.basil.servicesandbroadcastreceivers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity {

    MyService myService;

    boolean rebind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(READ_CONTACTS)) {
                    requestPermissions(new String[] {READ_CONTACTS}, 456);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 456) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onStartService(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("data", "Some Words");
        //startService(intent);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        NewMessageNotification.notify(this, "Some thing gone wrong", 5);
    }

    public void onStopService(View view) {
        //stopService(new Intent(this, MyService.class));
        if (rebind) {
            unbindService(connection);
            rebind = false;
        }
        NewMessageNotification.cancel(this);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            myService = binder.getService();
            rebind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            rebind = false;
        }
    };

    public void onGet(View view) {
        if (rebind){
            int num = myService.getNum();
            Toast.makeText(this, "" + num, Toast.LENGTH_SHORT).show();
        }
    }
}
