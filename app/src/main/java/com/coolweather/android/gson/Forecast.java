package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 解如辉 on 2018/10/17.
 */

public class Forecast {
    public String date;
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
    @SerializedName("temp")
    public Temperature temperature;
    public class Temperature{
        public String max;
        public String min;
    }
}
