package com.stevecrossin.mindlab;

public class HeartRateAnalysis {
    private int age, heartRate, restingMaxHeartRate, restingMinHeartRate, exerciseMaxHeartRate, exerciseMinHeartRate, avgMaxHeartRate;

    public HeartRateAnalysis(int age, int heartRate) {
        this.age = age;
        this.heartRate = heartRate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getRestingMaxHeartRate() {
        return restingMaxHeartRate;
    }

    public void setRestingMaxHeartRate(int restingMaxHeartRate) {
        this.restingMaxHeartRate = restingMaxHeartRate;
    }

    public int getRestingMinHeartRate() {
        return restingMinHeartRate;
    }

    public void setRestingMinHeartRate(int restingMinHeartRate) {
        this.restingMinHeartRate = restingMinHeartRate;
    }

    public int getExerciseMaxHeartRate() {
        return exerciseMaxHeartRate;
    }

    public void setExerciseMaxHeartRate(int exerciseMaxHeartRate) {
        this.exerciseMaxHeartRate = exerciseMaxHeartRate;
    }

    public int getExerciseMinHeartRate() {
        return exerciseMinHeartRate;
    }

    public void setExerciseMinHeartRate(int exerciseMinHeartRate) {
        this.exerciseMinHeartRate = exerciseMinHeartRate;
    }

    public int getAvgMaxHeartRate() {
        return avgMaxHeartRate;
    }

    public void setAvgMaxHeartRate(int avgMaxHeartRate) {
        this.avgMaxHeartRate = avgMaxHeartRate;
    }

    public void dataSet() {
        restingMaxHeartRate = 100;
        restingMinHeartRate = 60;
        if (age >= 20 && age < 30) {
            exerciseMaxHeartRate = 170;
            exerciseMinHeartRate = 100;
            avgMaxHeartRate = 200;
        } else if (age >= 30 && age < 35) {
            exerciseMaxHeartRate = 162;
            exerciseMinHeartRate = 95;
            avgMaxHeartRate = 190;
        } else if (age >= 35 && age < 40) {
            exerciseMaxHeartRate = 157;
            exerciseMinHeartRate = 93;
            avgMaxHeartRate = 185;
        } else if (age >= 40 && age < 45) {
            exerciseMaxHeartRate = 153;
            exerciseMinHeartRate = 90;
            avgMaxHeartRate = 180;
        } else if (age >= 45 && age < 50) {
            exerciseMaxHeartRate = 149;
            exerciseMinHeartRate = 88;
            avgMaxHeartRate = 175;
        } else if (age >= 50 && age < 55) {
            exerciseMaxHeartRate = 145;
            exerciseMinHeartRate = 85;
            avgMaxHeartRate = 170;
        } else if (age >= 55 && age < 60) {
            exerciseMaxHeartRate = 140;
            exerciseMinHeartRate = 83;
            avgMaxHeartRate = 165;
        } else if (age >= 60 && age < 65) {
            exerciseMaxHeartRate = 136;
            exerciseMinHeartRate = 80;
            avgMaxHeartRate = 160;
        } else if (age >= 65 && age < 70) {
            exerciseMaxHeartRate = 132;
            exerciseMinHeartRate = 78;
            avgMaxHeartRate = 155;
        } else if (age >= 70) {
            exerciseMaxHeartRate = 128;
            exerciseMinHeartRate = 75;
            avgMaxHeartRate = 150;
        }
    }

    public int restingDataAnalysis() {
        if (heartRate <= restingMaxHeartRate && heartRate >= restingMinHeartRate) {
            return 1;
        } else if (heartRate < restingMinHeartRate) {
            return 2;
        } else if (heartRate > restingMaxHeartRate) {
            return 3;
        }
        return 0;
    }

    public int exerciseDataAnalysis() {
        if (heartRate <= exerciseMaxHeartRate && heartRate >= exerciseMinHeartRate) {
            return 1;
        } else if (heartRate < exerciseMinHeartRate) {
            return 2;
        } else if (heartRate > exerciseMaxHeartRate) {
            return 3;
        }
        return 0;
    }
}
