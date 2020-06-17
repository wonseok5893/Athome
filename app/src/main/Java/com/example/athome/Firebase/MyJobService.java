package com.example.athome.Firebase;

import android.app.job.JobParameters;
import android.util.Log;

import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {
    private static final String TAG = "jiwon";

    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
        Log.d(TAG, "Performing long running task in scheduled job");
        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}
