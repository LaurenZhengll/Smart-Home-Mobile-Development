package com.stevecrossin.mindlab.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @ClassName: StepService
 * @Description: Service of the step of the day
 */
public class StepService extends Service {

    private static final int SAMPLING_PERIOD_US = SensorManager.SENSOR_DELAY_FASTEST; //Sensor refresh rate
    public static final String INTENT_ALARM_0_SEPARATE = "intent_alarm_0_separate";
    public static final String INTENT_BOOT_COMPLETED = "intent_boot_completed";
    private SensorManager mSensorManager;
    private StepCounter mStepCounter; //Sensor.TYPE_STEP_COUNTER pedometer counts the number of steps in the day, no background service is required
    private boolean mIsSeparate = false;
    private boolean mIsBoot = false;
    private int alarmCount;

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        initAlarm();//Turn on zero timing
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
        String CHANNEL_ONE_NAME = "CHANNEL_ONE_ID";
        NotificationChannel notificationChannel = null;
        Notification.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder = new Notification.Builder(this.getApplicationContext()).setChannelId(CHANNEL_ONE_ID);
        } else {
            builder = new Notification.Builder(this.getApplicationContext());
        }
        startForeground(1, builder.build());

        if (null != intent) {
            mIsSeparate = intent.getBooleanExtra(INTENT_ALARM_0_SEPARATE, false);
            mIsBoot = intent.getBooleanExtra(INTENT_BOOT_COMPLETED, false);
        }
        startStepDetector();//Register a sensor
        return START_STICKY;
    }

    private void startStepDetector() {
        //After Android 4.4, if there is a StepDetector, you can use a step counter sensor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && getStepCounter()) {
            addStepCounterListener();
        } else {
            StepSPHelper.setSupportStep(this, false);
        }
    }

    /**
     * Whether with pedometer sensor
     *
     * @return Return result
     */
    private boolean getStepCounter() {
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        boolean isHaveStepCounter = getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);
        return null != countSensor && isHaveStepCounter;
    }

    private void addStepCounterListener() {
        StepSPHelper.setSupportStep(this, true);
        if (null != mStepCounter) {
            mStepCounter.setZeroAndBoot(mIsSeparate, mIsBoot);
            return;
        }
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepCounter = new StepCounter(getApplicationContext(), mIsSeparate, mIsBoot);
        mSensorManager.registerListener(mStepCounter, countSensor, SAMPLING_PERIOD_US);//注册监听
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Define a zero alarm
     */
    public void initAlarm() {
        Intent intent = new Intent(this, StepZeroAlarmReceiver.class);
        intent.setAction("alarm_0_separate");
        PendingIntent pi = PendingIntent.getBroadcast(this, alarmCount++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstTime = SystemClock.elapsedRealtime();//Get current system time
        long systemTime = System.currentTimeMillis();//java.lang.System.currentTimeMillis (), which returns the number of milliseconds that have passed since midnight on January 1, 1970 UTC.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));//The time zone needs to be set here, otherwise there will be a time difference of 8 hours
        calendar.set(Calendar.HOUR_OF_DAY, 0);//Set to 0:00 reminder
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //Selected timing
        long selectTime = calendar.getTimeInMillis();//Calculate the set time
        //If the current time is greater than the set time, then start from the set time the next day
        if (systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }
        long time = selectTime - systemTime;//Calculate the time difference from the current time to the set time
        long alarmTime = firstTime + time;//System current time + time difference
        // Register for the alarm
        AlarmManager am = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        if (am != null) {
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmTime, AlarmManager.INTERVAL_DAY, pi);
        }
    }

}
