package fise.feng.com.beautifulwatchlauncher.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;

import fise.feng.com.beautifulwatchlauncher.event.BTStateChangeEvent;
import fise.feng.com.beautifulwatchlauncher.event.BlueSocketStatusEvent;
import fise.feng.com.beautifulwatchlauncher.manager.PreferControler;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.receiver.BatteryStatusReceiver;
import fise.feng.com.beautifulwatchlauncher.socket.BlueSocketBaseThread;
import fise.feng.com.beautifulwatchlauncher.socket.BluetoothSppHelper;
import fise.feng.com.beautifulwatchlauncher.socket.message.IMessage;
import fise.feng.com.beautifulwatchlauncher.socket.message.ImageMessage;
import fise.feng.com.beautifulwatchlauncher.socket.message.StringMessage;
import fise.feng.com.beautifulwatchlauncher.thread.HeartBeatThread;
import fise.feng.com.beautifulwatchlauncher.util.BluetoothUtils;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.util.NotificationUtils;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by qingfeng on 2018/1/17.
 */

public class BluetoothManagerService extends Service implements BluetoothSppHelper.BlueSocketListener {

    private String currentStatus = "";
    BatteryStatusReceiver batteryStatusReceiver;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("onCreate...start BluetoothManagerService");
        if (BluetoothUtils.isBluetoothOn()) {
            BluetoothSppHelper.instance().strat();
            BluetoothSppHelper.instance().setBlueSocketListener(this);
        }
        EventBus.getDefault().register(this);

        batteryStatusReceiver = new BatteryStatusReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryStatusReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_STICKY;
    }

    @Override
    public void onBlueSocketStatusChange(BlueSocketBaseThread.BlueSocketStatus status, BluetoothDevice remoteDevice) {
        currentStatus = status.toString();
        LogUtils.e("currentStatus =" + currentStatus);
        StaticManager.CURRENTSTATE = currentStatus;
        PreferControler.instance().setKeyConnectState(currentStatus);
        if(currentStatus.equals("DISCONNECTION")){
            HeartBeatThread.instance().onErrorConnected();
            BlueSocketStatusEvent blueSocketStatusEvent = new BlueSocketStatusEvent(status.toString());
            EventBus.getDefault().post(blueSocketStatusEvent);
        }else if(currentStatus.equals("CONNEDTIONED")){
            BlueSocketStatusEvent blueSocketStatusEvent = new BlueSocketStatusEvent(status.toString());
            EventBus.getDefault().post(blueSocketStatusEvent);
        }
    }

    @Override
    public void onBlueSocketMessageReceiver(IMessage message) {
        LogUtils.e("message comming");
        if (message instanceof StringMessage){
            NotificationUtils.recvMsg((StringMessage)message);
        }else if (message instanceof ImageMessage){
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BTStateChangeEvent(BTStateChangeEvent event) {
        LogUtils.e("isBTON = " + event.isBTOn);
        if(event.isBTOn){
            BluetoothSppHelper.instance().strat();
            BluetoothSppHelper.instance().setBlueSocketListener(this);
        }else {
            BluetoothSppHelper.instance().stop();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Intent intent = new Intent(this,BluetoothManagerService.class);
        startService(intent);
    }
}
