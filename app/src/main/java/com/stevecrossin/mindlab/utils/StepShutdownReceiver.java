package com.stevecrossin.mindlab.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @ClassName: StepShutdownReceiver
 * @Description: Shutdown broadcast
 * @Author: yanxu5
 * @Date: 2019/8/19
 */
public class StepShutdownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
            StepSPHelper.setShutdown(context, true);
        }
    }

}
