package com.stevecrossin.mindlab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeatMapActivity extends AppCompatActivity {
  private HeatMapView heatmap;
  private Gson        gson;
  private Handler     handler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message msg) {
      return true;
    }
  });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_heat_map);
    heatmap = findViewById(R.id.heatmap);
    gson = new Gson();
    Map<Float, Integer> colorStops = new ArrayMap<>();
    colorStops.put(0.0f, 0xffee42f4);
    colorStops.put(1.0f, 0xffeef442);
    heatmap.setColorStops(colorStops);
    heatmap.setMinimum(0.0);
    heatmap.setMaximum(100.0);

    //        Random rand = new Random();
    //        for (int i = 0; i < 20; i++) {
    //          HeatMapView.DataPoint point = new HeatMapView.DataPoint(rand.nextFloat(), rand.nextFloat(), rand.nextDouble() * 100.0);
    //          Log.e("yxs", "添加数据：" + point.x + "===" + point.y + "===" + point.value);
    //          heatmap.addData(point);
    //        }

    readAssets();
  }

  void readAssets() {

    try {
      String data = parsingInput(getAssets().open("events.log"));
      Log.e("yxss", "数据长度：" + data.length());
      String[] split = data.split("=");
      Log.e("yxss", "数组长度：" + split.length);
      if (split.length > 0) {
        List<HeatMapView.DataPoint> points = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
          if (i % 2 != 0) {
            String s = split[i];
            HeatmapBean heatmapBean = gson.fromJson("{\"s\":" + s + "}", HeatmapBean.class);
            for (List<String> list : heatmapBean.getS()) {
              Log.e("yxs", "获取数据：" + toFloat(list.get(1)) + "===" + toFloat(list.get(2)) + "===" + toDouble(list.get(0)));
              if (points.size() < 500)
                points.add(new HeatMapView.DataPoint(toFloat(list.get(1)), toFloat(list.get(2)), toDouble(list.get(0))));
            }
          }
        }
        Log.e("yxss", "数组长度：" + points.size());
        heatmap.addAllData(points);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private float toFloat(String s) {
    return Float.parseFloat(s) / 9;
  }

  private Double toDouble(String s) {
    return dou(Double.parseDouble(s));
  }

  private double dou(double d) {
    while (d > 99) {
      d = d / 10;
    }
    return d;
  }

  /**
   * InputStream转换String
   * @param inputStream
   * @return
   */
  public static String parsingInput(InputStream inputStream) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      byte[] bytes = new byte[1024];
      int len = 0;
      while ((len = inputStream.read(bytes)) != -1) {
        byteArrayOutputStream.write(bytes, 0, len);
      }
    } catch (IOException e) {

    }
    return byteArrayOutputStream.toString();
  }
}
