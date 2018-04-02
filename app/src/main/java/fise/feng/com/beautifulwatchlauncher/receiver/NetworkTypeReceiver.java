package fise.feng.com.beautifulwatchlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.event.NetworkTypeEvent;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;


import org.greenrobot.eventbus.EventBus;

/**
 * Created by qingfeng on 2017/9/26.
 */

public class NetworkTypeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        NetworkTypeEvent event = new NetworkTypeEvent();
        if (action.equals("com.fise.intent.ACTION_NETWORK_TYPE")) {
            event.networkType = intent.getIntExtra("network_type", 13);
            LogUtils.i("NetworkTypeReceiver networkType =" + event.networkType);
        } else if (action.equals("com.fise.intent.ACTION_NO_SIM")) {
            event.isSimAbsent = true;
        }
        EventBus.getDefault().post(event);
    }
}
