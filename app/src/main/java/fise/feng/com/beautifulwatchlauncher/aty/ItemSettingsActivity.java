package fise.feng.com.beautifulwatchlauncher.aty;

import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.constant.FragmentConstant;
import fise.feng.com.beautifulwatchlauncher.fragment.BluetoothFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.DownloadQRCodeFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.QRCodeFragment;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class ItemSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_item);
        initFragmentId();
    }
    private void initFragmentId(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int fragmentid = bundle.getInt(FragmentConstant.FRAGMENT_ID);
        LogUtils.d("fragmentid =" + fragmentid);
        switch (fragmentid){
            case FragmentConstant.FM_BT:
                startFragment(BluetoothFragment.newInstance(),R.id.fragment_setting);
                break;
            case FragmentConstant.FM_QR:
                startFragment(QRCodeFragment.newInstance(),R.id.fragment_setting);
                break;
//            case FragmentConstant.FM_MMS:
//                startFragment(MmsFragment.newInstance(),R.id.fragment_setting);
//                break;
//            case FragmentConstant.FM_NOTI:
//                startFragment(NotificationFragment.newInstance(),R.id.fragment_setting);
//                break;
//            case FragmentConstant.FM_CALL:
//                startFragment(PhoneFragment.newInstance(),R.id.fragment_setting);
//                break;
//            case FragmentConstant.DOWNLOAD:
//                startFragment(DownloadQRCodeFragment.newInstance(),R.id.fragment_setting);
//                break;
        }
    }
    private void startFragment(Fragment fragment, int fragmentId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentId, fragment);
        fragmentTransaction.commit();
    }
}
