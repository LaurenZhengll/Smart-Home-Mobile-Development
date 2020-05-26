package com.stevecrossin.mindlab;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Accelerometer Sensor
 */
public class StepSensorAcceleration extends StepSensorBase {
    private final String TAG = "StepSensorAcceleration";
    //Store three-axis data
    final int valueNum = 5;
    //Used to store the difference between the peak and valley of the calculated threshold
    float[] tempValue = new float[valueNum];
    int tempCount = 0;
    //Whether the flag is rising
    boolean isDirectionUp = false;
    //Continuous rise times
    int continueUpCount = 0;
    //The number of continuous rises from the previous point, in order to record the number of rises of the crest
    int continueUpFormerCount = 0;
    //The state of the previous point, rising or falling
    boolean lastStatus = false;
    //crest value
    float peakOfWave = 0;
    //Trough value
    float valleyOfWave = 0;
    //Time of the crest
    long timeOfThisPeak = 0;
    //Time of last crest
    long timeOfLastPeak = 0;
    //Current time
    long timeOfNow = 0;
    //Current sensor value
    float gravityNew = 0;
    //Last sensor value
    float gravityOld = 0;
    //Dynamic threshold requires dynamic data, and this value is used for the threshold of these dynamic data
    final float initialValue = (float) 1.7;
    //Initial threshold
    float ThreadValue = (float) 2.0;

    //Initial range
    float minValue = 11f;
    float maxValue = 19.6f;

    /**
     * 0-Ready to count 1- During counting 2- Normal counting
     */
    private int CountTimeState = 0;
    public static int TEMP_STEP = 0;
    private int lastStep = -1;
    //The average value calculated by the three dimensions of x, y and z
    public static float average = 0;
    private Timer timer;
    // Countdown for 3.5 seconds, no pedometer will be displayed within 3.5 seconds, used to mask minor fluctuations
    private long duration = 3500;
    private TimeCount time;

    public StepSensorAcceleration(Context context, StepCallBack stepCallBack) {
        super(context, stepCallBack);
    }

    @Override
    protected void registerStepListener() {
        // Registered acceleration sensor
        isAvailable = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        if (isAvailable) {
            Log.i(TAG, "Accelerometer available!");
        } else {
            Log.i(TAG, "Accelerometer not available!");
        }
    }

    @Override
    public void unregisterStep() {
        sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        synchronized (this) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                calc_step(event);
            }
        }
    }

    synchronized private void calc_step(SensorEvent event) {
        average = (float) Math.sqrt(Math.pow(event.values[0], 2)
                + Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2));
        detectorNewStep(average);
    }

    /*
     * Detect steps and start counting steps
     * 1. The data passed into the sensor
     * 2. If the peak is detected, and the conditions of time difference and threshold are met, it is judged as 1 step
     * 3. Meet the time difference condition, the difference between peak and valley is greater than initialValue,
     * the difference is included in the calculation of the threshold
     * */
    public void detectorNewStep(float values) {
        if (gravityOld == 0) {
            gravityOld = values;
        } else {
            if (DetectorPeak(values, gravityOld)) {
                timeOfLastPeak = timeOfThisPeak;
                timeOfNow = System.currentTimeMillis();

                if (timeOfNow - timeOfLastPeak >= 200
                        && (peakOfWave - valleyOfWave >= ThreadValue) && (timeOfNow - timeOfLastPeak) <= 2000) {
                    timeOfThisPeak = timeOfNow;
                    //Update interface processing, no algorithm involved
                    preStep();
                }
                if (timeOfNow - timeOfLastPeak >= 200
                        && (peakOfWave - valleyOfWave >= initialValue)) {
                    timeOfThisPeak = timeOfNow;
                    ThreadValue = Peak_Valley_Thread(peakOfWave - valleyOfWave);
                }
            }
        }
        gravityOld = values;
    }

    private void preStep() {
        StepSensorBase.CURRENT_SETP++;
        stepCallBack.Step(StepSensorBase.CURRENT_SETP);
    }


    /*
     * Detection peak
     * The following four conditions are judged as peaks:
     * 1. The current point is a downward trend: isDirectionUp is false
     * 2. The previous point is an upward trend: lastStatus is true
     * 3. Until the peak, continue to rise more than or equal to 2 times
     * 4. The wave peak value is greater than 1.2g and less than 2g
     * Record the trough value
     * 1. Observe the waveform graph, you can find that where the step appears, the next wave valley is the peak, which has obvious characteristics and differences
     * 2. So record the trough value of each time, in order to compare with the next crest
     * */
    public boolean DetectorPeak(float newValue, float oldValue) {
        lastStatus = isDirectionUp;
        if (newValue >= oldValue) {
            isDirectionUp = true;
            continueUpCount++;
        } else {
            continueUpFormerCount = continueUpCount;
            continueUpCount = 0;
            isDirectionUp = false;
        }

        if (!isDirectionUp && lastStatus
                && (continueUpFormerCount >= 2 && (oldValue >= minValue && oldValue < maxValue))) {
            peakOfWave = oldValue;
            return true;
        } else if (!lastStatus && isDirectionUp) {
            valleyOfWave = oldValue;
            return false;
        } else {
            return false;
        }
    }

    /*
     * Threshold calculation
     * 1. Calculate the threshold by the difference between the peaks and troughs
     * 2. Record 4 values and store in tempValue [] array
     * 3. Calculate the threshold in the array averageValue
     * */
    public float Peak_Valley_Thread(float value) {
        float tempThread = ThreadValue;
        if (tempCount < valueNum) {
            tempValue[tempCount] = value;
            tempCount++;
        } else {
            tempThread = averageValue(tempValue, valueNum);
            for (int i = 1; i < valueNum; i++) {
                tempValue[i - 1] = tempValue[i];
            }
            tempValue[valueNum - 1] = value;
        }
        return tempThread;

    }

    /*
     * Gradient threshold
     * 1. Calculate the mean of the array
     * 2. Gradient the threshold into a range by means of the mean
     * */
    public float averageValue(float value[], int n) {
        float ave = 0;
        for (int i = 0; i < n; i++) {
            ave += value[i];
        }
        ave = ave / valueNum;
        if (ave >= 8) {
//            Log.v(TAG, "over 8");
            ave = (float) 4.3;
        } else if (ave >= 7 && ave < 8) {
//            Log.v(TAG, "7-8");
            ave = (float) 3.3;
        } else if (ave >= 4 && ave < 7) {
//            Log.v(TAG, "4-7");
            ave = (float) 2.3;
        } else if (ave >= 3 && ave < 4) {
//            Log.v(TAG, "3-4");
            ave = (float) 2.0;
        } else {
//            Log.v(TAG, "else");
            ave = (float) 1.7;
        }
        return ave;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // If the timer ends normally, the pedometer starts
            time.cancel();
            StepSensorBase.CURRENT_SETP += TEMP_STEP;
            lastStep = -1;
            Log.v(TAG, "The timer ends normally");

            timer = new Timer(true);
            TimerTask task = new TimerTask() {
                public void run() {
                    if (lastStep == StepSensorBase.CURRENT_SETP) {
                        timer.cancel();
                        CountTimeState = 0;
                        lastStep = -1;
                        TEMP_STEP = 0;
                        Log.v(TAG, "Stop counting steps:" + StepSensorBase.CURRENT_SETP);
                    } else {
                        lastStep = StepSensorBase.CURRENT_SETP;
                    }
                }
            };
            timer.schedule(task, 0, 2000);
            CountTimeState = 2;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (lastStep == TEMP_STEP) {
                Log.v(TAG, "onTick Timing stop:" + TEMP_STEP);
                time.cancel();
                CountTimeState = 0;
                lastStep = -1;
                TEMP_STEP = 0;
            } else {
                lastStep = TEMP_STEP;
            }
        }
    }
}