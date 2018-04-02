package fise.feng.com.beautifulwatchlauncher.manager;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.BatteryManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import fise.feng.com.beautifulwatchlauncher.KApplicationContext;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.event.BatteryStatus;
import fise.feng.com.beautifulwatchlauncher.event.DataActivityUpdateEvent;
import fise.feng.com.beautifulwatchlauncher.event.NetworkTypeEvent;
import fise.feng.com.beautifulwatchlauncher.event.SignalStrengthChangedEvent;
import fise.feng.com.beautifulwatchlauncher.event.VolteStatusEvent;
import fise.feng.com.beautifulwatchlauncher.event.WifiStateChangedEvent;
import fise.feng.com.beautifulwatchlauncher.fragment.TopFragment;
import fise.feng.com.beautifulwatchlauncher.util.DataActivityUtils;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

/**
 * @author mare
 * @Description:TODO Delgate of StatusBar Widget
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2018/1/16
 * @time 20:58
 */
public class StatusBarManager {
    Context mContext;

    public StatusBarManager(Context ctx) {
        this.mContext = ctx;
    }

    public StatusBarManager(TopFragment standByFragment) {
        this(standByFragment.getContext());
    }

    @Bind(R.id.iv_sysiui_bluetooth)
    ImageView mBluetoothState;
    @Bind(R.id.iv_sysiui_battery)
    ImageView mBatteryView;
    @Bind(R.id.tv_battery)
    TextView mBatteryLevel;

    ClipDrawable mClipDrawable;
    LayerDrawable mLayerDrawable;

    @Bind(R.id.iv_sysiui_battery_charging)
    ImageView mBatteryCharging;
    @Bind(R.id.iv_sysiui_signal)
    ImageView mSignalView;
    @Bind(R.id.tv_sysiui_signal)
    TextView mSignalText;
    @Bind(R.id.iv_sysiui_wifi)
    ImageView mWifiView;
    @Bind(R.id.iv_mobile_traffic_down)
    ImageView mMobileDownView;
    @Bind(R.id.iv_mobile_traffic_up)
    ImageView mMobileUpView;
    @Bind(R.id.iv_wifi_traffic_up)
    ImageView mWifiUpView;
    @Bind(R.id.iv_wifi_traffic_down)
    ImageView mWifiDownView;
    @Bind(R.id.iv_sysiui_volte_network_hd)
    ImageView mVolteNetwork;
    @Bind(R.id.sysiui_signal_container)
    RelativeLayout mSysiuiSignalContainer;
    public BroadcastReceiver mReceiver;

    public void start(View container) {
        ButterKnife.bind(this, container);
        findId();
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mLayerDrawable = (LayerDrawable) mBatteryView.getDrawable();
        mClipDrawable = (ClipDrawable) mLayerDrawable.findDrawableByLayerId(R.id.clip_drawable);
//        initBluetoothIcon();
    }

    private void findId() {

    }

    public void stop() {
        if (null != mReceiver && null != mContext) {
            mContext.unregisterReceiver(mReceiver);
        }
        EventBus.getDefault().unregister(this);
    }

    public void updateBattery(BatteryStatus event) {
        if (null == event) {
            return;
        }
        if (event.level <= 15) {
            mBatteryView.setImageResource(R.drawable.battery_low_15);
        } else if (event.level > 15 && event.level <= 40) {
            mBatteryView.setImageResource(R.drawable.battery_low_40);
        } else {
            mBatteryView.setImageResource(R.drawable.battery);
        }
        LogUtils.v("updateBattery " + event.level);
        mBatteryLevel.setText(event.level + "");
        mLayerDrawable = (LayerDrawable) mBatteryView.getDrawable();
        mClipDrawable = (ClipDrawable) mLayerDrawable.findDrawableByLayerId(R.id.clip_drawable);
        mClipDrawable.setLevel((event.level * 100 / event.scale) * 100);
        if (event.status == BatteryManager.BATTERY_STATUS_CHARGING) {
            mBatteryCharging.setVisibility(View.VISIBLE);
        } else {
            mBatteryCharging.setVisibility(View.INVISIBLE);
        }
        LogUtils.i("BatteryStatus " + event.toString());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVolteStateChanged(VolteStatusEvent event) {
        mVolteNetwork.setVisibility(event.volteStatus == 0 ? View.VISIBLE : View.GONE);
        LogUtils.d("onVolteStateChanged event.volteStatus =" + event.volteStatus);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMobileDataActivityChanged(DataActivityUpdateEvent event) {
        LogUtils.d(event.toString());
        updateMobileDataActivity(event);
    }

    public void updateMobileDataActivity(DataActivityUpdateEvent event) {
        boolean[] viss = new boolean[2];
        if (event != null) {
            int direction = event.direction;
            viss = DataActivityUtils.getMobileDataVis(direction);
        }
        LogUtils.d("viss[0]" + viss[0] + ",viss[1]" + viss[1]);
        mMobileUpView.setVisibility(viss[0] && NetManager.instance().hasInsertSIM()? View.VISIBLE : View.GONE);
        mMobileDownView.setVisibility(viss[1]&& NetManager.instance().hasInsertSIM() ? View.VISIBLE : View.GONE);
    }

    private void initBluetoothIcon() {
        final BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();

        mBluetoothState.post(new Runnable() {
            @Override
            public void run() {
                mBluetoothState.setVisibility(blueadapter.isEnabled() ? View.VISIBLE : View.GONE);
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case BluetoothAdapter.ACTION_STATE_CHANGED:
                        int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                        switch (blueState) {
                            case BluetoothAdapter.STATE_TURNING_ON:
                            case BluetoothAdapter.STATE_ON:
                                mBluetoothState.setVisibility(View.VISIBLE);
                                break;
                            case BluetoothAdapter.STATE_TURNING_OFF:
                            case BluetoothAdapter.STATE_OFF:
                                mBluetoothState.setVisibility(View.GONE);
                                break;
                        }
                        break;
                }
            }
        };
        mContext.registerReceiver(mReceiver, filter);
    }

    public void setWifiIcon(WifiStateChangedEvent event) {
        if (event.state == 1) {
            mWifiView.setVisibility(View.INVISIBLE);
        } else if (event.connected) {
            mWifiView.setVisibility(View.VISIBLE);
            mWifiView.setImageResource(NetManager.WIFI_ICON[event.wifiStrength]);
        } else {
            mWifiView.setVisibility(View.VISIBLE);
            mWifiView.setImageResource(NetManager.WIFI_ICON[event.wifiStrength]);
        }
    }

    public void setNetworkType(NetworkTypeEvent event) {
        LogUtils.i("fengqing: setNetworkType : type =" + event.networkType);
        boolean isSimAbsent = event.isSimAbsent;
        if (!isSimAbsent || NetManager.instance().hasInsertSIM()) {
            String networkType = NetManager.instance().getNetworkType(event.networkType);
            if (!networkType.isEmpty()) {
                mSignalText.setText(networkType);
            } else {
//                mSignalText.setText(KApplication.sContext.getResources().getString(R.string.unknow_sim));
                mSignalText.setText("");
            }
        } else {
            mSignalText.setText(KApplicationContext.sContext.getResources().getString(R.string.no_sim));
            mSignalView.setImageResource(NetManager.SIGNAL_ICON[0]);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkTypeUpdate(NetworkTypeEvent event) {
        setNetworkType(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWifiStateChanged(WifiStateChangedEvent event) {
        setWifiIcon(event);
        LogUtils.i("WifiDebug: state= " + event.state + ",connected=" + event.connected + ",wifiStrength =" + event.wifiStrength);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignalUpdate(SignalStrengthChangedEvent event) {
        if (NetManager.instance().hasInsertSIM()) {
            mSignalView.setImageResource(NetManager.SIGNAL_ICON[event.mSignalStrength]);
            LogUtils.i("SignalDebug: mSignalStrength =" + event.mSignalStrength);
        }
    }

    @Subscribe
    public void onEvent(BatteryStatus event) {
        updateBattery(event);
    }

    /**
     * 更新状态栏所有状态
     */
    public void update() {
        WifiStateChangedEvent wifiStateChangedEvent = StaticManager.instance().wifiStateChangedEvent;
        if (null != wifiStateChangedEvent) {
            setWifiIcon(wifiStateChangedEvent);
        }
        NetworkTypeEvent networkTypeEvent = StaticManager.instance().networkTypeEvent;
        if (networkTypeEvent != null) {
            setNetworkType(networkTypeEvent);
        }
        updateBattery(StaticManager.instance().getBatteryStatus());
        updateMobileDataActivity(StaticManager.instance().dataActivityUpdateEvent);
    }
}
