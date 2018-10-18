package com.coolweather.android.util;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by 解如辉 on 2018/10/17.
 */

public class Utility {
    public static boolean handleProvinceResponse(String response){
        try{
            JSONArray jsonArray=new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Province province=new Province();
                province.setProvinceName(jsonObject.getString("name"));
                province.setProvinceCode(jsonObject.getInt("id"));
                province.save();
            }
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return false;
    }
    public static boolean handleCityResponse(String response,int provinceId){
        try{
            JSONArray jsonArray=new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                City city=new City();
                city.setCityCode(jsonObject.getInt("id"));
                city.setCityName(jsonObject.getString("name"));
                city.setProvinceId(provinceId);
                city.save();
            }
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    public static boolean handleCountyResponse(String response,int cityId){
        try{
            JSONArray jsonArray=new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject =jsonArray.getJSONObject(i);
                County county=new County();
                county.setCityId(cityId);
                county.setCountyName(jsonObject.getString("name"));
                county.setWeatherId(jsonObject.getString("weather_id"));
                county.save();
            }
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
