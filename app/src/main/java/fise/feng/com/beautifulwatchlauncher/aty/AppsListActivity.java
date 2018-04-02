package fise.feng.com.beautifulwatchlauncher.aty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.constant.FragmentConstant;
import fise.feng.com.beautifulwatchlauncher.fragment.ApplistFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.BluetoothFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.DownloadQRCodeFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.QRCodeFragment;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

public class AppsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_app_list);
        startFragment(ApplistFragment.newInstance(),R.id.app_list);
    }

    private void startFragment(Fragment fragment, int fragmentId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentId, fragment);
        fragmentTransaction.commit();
    }
}
