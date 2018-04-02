package fise.feng.com.beautifulwatchlauncher.thread;

import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.socket.BluetoothSppHelper;
import fise.feng.com.beautifulwatchlauncher.util.BluetoothUtils;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

import android.os.CountDownTimer;


/**
 * @author mare
 * @Description:心跳处理方式一
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/8/9
 * @time 17:28
 */
public class HeartBeatThread {
    static final String TAG = HeartBeatThread.class.getSimpleName();

    private static final long HEART_BEAT_LINK_INTERVAL = 1 * 60 * 1000;
    private static final long HEART_BEAT_RECONNECT_INTERVAL = 10 * 1000;

    private static final int MAX_HEART_BEAT_COUNT = 5;
    private int lastReconnectCount = 0;
    private boolean isReConnectRunning = false;
    private boolean isRunning = false;


    private HeartBeatThread() {
    }



    private static class SingletonHolder {
        private static final HeartBeatThread INSTANCE = new HeartBeatThread();
    }

    public static HeartBeatThread instance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 第一次连接时候
     */
    public void onResume() {
        if (!isRunning) {
            startThreadKeepLink();
            LogUtils.d("HeartBeatThread onResume");
            isRunning = true;
        }
    }

    public void onStop() {
        if (isRunning) {
            stopThreadKeepLink();
            LogUtils.d("HeartBeatThread onStop");
            isRunning = false;
        }
    }

    public void onErrorConnected() {
        LogUtils.e("onErrorConnected isReConnectRunning = " + isReConnectRunning);
        if (!isReConnectRunning) {
            LogUtils.e("tryConnect");
            tryConnect();
            startThreadReconnect();
            isReConnectRunning = true;
        }
    }

    private void tryConnect() {
        LogUtils.e("心跳 重新连接 ");
            reConnectClient();
    }

    public void onSuccessConnected() {//运行在主线程
        LogUtils.e("onSuccessConnected");
        stopThreadReconnect();//关掉一分钟一次的心跳
        lastReconnectCount = 0;
    }

    private void reConnectClient() {
        //TODO
        if (BluetoothUtils.isBluetoothOn()) {
            //BluetoothSppHelper.instance().strat();
            LogUtils.d("reConnectClient  start listening........");
        }
    }

    private void startThreadKeepLink() {
        link.start();
    }

    private void stopThreadKeepLink() {
        LogUtils.e("stopThreadKeepLink");
        link.cancel();
        if (isReConnectRunning) {
            stopThreadReconnect();
        }
    }

    private void startThreadReconnect() {
        reConnect.start();
    }

    private void stopThreadReconnect() {
        LogUtils.e("stopThreadReconnect");
        reConnect.cancel();
        isReConnectRunning = false;
    }

    private void doCheck() {
        LogUtils.d("doCheck() ......");
        if (BluetoothUtils.isBluetoothOn() ) {
            if (StaticManager.CURRENTSTATE.equals("CONNEDTIONED")) {
                //do nothing
            }else{
                onErrorConnected();
            }
        }else{
            //onStop();//tingzhi
        }
    }

    CountDownTimer link = new CountDownTimer(HEART_BEAT_LINK_INTERVAL * MAX_HEART_BEAT_COUNT,
            HEART_BEAT_LINK_INTERVAL) {
        @Override
        public void onTick(long millisUntilFinished) {
            doCheck();
        }

        @Override
        public void onFinish() {
            doCheck();
            start();
        }
    };

    CountDownTimer reConnect = new CountDownTimer(HEART_BEAT_RECONNECT_INTERVAL * MAX_HEART_BEAT_COUNT,
            HEART_BEAT_RECONNECT_INTERVAL) {
        @Override
        public void onTick(long millisUntilFinished) {
            tryConnect();
        }

        @Override
        public void onFinish() {
            tryConnect();
        }
    };

}
