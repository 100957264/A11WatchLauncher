package fise.feng.com.beautifulwatchlauncher.fragment;

import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.event.BatteryStatus;
import fise.feng.com.beautifulwatchlauncher.event.DataActivityUpdateEvent;
import fise.feng.com.beautifulwatchlauncher.event.NetworkTypeEvent;
import fise.feng.com.beautifulwatchlauncher.event.SignalStrengthChangedEvent;
import fise.feng.com.beautifulwatchlauncher.event.VolteStatusEvent;
import fise.feng.com.beautifulwatchlauncher.event.WifiStateChangedEvent;
import fise.feng.com.beautifulwatchlauncher.manager.NetManager;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.manager.StatusBarManager;
import fise.feng.com.beautifulwatchlauncher.util.DataActivityUtils;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qingfeng on 2017/12/14.
 */

public class TopFragment extends Fragment implements View.OnClickListener,View.OnLongClickListener{
    ImageView settingsIcon;
    ImageView lightIcon;
    ImageView disturbIcon;
    ImageView volumeIcon;
    StatusBarManager statusBarManager;

    public static TopFragment newInstance() {
        Bundle args = new Bundle();
        TopFragment fragment = new TopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        statusBarManager.update();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment,container,false);
        ButterKnife.bind(this,view);
        initView(view);
        statusBarManager = new StatusBarManager(this);
        statusBarManager.start(view);
        return view;
    }
    private void initView(View view){
        settingsIcon = view.findViewById(R.id.iv_sysui_set);
        settingsIcon.setOnClickListener(this);
        settingsIcon.setOnLongClickListener(this);
        lightIcon = view.findViewById(R.id.iv_sysui_light);
        lightIcon.setOnClickListener(this);
        disturbIcon = view.findViewById(R.id.iv_sysui_disturb);
        disturbIcon.setOnClickListener(this);
        volumeIcon = view.findViewById(R.id.iv_sysui_voice);
        volumeIcon.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sysui_set:
//                Intent settingIntent = new Intent();
//                settingIntent.setClassName("com.android.settings","com.android.settings.FiseSettingActivity");
//                startActivity(settingIntent);
                Intent settingsIntent = new Intent("android.settings.SETTINGS");
                startActivity(settingsIntent);
                break;
            case R.id.iv_sysui_light:
                Intent lightIntent = new Intent();
                lightIntent.setClassName("com.android.settings","com.android.settings.BrightnessActivity");
                startActivity(lightIntent);
                break;
            case R.id.iv_sysui_voice:
                Intent voiceIntent = new Intent();
                voiceIntent.setClassName("com.android.settings","com.android.settings.VolumeActivity");
                startActivity(voiceIntent);
                break;
            case R.id.iv_sysui_disturb:
                Intent cleanIntent = new Intent("com.android.systemui.recents.TOGGLE_RECENTS");
                startActivity(cleanIntent);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(v.getId() == R.id.iv_sysui_set){
            Intent factoryIntent = new Intent("com.freeme.intent.action.HARDWARE_TEST");
            startActivity(factoryIntent);
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (statusBarManager != null)
        statusBarManager.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
