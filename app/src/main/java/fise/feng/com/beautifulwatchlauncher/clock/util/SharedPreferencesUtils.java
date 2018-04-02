package fise.feng.com.beautifulwatchlauncher.clock.util;

import android.content.Context;
import android.content.SharedPreferences;
import fise.feng.com.beautifulwatchlauncher.KApplicationContext;

import java.util.Map;

/**
 * Created by qingfeng on 2017/12/29.
 */

public class SharedPreferencesUtils {
    public static void save(String fileName, Map<String, String> saveMap) {
        SharedPreferences sp = getSharePreferenceInstance(fileName);
        SharedPreferences.Editor editor = sp.edit();
        for (Map.Entry<String, String> entry : saveMap.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.apply();
    }

    public static void sava(String fileName,String key, String s){
        SharedPreferences sp = getSharePreferenceInstance(fileName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, s);
        editor.apply();
    }

    public static String getValue(String fileName, String key, String defaultValue) {
        SharedPreferences sp = getSharePreferenceInstance(fileName);
        return sp.getString(key, defaultValue);
    }

    public static void remove(String fileName) {
        SharedPreferences sp = getSharePreferenceInstance(fileName);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences getSharePreferenceInstance(String fileName) {
        return KApplicationContext.sContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 保存在手机里面的文件吿
     */
    public static final String FILE_NAME = "shared_preferences_date";

    /**
     * 保存数据的方法，我们霿要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context , String key, Object object){
        if(context!=null) {
            String type = object.getClass().getSimpleName();
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            if ("String".equals(type)) {
                editor.putString(key, (String) object);
            } else if ("Integer".equals(type)) {
                editor.putInt(key, (Integer) object);
            } else if ("Boolean".equals(type)) {
                editor.putBoolean(key, (Boolean) object);
            } else if ("Float".equals(type)) {
                editor.putFloat(key, (Float) object);
            } else if ("Long".equals(type)) {
                editor.putLong(key, (Long) object);
            }

            editor.commit();
        }
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取倿
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context , String key, Object defaultObject){
        if(context!=null) {
            String type = defaultObject.getClass().getSimpleName();
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

            if ("String".equals(type)) {
                return sp.getString(key, (String) defaultObject);
            } else if ("Integer".equals(type)) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if ("Boolean".equals(type)) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if ("Float".equals(type)) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if ("Long".equals(type)) {
                return sp.getLong(key, (Long) defaultObject);
            }
        }
        return null;
    }
    public static float getFloatParam(Context context , String key, float defaultValue){
        if(context != null){
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            return sp.getFloat(key, defaultValue);
        }
        return defaultValue;
    }
    public static int getIntParam(Context context , String key, int defaultValue){
        if(context != null){
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            return sp.getInt(key, defaultValue);
        }
        return defaultValue;
    }
}
