package fise.feng.com.beautifulwatchlauncher.aty;

import android.Manifest;
import android.content.Intent;

import fise.feng.com.beautifulwatchlauncher.KApplicationContext;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.adapter.CommonFragmentPagerAdapter;
import fise.feng.com.beautifulwatchlauncher.db.DBManager;
import fise.feng.com.beautifulwatchlauncher.fragment.TopFragment;
import fise.feng.com.beautifulwatchlauncher.listener.MenuGestureListener;
import fise.feng.com.beautifulwatchlauncher.manager.NetManager;
import fise.feng.com.beautifulwatchlauncher.manager.PreferControler;
import fise.feng.com.beautifulwatchlauncher.manager.StatusBarManager;
import fise.feng.com.beautifulwatchlauncher.receiver.BatteryStatusReceiver;
import fise.feng.com.beautifulwatchlauncher.service.BluetoothManagerService;
import fise.feng.com.beautifulwatchlauncher.thread.HeartBeatThread;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.view.MutilDrawView;
import fise.feng.com.beautifulwatchlauncher.view.ViewManager;

import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class BeautifulWatchLauncherActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    ViewPager viewPager;
    private CommonFragmentPagerAdapter mAllPageAdapter;
    private int currentPage = 0;
    List<Fragment> pages = new ArrayList<Fragment>();
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_PRIVILEGED,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE
    };

    private MutilDrawView mPullDown;
    private RelativeLayout mMainContent;
    private MenuGestureListener menuGestureListener;
    private RelativeLayout mTopMenuContainer;
    private StatusBarManager mStatusBarManager;
    BatteryStatusReceiver batteryStatusReceiver;

    private void getPermission() {
        RxPermissions.getInstance(this)
                .request(PERMISSIONS).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) {// 已经获取权限
                    DBManager.instance().initDao();
                    initView();
                    initStatusBarView();
                    NetManager.instance().registerTm();//注册电话状态监听
//                    StaticManager.instance().initIMEI();
                } else {
                    // 未获取权限
                    Toast.makeText(KApplicationContext.sContext, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.main);
        pages = ViewManager.instance().initAllPages();
//        getPermission();
        //=============
        DBManager.instance().initDao();
        initView();
        initStatusBarView();
        NetManager.instance().registerTm();//注册电话状态监听
        //===========
//        HeartBeatThread.instance();
//        Intent serviceIntent = new Intent(this, BluetoothManagerService.class);
//        startService(serviceIntent);
        PreferControler.instance();
        LogUtils.d("onCreate:  currentPage =" + currentPage);
    }

    private void initStatusBarView() {
        mPullDown = (MutilDrawView) findViewById(R.id.drag_view_group);
        mMainContent = (RelativeLayout) findViewById(R.id.fise_launcher_main_content);
        menuGestureListener = new MenuGestureListener();

        mTopMenuContainer = (RelativeLayout) findViewById(R.id.rl_sysui_gride_container);
        ImageView light = (ImageView) findViewById(R.id.iv_sysui_light);
        ImageView setting = (ImageView) findViewById(R.id.iv_sysui_set);
        ImageView volume = (ImageView) findViewById(R.id.iv_sysui_voice);
        ImageView cleaner = (ImageView) findViewById(R.id.iv_sysui_clean);
        light.setOnClickListener(menuGestureListener);
        setting.setOnClickListener(menuGestureListener);
        setting.setOnLongClickListener(menuGestureListener);
        volume.setOnClickListener(menuGestureListener);
        volume.setOnLongClickListener(menuGestureListener);
        cleaner.setOnClickListener(menuGestureListener);
        cleaner.setOnLongClickListener(menuGestureListener);
        mTopMenuContainer.setOnTouchListener(menuGestureListener);
        mStatusBarManager = new StatusBarManager(this);
        mStatusBarManager.start(mPullDown);
        batteryStatusReceiver = new BatteryStatusReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryStatusReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewPager != null) {
            viewPager.setCurrentItem(currentPage);
        }
        LogUtils.d("onResume:  currentPage =" + currentPage);
    }

    @Override
    public void onBackPressed() {
        LogUtils.d("currentPage =" + currentPage);
        if (currentPage == 1) {
            currentPage = 0;
        }
        viewPager.setCurrentItem(currentPage);
        LogUtils.d("currentPage111 =" + currentPage);
        return;
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_page);
        viewPager.setOffscreenPageLimit(pages.size() - 1);
        mAllPageAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(mAllPageAdapter);
        viewPager.setCurrentItem(currentPage);//default set 1
        viewPager.setOnPageChangeListener(this);
    }

    public void startFragment(Fragment fragment, int fragmentId) {
        LogUtils.d("fengqing startFragment ...");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentId, fragment, null);
        fragmentTransaction.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy =");
        if(batteryStatusReceiver != null) {
            unregisterReceiver(batteryStatusReceiver);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d("onNewIntent ===" + intent);
        if (intent != null) {
            LogUtils.d("onNewIntent ===" + intent.getClass());
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        LogUtils.d("onPageSelected position=" + position);
        currentPage = position;
        // PreferControler.instance().setViewPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
