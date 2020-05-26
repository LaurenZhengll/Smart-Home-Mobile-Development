package com.stevecrossin.mindlab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HeartRate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_heart_rate);

        Button button = (Button) findViewById(R.id.button);
        final EditText age = (EditText) findViewById(R.id.age);
        final EditText heartRate = (EditText) findViewById(R.id.heartRate);
        final TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(age.getText().toString()) < 20) {
                    resultTextView.setText("This app is only designed for people over 20 years old.");
                } else {
                    HeartRateAnalysis heartRateData = new HeartRateAnalysis(Integer.parseInt(age.getText().toString()), Integer.parseInt(heartRate.getText().toString()));
                    heartRateData.dataSet();
                    showResult(heartRateData);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showResult(HeartRateAnalysis heartRateData) {
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        if (spinner.getSelectedItem().toString().equals("Resting")) {
            switch (heartRateData.restingDataAnalysis()) {
                case 1:
                    resultTextView.setText("Your resting heart rate is normal.");
                    break;
                case 2:
                    resultTextView.setText("Your resting heart rate is too slow.\nNormal heart rate should be " + heartRateData.getRestingMinHeartRate() + "-" + heartRateData.getRestingMaxHeartRate());
                    break;
                case 3:
                    resultTextView.setText("Your resting heart rate is too fast.\nNormal heart rate should be " + heartRateData.getRestingMinHeartRate() + "-" + heartRateData.getRestingMaxHeartRate());
                    break;
                default:
                    break;
            }
        } else if (spinner.getSelectedItem().toString().equals("Exercising")) {
            switch (heartRateData.exerciseDataAnalysis()) {
                case 1:
                    resultTextView.setText("Your exercising heart rate is normal.");
                    break;
                case 2:
                    resultTextView.setText("Your exercising heart rate is too slow.\nNormal heart rate should be "
                            + heartRateData.getExerciseMinHeartRate() + "-" + heartRateData.getExerciseMaxHeartRate()
                            + "\nYour maximum exercising heart rate should not more than " + heartRateData.getAvgMaxHeartRate());
                    break;
                case 3:
                    resultTextView.setText("Your exercising heart rate is too fast.\nNormal heart rate should be "
                            + heartRateData.getExerciseMinHeartRate() + "-" + heartRateData.getExerciseMaxHeartRate()
                            + "\nYour maximum exercising heart rate should not more than " + heartRateData.getAvgMaxHeartRate());
                    break;
                default:
                    break;
            }
        }
    }
}