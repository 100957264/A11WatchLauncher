package fise.feng.com.beautifulwatchlauncher.listener;

import android.content.Context;

import fise.feng.com.beautifulwatchlauncher.event.DataActivityUpdateEvent;
import fise.feng.com.beautifulwatchlauncher.event.SignalStrengthChangedEvent;
import fise.feng.com.beautifulwatchlauncher.manager.NetManager;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author mare
 * @Description:TODO
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/9/4
 * @time 11:45
 */
public class CustomPhoneStateListener extends PhoneStateListener {
    Context context;

    public CustomPhoneStateListener(Context con) {
        context = con;
    }

    public void onCallForwardingIndicatorChanged(boolean cfi) {
        LogUtils.d("onCallForwardingIndicatorChanged cfi " + cfi);
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                LogUtils.d("onCallStateChanged call not answer");
                break;

            case TelephonyManager.CALL_STATE_RINGING:
                LogUtils.d("onCallStateChanged incoming");
                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:
                LogUtils.d("onCallStateChanged in a call");
                break;
        }
    }

    @Override
    public void onCellLocationChanged(CellLocation location) {
        if (location != null) {
            LogUtils.d("onCellLocationChanged " + location.toString());
        }
    }

    @Override
    public void onDataActivity(int direction) {
        LogUtils.d("onDataActivity direction " + direction);
        DataActivityUpdateEvent event = new DataActivityUpdateEvent(direction);
        StaticManager.instance().dataActivityUpdateEvent = event;
        EventBus.getDefault().post(event);
    }

    @Override
    public void onDataConnectionStateChanged(int state) {
        LogUtils.d("onDataConnectionStateChanged state " + state);

    }

    @Override
    public void onMessageWaitingIndicatorChanged(boolean mwi) {
        LogUtils.d("onMessageWaitingIndicatorChanged mwi " + mwi);
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        LogUtils.d("onServiceStateChanged " + serviceState.toString());

    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        LogUtils.d("onSignalStrengthsChanged level " + signalStrength.getLevel());
        SignalStrengthChangedEvent signalStrengthChangedEvent = new SignalStrengthChangedEvent(signalStrength.getLevel()
                % NetManager.MAX_LEVEL);
        StaticManager.instance().signalStrengthChangedEvent = signalStrengthChangedEvent;
        EventBus.getDefault().post(signalStrengthChangedEvent);
    }

    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        super.onCellInfoChanged(cellInfo);
        LogUtils.d("onCellInfoChanged " + cellInfo.toString());
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        super.onDataConnectionStateChanged(state, networkType);
        LogUtils.d("onDataConnectionStateChanged state" + state + "\t networkType " + networkType);
    }

}
