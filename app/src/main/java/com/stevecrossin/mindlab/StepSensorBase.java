package com.stevecrossin.mindlab;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class StepSensorBase implements SensorEventListener {
    private Context context;
    protected StepCallBack stepCallBack;
    protected SensorManager sensorManager;
    protected static int CURRENT_SETP = 0;
    protected boolean isAvailable = false;

    public StepSensorBase(Context context, StepCallBack stepCallBack) {
        this.context = context;
        this.stepCallBack = stepCallBack;
    }

    public interface StepCallBack {
        /**
         * Step counting callback
         */
        void Step(int stepNum);
    }

    /**
     * Start step counting
     */
    public boolean registerStep() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            sensorManager = null;
        }
        sensorManager = SensorUtil.getInstance().getSensorManager(context);
        registerStepListener();
        return isAvailable;
    }

    /**
     * Register step listener
     */
    protected abstract void registerStepListener();

    /**
     * Unregister step
     */
    public abstract void unregisterStep();
}
