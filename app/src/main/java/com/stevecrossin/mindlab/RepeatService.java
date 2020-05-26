package com.stevecrossin.mindlab;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.stevecrossin.mindlab.utils.StepUtil;

import java.util.Date;
import java.util.UUID;

public class RepeatService extends IntentService {
    private MyDBHelper helper;
    private SQLiteDatabase db;

    public RepeatService() {
        super("RepeatService");
    }

    @Override
    public void onCreate() {
        Log.d("RepeatService", "onCreate");
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("RepeatService", "66666666666");
        //Get today steps
        int steps = StepUtil.getTodayStep(this);
        Date date = new Date();
        long time = date.getTime();
        if (helper == null) {
            helper = new MyDBHelper(this);
        }
        //Get writable instance
        db = helper.getWritableDatabase();
        //UUID Produces a string of random values
        db.execSQL("insert into steps values('" + UUID.randomUUID().toString() + "'," + time + ",steps)");
        db.close();
        helper.close();
    }
}
