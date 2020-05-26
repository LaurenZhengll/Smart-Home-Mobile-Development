package com.stevecrossin.mindlab;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;
import java.util.Stack;

/**
 * Sensor tool class
 */
public class SensorUtil {
    private static final String TAG = "SensorUtil";
    private static final SensorUtil sensorUtil = new SensorUtil(); // Singleton constant
    private SensorManager sensorManager;

    private static final int SENSE = 10; // Direction difference sensitivity
    private static final int STOP_COUNT = 6; // Number of stops
    private int initialOrient = -1; // Initial direction
    private int endOrient = -1; // Direction of rotation stop

    private boolean isRotating = false; // If turning
    private int lastDOrient = 0; // Difference between last direction and initial direction
    private Stack<Integer> dOrientStack = new Stack<>(); // Difference stack of historical direction and initial direction

    private SensorUtil() {
    }

    /**
     * Singleton acquisition
     */
    public static SensorUtil getInstance() {
        return sensorUtil;
    }

    /**
     * Get an instance of the sensor management class
     */
    public SensorManager getSensorManager(Context context) {
        if (sensorManager == null) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        return sensorManager;
    }

    /**
     * Print all available sensors
     */
    public void printAllSensor(Context context) {
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensorList) {
            Log.d(TAG, "All available sensors----: " + sensor.getName());
        }
    }

    /**
     * Get the direction in which the phone's rotation stops
     *
     * @param orient Mobile phone real-time direction
     */
    public int getRotateEndOrient(int orient) {
        if (initialOrient == -1) {
            // Initial rotation
            endOrient = initialOrient = orient;
            Log.i(TAG, "getRotateEndOrient: Initialization, direction：" + initialOrient);
        }

        int currentDOrient = Math.abs(orient - initialOrient); // Difference between current direction and initial direction
        if (!isRotating) {
            // Detect whether it starts to rotate
            lastDOrient = currentDOrient;
            if (lastDOrient >= SENSE) {
                // Start turning
                isRotating = true;
            }
        } else {
            // Check if it stops rotating
            if (currentDOrient <= lastDOrient) {
                // At least STOP_COUNT times of the current direction difference is less than the last direction difference
                int size = dOrientStack.size();
                if (size >= STOP_COUNT) {
                    // Only if the difference between the previous SENSE secondary direction gap and the current gap is less than or equal to SENSE, it will be judged as stopped
                    for (int i = 0; i < size; i++) {
                        if (Math.abs(currentDOrient - dOrientStack.pop()) >= SENSE) {
                            isRotating = true;
                            break;
                        }
                        isRotating = false;
                    }
                }

                if (!isRotating) {
                    // Stop turning
                    dOrientStack.clear();
                    initialOrient = -1;
                    endOrient = orient;
                    Log.i(TAG, "getRotateEndOrient: Stop turning, direction------：" + endOrient);
                } else {
                    // Turning, put the difference between the current direction and the initial direction on the stack
                    dOrientStack.push(currentDOrient);
                    Log.i(TAG, "getRotateEndOrient: Turning, direction：" + orient);
                }
            } else {
                lastDOrient = currentDOrient;
            }
        }
        return endOrient;
    }
}