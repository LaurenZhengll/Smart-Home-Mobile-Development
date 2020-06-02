package com.stevecrossin.mindlab.utils;

import android.content.Context;

/**
 * @ClassName: StepSPHelper
 * @Description: SharedPreferences Tools
 * @Author: yanxu5
 * @Date: 2019/8/19
 */
public class StepSPHelper {

    // Step count of last pedometer
    private static final String LAST_SENSOR_TIME = "last_sensor_time";
    // Step compensation value, the number of steps returned by each sensor -offset = current step
    private static final String STEP_OFFSET = "step_offset";
    // On the day, used to determine whether to cross
    private static final String STEP_TODAY = "step_today";
    // Clear steps
    private static final String CLEAN_STEP = "clean_step";
    // Current step
    private static final String CURR_STEP = "curr_step";
    // Phone shutdown monitoring
    private static final String SHUTDOWN = "shutdown";
    // System runtime
    private static final String ELAPSED_REAL_TIME = "elapsed_real_time";
    // Whether to support step counting function
    private static final String IS_SUPPORT_STEP = "is_support_step";

    /**
     * Save the last step of the pedometer
     *
     * @param context
     * @param lastSensorStep Last step
     */
    protected static void setLastSensorStep(Context context, float lastSensorStep) {
        StepSharedPreferencesUtil.setParam(context, LAST_SENSOR_TIME, lastSensorStep);
    }

    /**
     * Read the number of steps of the last pedometer
     *
     * @param context
     * @return Step count of last pedometer
     */
    protected static float getLastSensorStep(Context context) {
        return (float) StepSharedPreferencesUtil.getParam(context, LAST_SENSOR_TIME, 0.0f);
    }

    /**
     * Save step compensation value
     *
     * @param context
     * @param stepOffset Compensation value
     */
    protected static void setStepOffset(Context context, float stepOffset) {
        StepSharedPreferencesUtil.setParam(context, STEP_OFFSET, stepOffset);
    }

    /**
     * Read the step compensation value
     *
     * @param context
     * @return Compensation value
     */
    protected static float getStepOffset(Context context) {
        return (float) StepSharedPreferencesUtil.getParam(context, STEP_OFFSET, 0.0f);
    }

    /**
     * Save today's date
     *
     * @param context
     * @param stepToday Today's date
     */
    protected static void setStepToday(Context context, String stepToday) {
        StepSharedPreferencesUtil.setParam(context, STEP_TODAY, stepToday);
    }

    /**
     * Read today's date
     *
     * @param context
     * @return Today's date
     */
    protected static String getStepToday(Context context) {
        return (String) StepSharedPreferencesUtil.getParam(context, STEP_TODAY, "");
    }

    /**
     * Whether to save the clear step count true to clear the step count starts from 0, false no
     *
     * @param context   context
     * @param cleanStep cleanStep
     */
    protected static void setCleanStep(Context context, boolean cleanStep) {
        StepSharedPreferencesUtil.setParam(context, CLEAN_STEP, cleanStep);
    }

    /**
     * Whether to save the number of clear steps true to clear the number of steps, false no
     *
     * @param context context
     * @return boolean
     */
    protected static boolean getCleanStep(Context context) {
        return (boolean) StepSharedPreferencesUtil.getParam(context, CLEAN_STEP, true);
    }

    /**
     * Save current step
     *
     * @param context
     * @param currStep Current step
     */
    protected static void setCurrentStep(Context context, float currStep) {
        StepSharedPreferencesUtil.setParam(context, CURR_STEP, currStep);
    }

    /**
     * Read current step count
     *
     * @param context
     * @return Current step
     */
    protected static float getCurrentStep(Context context) {
        return (float) StepSharedPreferencesUtil.getParam(context, CURR_STEP, 0.0f);
    }

    /**
     * Save to shut down
     *
     * @param context
     * @param shutdown Whether to shut down
     */
    protected static void setShutdown(Context context, boolean shutdown) {
        StepSharedPreferencesUtil.setParam(context, SHUTDOWN, shutdown);
    }

    /**
     * Read whether to shut down
     *
     * @param context
     * @return Whether to shut down
     */
    protected static boolean getShutdown(Context context) {
        return (boolean) StepSharedPreferencesUtil.getParam(context, SHUTDOWN, false);
    }

    /**
     * Save system runtime
     *
     * @param context
     * @param elapsedRealTime System runtime
     */
    protected static void setElapsedRealTime(Context context, long elapsedRealTime) {
        StepSharedPreferencesUtil.setParam(context, ELAPSED_REAL_TIME, elapsedRealTime);
    }

    /**
     * Read system runtime
     *
     * @param context
     * @return System runtime
     */
    protected static long getElapsedRealTime(Context context) {
        return (long) StepSharedPreferencesUtil.getParam(context, ELAPSED_REAL_TIME, 0L);
    }

    /**
     * Whether saving supports step counting
     *
     * @param context
     * @param isSupportStep Whether to support step counting
     */
    protected static void setSupportStep(Context context, boolean isSupportStep) {
        StepSharedPreferencesUtil.setParam(context, IS_SUPPORT_STEP, isSupportStep);
    }

    /**
     * Read whether to support step counting
     *
     * @param context
     * @return Whether to support step counting
     */
    protected static boolean getSupportStep(Context context) {
        return (boolean) StepSharedPreferencesUtil.getParam(context, IS_SUPPORT_STEP, false);
    }

}
