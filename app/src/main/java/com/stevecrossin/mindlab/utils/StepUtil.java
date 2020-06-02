package com.stevecrossin.mindlab.utils;

import android.content.Context;

/**
 * @ClassName: StepUtil
 * @Description: Pedometer related tools
 * @Author: yanxu5
 * @Date: 2019/8/19
 */
public class StepUtil {

    /**
     * Whether to support step counting
     *
     * @param context
     * @return Whether to support step counting
     */
    public static boolean isSupportStep(Context context) {
        return StepSPHelper.getSupportStep(context);
    }

    /**
     * Today's steps (30000 effective steps per day-> server-side processing)
     *
     * @param context
     * @return Today's steps
     */
    public static int getTodayStep(Context context) {
        return (int) StepSPHelper.getCurrentStep(context);
    }

}
