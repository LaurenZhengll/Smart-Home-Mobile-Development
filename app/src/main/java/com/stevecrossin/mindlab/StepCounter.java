package com.stevecrossin.mindlab;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.stevecrossin.mindlab.utils.StepService;
import com.stevecrossin.mindlab.utils.StepUtil;
import com.stevecrossin.mindlab.view.CustomMPLineChartMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The chart tool uses a third-party library - MPChart
 * The third-party library used to calculate the number of steps for the day
 * https://github.com/AndroidyxChen/TodaySteps
 * (Because the author did not give the gradle reference method, so only the source code can be copied, and the source code is under the Utils package)
 */
public class StepCounter extends AppCompatActivity implements StepSensorBase.StepCallBack {

    private LineChart lineChart;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    private static String TAG = "StepCounter";
    private ArrayList<Model> list;
    private TextView textView;
    private int steps;
    //List of required permissions
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.READ_SYNC_SETTINGS};
    //List of permissions not yet obtained
    List<String> mPermissionList;
    private final int mRequestCode = 100;//Permission request code
    private StepSensorAcceleration stepSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        //Get alarm clock service. Use it to get the number of steps for the day at around 23.50 minutes every day.
        //When the alarm clock expires, the RepeatService service will be started and the onHandleIntent method will be executed.
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, RepeatService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 50);
        //Set the first start time, each repetition interval is AlarmManager.INTERVAL_DAY, that is, start every other day
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        mPermissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23) {//Only use dynamic permissions in 6.0
            Log.e(TAG, "initPermission");
            // Because you need to open the front desk service to ensure that the service will survive for a long time
            // Since Android 8.0, the front desk service also needs to apply for permission.
            // So when the version is greater than 28 or 8.0, add Manifest.permission.FOREGROUND_SERVICE to the list of required permissions
            if (Build.VERSION.SDK_INT >= 28) {
                permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.READ_SYNC_SETTINGS, Manifest.permission.FOREGROUND_SERVICE};
            }
            initPermission();
        }


        textView = findViewById(R.id.steps_tv);
        //Set the latest steps every time you open
        //If the pedometer sensor is supported, the number of steps is directly obtained from the pedometer sensor
        if (StepUtil.isSupportStep(this)) {
            textView.setText(StepUtil.getTodayStep(this) + "");
        } else {
            //Otherwise, first set to 0 to start the acceleration sensor step
            SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
            steps = sharedPreferences.getInt("steps", 0);
            //Set steps number
            textView.setText(String.valueOf(steps));
            stepSensor = new StepSensorAcceleration(this, this);
            if (!stepSensor.registerStep()) {
                Toast.makeText(this, "The acceleration sensor is also not available!", Toast.LENGTH_SHORT).show();
            }

        }

        lineChart = findViewById(R.id.line_chart);
        //When the database has no data, the text displayed in the chart
        lineChart.setNoDataText("No data for now");
        //Close icon description text
        lineChart.getDescription().setEnabled(false);
        //Set chart grid background color
        lineChart.setGridBackgroundColor(Color.rgb(255, 255, 255));
        //Set whether can touch
        lineChart.setTouchEnabled(true);
        //Set whether you can drag and zoom
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        //The custom marker is the orange icon that appears when you click on a point in the chart
        CustomMPLineChartMarkerView mv = new CustomMPLineChartMarkerView(this);
        //Set the chart associated with the marker
        mv.setChartView(lineChart);
        //Line chart setting marker
        lineChart.setMarker(mv);
        //Get chart X axis object
        XAxis xl = lineChart.getXAxis();

        xl.setPosition(XAxis.XAxisPosition.BOTTOM); // The X-axis data is displayed at the bottom
        xl.setTextSize(10f); // Set font size
        //Get the Y-axis object on the left (you can also get the right)
        YAxis yl = lineChart.getAxisLeft();
        //Disable the right Y axis. Set to true to display the right Y axis
        lineChart.getAxisRight().setEnabled(false);
        //Set the position of the Y axis. The specific type can be Baidu search YAxisLabelPosition
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yl.setTextSize(10f); // Set font size
        yl.setAxisMinValue(90f);
        yl.setStartAtZero(false);
        getData();
        setData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stepSensor.unregisterStep();
    }

    /**
     * Initialize permissions Check already owned permissions and not owned permissions
     * Apply for not having permission
     */
    private void initPermission() {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        //Apply for permission
        if (mPermissionList.size() > 0) {
            Log.e(TAG, "request for access");
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            //All permissions passed, proceed to the next step
            Log.e(TAG, "Already have permission");
            initStepService();
            if (!StepUtil.isSupportStep(this)) {
                //After android4.4, most mobile phones support step sensor
                Log.e(TAG, "The phone does not support step sensor");
                Toast.makeText(this, "Do not support step sensor, acceleration sensor turned on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Permission application callback method
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//Have permission to fail
        if (mRequestCode == requestCode) {
            //Check whether all the applied permissions have failed
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //If permission is not allowed
            if (hasPermissionDismiss) {
                Log.e(TAG, "Permission not allowed");
                //this.finish();
            } else {
                //All permissions passed, proceed to the next step
                Log.e(TAG, "Permission allowed");
                initStepService();
                if (!StepUtil.isSupportStep(this)) {
                    Log.e(TAG, "The phone does not support step sensor");
                    Toast.makeText(this, "Do not support step sensor, acceleration sensor turned on", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Read data from the database
     */
    private void getData() {
        if (helper == null) {
            helper = new MyDBHelper(this);
        }
        db = helper.getReadableDatabase();
        //Descending by time
        Cursor cursor = db.rawQuery("select *from steps order by day_time desc", null);
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }

        Calendar calendar = Calendar.getInstance();
        //Move to the last piece of data, because when drawing the chart, you should start drawing from a small time
        cursor.moveToLast();

        do {
            long time = cursor.getLong(cursor.getColumnIndex("day_time"));
            int steps = cursor.getInt(cursor.getColumnIndex("steps_count"));
            Model model = new Model();
            //Set time format for data Used to display a specific format on the X axis
            Date date = new Date(time);
            calendar.setTime(date);
            //Get the month and day
            model.setDate((calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DAY_OF_MONTH));
            model.setSteps(steps);
            list.add(model);
        } while (cursor.moveToPrevious());
        //Close cursor, database
        cursor.close();
        db.close();
        helper.close();
    }

    /**
     * Initialize pedometer service
     */
    private void initStepService() {
        Intent intent = new Intent(this, StepService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    /**
     * Set up data for the chart
     */
    private void setData() {
        //Entry Equivalent to a pair of X, Y values.
        final ArrayList<Entry> values = new ArrayList<>();
        //For each piece of data in the database, an Entry is added
        for (int i = 0; i < list.size(); i++) {
            Model model = list.get(i);
            //val is Y data
            float val = (float) model.getSteps();
            //Here i is X data. The third parameter is used to change i to a custom X axis coordinate, which is the string of "month. Day"
            values.add(new Entry(i, val, model.getDate()));
        }
        //Get X axis
        XAxis xl = lineChart.getXAxis();
        //Use this formatter to set the X-axis coordinates
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //Get the corresponding Entry by value and then get the value of the third parameter set above. That is getData (). Use it as the X axis coordinate
                return values.get((int) value).getData().toString();
            }
        });
        //Display up to ten data
        xl.setLabelCount(Math.min(list.size(), 10), true);
        //A data set is equivalent to a polyline
        LineDataSet set1;
        //If the chart already has data. Then update the chart data
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            //Otherwise, create new chart data
            //Line name
            set1 = new LineDataSet(values, "Daily steps");
            //Whether to draw the icon
            set1.setDrawIcons(false);
            //Use spaced lines
            set1.enableDashedLine(10f, 5f, 0f);
            //Set line color
            set1.setColor(Color.BLACK);
            //Set the circle color of each point
            set1.setCircleColor(Color.BLACK);
            //Set line width
            set1.setLineWidth(1f);
            //Set circle radius
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setFormLineWidth(1f);
            set1.setFormSize(15.f);
            set1.setValueTextSize(9f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            //Whether to fill the area below the line, that is, the area enclosed by the line and the X axis and Y axis
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                //Starting from the minimum value on the Y axis should be to set the bottom limit of the filled area
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart.getAxisLeft().getAxisMinimum();
                }
            });
            //Set up the data set
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);
            //Set up data for the chart
            lineChart.setData(data);
        }
    }

    @Override
    public void Step(int stepNum) {
        steps += stepNum;
        textView.setText(steps + "");
        SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("steps", steps);
        editor.apply();
    }
}