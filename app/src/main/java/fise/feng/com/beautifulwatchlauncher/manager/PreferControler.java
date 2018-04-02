package fise.feng.com.beautifulwatchlauncher.manager;

import android.content.Context;
import android.content.SharedPreferences;
import fise.feng.com.beautifulwatchlauncher.KApplicationContext;


/**
 * @author mare
 * @PS :同一次commit进行的操作如果含有clear()操作，则先执行clear()再执行其他，与代码前后顺序没有关系。
 * @Description: config of preference
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/8/25
 * @time 16:23
 */
public class PreferControler {
    public static final String KEY_PREFS = "prefer_controlller";
    public static final String KEY_CLOCK_INDEX = "prefer_colck_index";
    public static final String KEY_BT = "prefer_bt_key";
    public static final String KEY_MMS = "prefer_mms_key";
    public static final String KEY_NOTI = "prefer_noti_key";
    public static final String KEY_PHONE = "prefer_phone_key";
    public static final String KEY_VIEW_PAGE = "prefer_view_page_key";
    public static final String KEY_CONNECT_STATE = "prefer_connect_state";


    private PreferControler() {
    }

    private static class SingletonHolder {
        private static final PreferControler INSTANCE = new PreferControler();
    }

    public static PreferControler instance() {
        return SingletonHolder.INSTANCE;
    }

    private SharedPreferences getSharedPreferences(String key) {
        SharedPreferences sharedPreferences = KApplicationContext.sContext.getSharedPreferences(key,
                Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    private String getString(String keyPreference, String keyValue, String defaultValue) {
        SharedPreferences settings = getSharedPreferences(keyPreference);
        return settings.getString(keyValue, defaultValue);
    }

    private int getInt(String keyPreference, String keyValue, int defaultValue) {
        SharedPreferences settings = getSharedPreferences(keyPreference);
        return settings.getInt(keyValue, defaultValue);
    }

    private long getLong(String keyPreference, String keyValue, long defaultValue) {
        SharedPreferences settings = getSharedPreferences(keyPreference);
        return settings.getLong(keyValue, defaultValue);
    }

    private boolean getBoolean(String keyPreference, String keyValue, boolean defaultValue) {
        SharedPreferences settings = getSharedPreferences(keyPreference);
        return settings.getBoolean(keyValue, defaultValue);
    }

    private boolean putInt(String keyPreference, String key, int value) {
        SharedPreferences settings = getSharedPreferences(keyPreference);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    private boolean putLong(String keyPreference, String key, long value) {
        SharedPreferences settings = getSharedPreferences(keyPreference);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    private boolean putBoolean(String keyPreference, String key, boolean value) {
        SharedPreferences settings = getSharedPreferences(keyPreference);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    private boolean putString(String keyPreference, String key, String value) {
        SharedPreferences settings = getSharedPreferences(keyPreference);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    public int getClockIndex(){
        return getInt(KEY_PREFS,KEY_CLOCK_INDEX,0);
    }
    public void  setClockIndex(int index){
        putInt(KEY_PREFS,KEY_CLOCK_INDEX,index);
    }

    public boolean getBTSwitch(){
        return getBoolean(KEY_PREFS,KEY_BT,true);
    }
    public void setBTSwitch(boolean isBTOn){
        putBoolean(KEY_PREFS,KEY_BT,isBTOn);
    }
    public boolean getMMSwitch(){
        return getBoolean(KEY_PREFS,KEY_MMS,true);
    }
    public void setMMSwitch(boolean isMMSOn){
        putBoolean(KEY_PREFS,KEY_MMS,isMMSOn);
    }
    public boolean getNotiwitch(){
        return getBoolean(KEY_PREFS,KEY_NOTI,true);
    }
    public void setNOTIwitch(boolean isNotiOn){
        putBoolean(KEY_PREFS,KEY_NOTI,isNotiOn);
    }
    public boolean getPhonewitch(){
        return getBoolean(KEY_PREFS,KEY_PHONE,true);
    }
    public void setPhonewitch(boolean isMMSOn){
        putBoolean(KEY_PREFS,KEY_PHONE,isMMSOn);
    }

    public void setViewPage(int persion){
        putInt(KEY_PREFS,KEY_VIEW_PAGE,persion);
    }
    public int getPagePersion(){
        return getInt(KEY_PREFS,KEY_VIEW_PAGE,1);
    }

    public void setKeyConnectState(String state){
        putString(KEY_PREFS,KEY_CONNECT_STATE,state);
    }
    public String getKeyConnectState(){
        return getString(KEY_PREFS,KEY_CONNECT_STATE,"DISCONNECTION");
    }
}

