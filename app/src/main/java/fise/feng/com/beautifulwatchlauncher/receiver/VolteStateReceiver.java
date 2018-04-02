package fise.feng.com.beautifulwatchlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.event.VolteStatusEvent;


import org.greenrobot.eventbus.EventBus;

/**
 * Created by qingfeng on 2017/9/25.
 */

public class VolteStateReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals("fise.intent.ACTION_VOLTE_ENABLE")){
            EventBus.getDefault().post(new VolteStatusEvent(0));
        }else if(action.equals("fise.intent.ACTION_VOLTE_DISENABLE")) {
            EventBus.getDefault().post(new VolteStatusEvent(1));
        }
    }
}
