package com.coolweather.android.gson;

/**
 * Created by 解如辉 on 2018/10/17.
 */

public class AQI {

    public AQICity city;

    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
