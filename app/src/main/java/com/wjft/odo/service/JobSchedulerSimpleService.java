package com.wjft.odo.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/9.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerSimpleService extends JobService {
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            jobFinished((JobParameters) message.obj,false);
            return true;
        }
    });
    private String TAG = "JobSchedulerSimpleService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called with: " + "");
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob() called with: " + "jobParameters = [" + jobParameters + "]");
        mHandler.sendMessage(Message.obtain(mHandler, 0, jobParameters));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob() called with: " + "jobParameters = [" + jobParameters + "]");
        mHandler.removeMessages(0);
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called with: " + "");
    }
}
