package fise.feng.com.beautifulwatchlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.event.BatteryStatus;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import android.os.BatteryManager;

/**
 * Created by sean on 8/30/16.
 */
public class BatteryStatusReceiver extends BroadcastReceiver {
    private final static String TAG = "BatteryStatusReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            BatteryStatus event = new BatteryStatus();
            event.level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 100);
            event.scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            event.plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0); // default set as battery
            event.health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,
                    BatteryManager.BATTERY_HEALTH_UNKNOWN);
            event.status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
                    BatteryManager.BATTERY_STATUS_UNKNOWN);
            event.temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            event.voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            event.present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, true);
            event.technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            event.timestamp = System.currentTimeMillis();
            LogUtils.d(TAG, "level=" + event.level + ", scale=" + event.scale + ", plugged=" + event.plugged +
                    ", health=" + event.health + ", status=" + event.status + ", temperature=" + event.temperature +
                    ", voltage=" + event.voltage + ", present=" + event.present + ", technology=" + event.technology);
            StaticManager.instance().setBatteryStatus(event);
        }
    }
}
