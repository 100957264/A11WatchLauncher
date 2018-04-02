package fise.feng.com.beautifulwatchlauncher.aty;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.adapter.BluetoothSettingsAdapter;
import fise.feng.com.beautifulwatchlauncher.entity.SettingsEntity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class BluetoothActivity extends BaseActivity {
    BluetoothSettingsAdapter bluetoothSettingsAdapter;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.bluetooth_activity);
        initView();
    }
    private void initView(){
         List<SettingsEntity> mSettingsList = new ArrayList<>();
         String[] TITLE = {getResources().getString(R.string.bluetooth_item)
                ,getResources().getString(R.string.bind_qr_code)
                };
        int[] ICONID = {R.drawable.icon_bluetooth_setting,R.drawable.qr_icon};
            mSettingsList.clear();
            for (int i = 0; i< ICONID.length; i++){
                SettingsEntity settingsEntity = new SettingsEntity();
                settingsEntity.setDrawableId(ICONID[i]);
                settingsEntity.setTitle(TITLE[i]);
                mSettingsList.add(settingsEntity);
            }

        mRecyclerView = findViewById(R.id.bluetooth_settings);
        bluetoothSettingsAdapter = new BluetoothSettingsAdapter(this, mSettingsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(bluetoothSettingsAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
