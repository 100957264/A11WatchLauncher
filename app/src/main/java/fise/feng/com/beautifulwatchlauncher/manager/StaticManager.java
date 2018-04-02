package fise.feng.com.beautifulwatchlauncher.manager;

import android.content.Context;
import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.KApplicationContext;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.aty.ItemSettingsActivity;
import fise.feng.com.beautifulwatchlauncher.constant.FragmentConstant;
import fise.feng.com.beautifulwatchlauncher.entity.SettingsEntity;
import fise.feng.com.beautifulwatchlauncher.entity.SosNumberEntity;
import fise.feng.com.beautifulwatchlauncher.event.BatteryStatus;
import fise.feng.com.beautifulwatchlauncher.event.DataActivityUpdateEvent;
import fise.feng.com.beautifulwatchlauncher.event.NetworkTypeEvent;
import fise.feng.com.beautifulwatchlauncher.event.SignalStrengthChangedEvent;
import fise.feng.com.beautifulwatchlauncher.event.WifiStateChangedEvent;
import android.telephony.TelephonyManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingfeng on 2018/1/12.
 */

public class StaticManager {
    private static class SingletonHolder {
        private static final StaticManager INSTANCE = new StaticManager();
    }
    public static StaticManager instance() {
        return SingletonHolder.INSTANCE;
    }
    public static String CURRENTSTATE = "";
    public static String IMEI = "";
    public void initIMEI(){
        TelephonyManager telephonyManager = (TelephonyManager) KApplicationContext.sContext.getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();
    }
    public Intent getIntentById(Context context, int id){
        Intent intent = null;
        switch (id){
            case 0:
                intent = new Intent(context, ItemSettingsActivity.class);
                intent.putExtra(FragmentConstant.FRAGMENT_ID, FragmentConstant.FM_BT);
                break;
            case 1:
                intent = new Intent(context, ItemSettingsActivity.class);
                intent.putExtra(FragmentConstant.FRAGMENT_ID, FragmentConstant.FM_QR);
                break;
//            case 2:
//                intent = new Intent(context, ItemSettingsActivity.class);
//                intent.putExtra(FragmentConstant.FRAGMENT_ID, FragmentConstant.DOWNLOAD);
        }
        return intent;
    }

    public DataActivityUpdateEvent dataActivityUpdateEvent;
    public SignalStrengthChangedEvent signalStrengthChangedEvent;
    public WifiStateChangedEvent wifiStateChangedEvent;
    public NetworkTypeEvent networkTypeEvent;

    private BatteryStatus batteryStatus;

    public BatteryStatus getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(BatteryStatus batteryStatus) {
        this.batteryStatus = batteryStatus;
        EventBus.getDefault().post(batteryStatus);
    }

    public int getBatteryLevel() {
        if (null != batteryStatus) {
            return batteryStatus.level;
        }
        return 0;
    }

    public int mNoAnswerCount = 0;
    public boolean isOutgoingConnected = false;
    public int mCurrentOutgoingIndex = 0;
    public int sMaxNoAnswerCount = 2;
    public List<SosNumberEntity> outgoingCalls = new ArrayList<>();
    public boolean isSosReady;//是否长按关机键五秒了(报警紧情开关)
    public boolean isSosStart = false;//是否开始紧急呼叫(继续拨打紧急电话而不是直接挂掉标记)

    public List<SosNumberEntity> mContactInfoList = new ArrayList<>();

    public String previousNumber="";
    public String previousName="";
}
