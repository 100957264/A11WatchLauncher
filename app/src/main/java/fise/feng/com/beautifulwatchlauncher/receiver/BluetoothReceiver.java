package fise.feng.com.beautifulwatchlauncher.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.event.BTStateChangeEvent;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qingfeng on 2017/12/21.
 */

public class BluetoothReceiver extends BroadcastReceiver {
    String strPsw = "0123";
    @Override
    public void onReceive(Context context, Intent intent) {
      if(intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
             int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,0);
             LogUtils.d("blueState=" + blueState);
             switch (blueState){
                 case BluetoothAdapter.STATE_ON:
                     BTStateChangeEvent btStateChangeEventOn = new BTStateChangeEvent(true);
                     EventBus.getDefault().post(btStateChangeEventOn);
                     break;
                 case BluetoothAdapter.STATE_OFF:
                     BTStateChangeEvent btStateChangeEventOff = new BTStateChangeEvent(false);
                     EventBus.getDefault().post(btStateChangeEventOff);
                     break;
             }
        }
    }

}
