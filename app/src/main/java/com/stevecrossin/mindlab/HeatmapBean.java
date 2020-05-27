package com.stevecrossin.mindlab;

import com.google.gson.Gson;

import java.util.List;

/*
 * Package    :com.example.heatmapdemo
 * ClassName  :Bean
 * Description:
 * Data       :2020/5/19 9:23
 */
public class HeatmapBean {

  private List<List<String>> s;

  public static HeatmapBean objectFromData(String str) {

    return new Gson().fromJson(str, HeatmapBean.class);
  }

  public List<List<String>> getS() {
    return s;
  }

  public void setS(List<List<String>> s) {
    this.s = s;
  }
}
