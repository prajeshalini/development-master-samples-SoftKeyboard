package com.example.android.softkeyboard;


import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import static com.example.android.softkeyboard.SoftKeyboard.EXTRA_MESSAGE;

/**
 * Created by Shalini Prajesh on 19/3/18.
 */

public class SyncMessageJobService extends JobService {
    private static final String TAG = "SyncMessageJobService";
    @Override
    public boolean onStartJob(JobParameters params) {
        //perform network operation here
        String messages = params.getExtras().getString(EXTRA_MESSAGE);
        Log.d(TAG, "onStartJob: "+messages);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
