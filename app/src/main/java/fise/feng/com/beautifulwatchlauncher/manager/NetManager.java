package fise.feng.com.beautifulwatchlauncher.manager;

import android.content.Context;
import fise.feng.com.beautifulwatchlauncher.KApplicationContext;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.listener.CustomPhoneStateListener;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import static android.telephony.PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR;
import static android.telephony.PhoneStateListener.LISTEN_CALL_STATE;
import static android.telephony.PhoneStateListener.LISTEN_CELL_LOCATION;
import static android.telephony.PhoneStateListener.LISTEN_DATA_ACTIVITY;
import static android.telephony.PhoneStateListener.LISTEN_DATA_CONNECTION_STATE;
import static android.telephony.PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR;
import static android.telephony.PhoneStateListener.LISTEN_SERVICE_STATE;

/**
 * @author mare
 * @Description:
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/8/9
 * @time 9:34
 */
public class NetManager {

    private NetManager() {
    }

    private static class SingletonHolder {
        private static final NetManager INSTANCE = new NetManager();
    }

    public static NetManager instance() {
        return SingletonHolder.INSTANCE;
    }

    public static final int MAX_LEVEL = 5;
    public static int[] SIGNAL_ICON = new int[]{
            R.drawable.ic_signal_0,
            R.drawable.ic_signal_1,
            R.drawable.ic_signal_2,
            R.drawable.ic_signal_3,
            R.drawable.ic_signal_4,
    };
    public static int[] WIFI_ICON = new int[]{
            R.drawable.ic_wifi_0,
            R.drawable.ic_wifi_1,
            R.drawable.ic_wifi_2,
            R.drawable.ic_wifi_3,
            R.drawable.ic_wifi_4,
    };

    public boolean hasInsertSIM() {
        TelephonyManager tm = (TelephonyManager) KApplicationContext.sContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null && tm.hasIccCard()) {
            return true;
        }
        return false;
    }

    public String getNetworkType(int type) {
        String networkType = "";
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                networkType = "3G";
                break;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                networkType = "E";
                break;
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                networkType = "2G";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                networkType = "4G";
                break;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                networkType = "";
                break;
        }
        return networkType;
    }

    //设置监听器方法
    CustomPhoneStateListener phoneStateListener;

    public void registerTm() {
        //注册监听器，设定不同的监听类型
        if (null == phoneStateListener) {
            Context ctx = KApplicationContext.sContext;
            phoneStateListener = new CustomPhoneStateListener(ctx);
            try {
                TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                        | LISTEN_SERVICE_STATE | LISTEN_MESSAGE_WAITING_INDICATOR | LISTEN_CALL_FORWARDING_INDICATOR
                        | LISTEN_CALL_STATE | LISTEN_DATA_CONNECTION_STATE | LISTEN_DATA_ACTIVITY);
            }catch (SecurityException e){
                LogUtils.d("e =" + e.getCause());
            }
        }
    }

}

