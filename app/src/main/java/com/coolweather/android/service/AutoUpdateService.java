package com.coolweather.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.util.Log;

import com.coolweather.android.WeatherActivity;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        Log.d("service","服务");
        updateWeather();
        updateBingPic();
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        long time= SystemClock.elapsedRealtime()+1000*60*60*8;
        Intent intent1=new Intent(this,AutoUpdateService.class);
        PendingIntent pendingIntent=PendingIntent.getService(this,0,intent1,0);
        manager.cancel(pendingIntent);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,time,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }


    private void updateWeather(){
     final   SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this);
        String weatherString=sharedPreferences.getString("weather",null);
        if(weatherString!=null){
            Weather weather= Utility.handleWeatherResponse(weatherString);
            String weatherId=weather.basic.weatherId;
            String weatherUrl="http://guolin.tech/api/weather?cityid="+weatherId+"&key=fd6e99cbeae24b3bbfdafb9920bddb19";
            HttpUtil.sendOkHttpRequest(weatherUrl,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String weatherString=response.body().string();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("weather",weatherString);
                    editor.apply();
                }
            });
        }
    }

    private void updateBingPic(){

        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("bing_pic",response.body().toString());
                editor.apply();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
