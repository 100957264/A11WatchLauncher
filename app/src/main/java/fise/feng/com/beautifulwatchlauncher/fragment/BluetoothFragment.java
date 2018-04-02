package fise.feng.com.beautifulwatchlauncher.fragment;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.event.BlueSocketStatusEvent;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.util.BluetoothUtils;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by qingfeng on 2018/1/3.
 */

public class BluetoothFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    Switch btSwitch;
    TextView btState;

    public static BluetoothFragment newInstance() {
        Bundle args = new Bundle();
        BluetoothFragment fragment = new BluetoothFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bluetooth_settings, container, false);
        btSwitch = view.findViewById(R.id.bt_switch);
        btSwitch.setOnCheckedChangeListener(this);
        btSwitch.setChecked(BluetoothUtils.isBluetoothOn());
        btState = view.findViewById(R.id.bt_state_value);
        if(!TextUtils.isEmpty(StaticManager.CURRENTSTATE)){
            btState.setText(StaticManager.CURRENTSTATE);
        }
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       boolean isOn = buttonView.isChecked() ? true : false;
       if(isOn){
           BluetoothUtils.turnOnBluetooth();
       }else {
           BluetoothUtils.turnOffBluetooth();
       }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BluetoothConnectState(BlueSocketStatusEvent event) {
        btState.setText(event.status);
        LogUtils.d("BluetoothConnectState ==" + event.status);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
