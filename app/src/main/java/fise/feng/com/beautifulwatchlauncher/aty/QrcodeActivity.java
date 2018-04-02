package fise.feng.com.beautifulwatchlauncher.aty;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.fragment.QRCodeFragment;
import fise.feng.com.beautifulwatchlauncher.socket.BluetoothSppHelper;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

public class QrcodeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.qrcode_activity);
        startFragment(QRCodeFragment.newInstance(),R.id.bluetooth_fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BluetoothSppHelper.instance().strat();
    }


    public void startFragment(Fragment fragment, int fragmentId){
        LogUtils.d("fengqing startFragment ...");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentId,fragment,null);
        fragmentTransaction.commit();
    }

}
