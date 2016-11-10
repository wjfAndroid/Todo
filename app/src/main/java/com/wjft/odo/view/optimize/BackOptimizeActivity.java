package com.wjft.odo.view.optimize;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wjft.odo.R;
import com.wjft.odo.broadcastrecevier.ConnectReceiver;
import com.wjft.odo.service.JobSchedulerSimpleService;
import com.wjft.odo.util.NetworkUtils;

public class BackOptimizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_optimize);

    }


    public static final int jobid = 1;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setJobService() {
        JobScheduler jobScheduler = (JobScheduler) this.getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(jobid, new ComponentName(this, JobSchedulerSimpleService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .build();
        jobScheduler.schedule(jobInfo);
    }

    ConnectReceiver receiver;

    private void getNetState() {
        Log.d("MainActivity", "NetworkUtils.isWifiConnected(this):" + NetworkUtils.isWifiConnected(this));
        Log.d("MainActivity", "NetworkUtils.getNetWorkType(this):" + NetworkUtils.getNetWorkType(this));
        receiver = new ConnectReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(receiver, intentFilter);
    }

    ConnectivityManager manager;
    ConnectivityManager.NetworkCallback callback;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void netWorkRequest() {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                //    .addCapability(NetworkCapabilities.NET_CAPABILITY_WIFI_P2P)
                .build();
        manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        callback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                System.out.println("network = [" + network + "]");
            }
        };
        manager.registerNetworkCallback(networkRequest, callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        this.unregisterReceiver(receiver);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager.unregisterNetworkCallback(callback);
        }
        ;
    }
}
