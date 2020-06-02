package com.stevecrossin.mindlab.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.SystemClock;

/**
 * @ClassName: StepCounter
 * @Description: Sensor.TYPE_STEP_COUNTER (Android 4.4 and above) Count the steps of the day through the pedometer sensor, no background service is required
 * @Author: yanxu5
 * @Date: 2019/8/19
 */

public class StepCounter implements SensorEventListener {
    private int sOffsetStep;
    private int sCurrStep;
    private String mTodayDate;
    private boolean mIsCleanStep;
    private boolean mIsShutdown;
    private boolean mIsCounterStepReset = true;//Used to identify the first creation of an object
    private Context mContext;
    private boolean mIsSeparate;
    private boolean mIsBoot;

    public StepCounter(Context context, boolean separate, boolean boot) {
        mContext = context;
        mIsSeparate = separate;
        mIsBoot = boot;
        sCurrStep = (int) StepSPHelper.getCurrentStep(mContext);
        mIsCleanStep = StepSPHelper.getCleanStep(mContext);
        mTodayDate = StepSPHelper.getStepToday(mContext);
        sOffsetStep = (int) StepSPHelper.getStepOffset(mContext);
        mIsShutdown = StepSPHelper.getShutdown(mContext);
        boolean isShutdown = shutdownBySystemRunningTime();//When the boot is monitored, it must be turned off.
        if (mIsBoot || isShutdown || mIsShutdown) {
            mIsShutdown = true;
            StepSPHelper.setShutdown(mContext, true);
        }
        initBroadcastReceiver();
        dateChangeCleanStep();
    }

    private void initBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                if (Intent.ACTION_TIME_TICK.equals(intent.getAction())
                        || Intent.ACTION_TIME_CHANGED.equals(intent.getAction())
                        || Intent.ACTION_DATE_CHANGED.equals(intent.getAction())) {
                    dateChangeCleanStep();//Service survival is separated by 0 points
                }
            }
        };
        mContext.registerReceiver(mBatInfoReceiver, filter);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int counterStep = (int) event.values[0];
            if (mIsCleanStep) {
                cleanStep(counterStep);
            } else if (mIsShutdown || shutdownByCounterStep(counterStep)) {
                shutdown(counterStep);//Handling shutdown startup
            }
            sCurrStep = counterStep - sOffsetStep;
            if (sCurrStep < 0) {
                cleanStep(counterStep);//For fault-tolerant processing, the number of steps cannot be less than 0 for any reason, if it is less than 0, it is directly cleared
            }
            StepSPHelper.setCurrentStep(mContext, sCurrStep);
            StepSPHelper.setElapsedRealTime(mContext, SystemClock.elapsedRealtime());
            StepSPHelper.setLastSensorStep(mContext, counterStep);
            dateChangeCleanStep();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void cleanStep(int counterStep) {
        sCurrStep = 0;//Clear the number of steps, return to zero, the highest priority
        sOffsetStep = counterStep;
        StepSPHelper.setStepOffset(mContext, sOffsetStep);
        mIsCleanStep = false;
        StepSPHelper.setCleanStep(mContext, false);
    }

    private void shutdown(int counterStep) {
        int tmpCurrStep = (int) StepSPHelper.getCurrentStep(mContext);
        //Reset the offset
        sOffsetStep = counterStep - tmpCurrStep;
        StepSPHelper.setStepOffset(mContext, sOffsetStep);
        mIsShutdown = false;
        StepSPHelper.setShutdown(mContext, false);
    }

    private boolean shutdownByCounterStep(int counterStep) {
        if (mIsCounterStepReset) {
            //Judge only once
            mIsCounterStepReset = false;
            //Current sensor step count is less than last sensor step count
            if (counterStep < StepSPHelper.getLastSensorStep(mContext)) {
                //If the current sensor step number is less than the last sensor step number, it must be restarted, but it is not used to increase accuracy.
                return true;
            }
        }
        return false;
    }

    private boolean shutdownBySystemRunningTime() {
        //Time recorded locally to determine that a shutdown operation was performed
        if (StepSPHelper.getElapsedRealTime(mContext) > SystemClock.elapsedRealtime()) {
            //If the time of the last run is greater than the current run time, it is judged as restart, but it only increases the accuracy. In extreme cases, continuous restart will not be determined
            return true;
        }
        return false;
    }

    private synchronized void dateChangeCleanStep() {
        //The time is changed to zero, or the callback is separated by 0
        if (!getTodayDate().equals(mTodayDate) || mIsSeparate) {
            mIsCleanStep = true;
            StepSPHelper.setCleanStep(mContext, true);
            mTodayDate = getTodayDate();
            StepSPHelper.setStepToday(mContext, mTodayDate);
            mIsShutdown = false;
            StepSPHelper.setShutdown(mContext, false);
            mIsBoot = false;
            mIsSeparate = false;
            sCurrStep = 0;
            StepSPHelper.setCurrentStep(mContext, sCurrStep);
        }
    }

    private String getTodayDate() {
        return StepDateUtils.getCurrentDate("yyyy-MM-dd");
    }

    /**
     * Whether to cross the zero point, whether to turn on
     *
     * @param separate
     * @param boot
     */
    public void setZeroAndBoot(boolean separate, boolean boot) {
        mIsSeparate = separate;
        mIsBoot = boot;
    }

}
