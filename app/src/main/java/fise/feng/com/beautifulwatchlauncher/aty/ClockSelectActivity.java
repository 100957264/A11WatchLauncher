package fise.feng.com.beautifulwatchlauncher.aty;

import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.adapter.CommonFragmentPagerAdapter;
import fise.feng.com.beautifulwatchlauncher.constant.ActivityConstant;
import fise.feng.com.beautifulwatchlauncher.manager.PreferControler;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.view.ViewManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class ClockSelectActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    private CommonFragmentPagerAdapter mAllPageAdapter;
    List<Fragment> pages = new ArrayList<>();
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.clock_select);
        pages = ViewManager.instance().initClockPages();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setCurrentItem(PreferControler.instance().getClockIndex());//default set 0
    }

    private void initView(){
        viewPager =(ViewPager) findViewById(R.id.select_view_page);
        viewPager.setOffscreenPageLimit(pages.size());
        LogUtils.d("fengqing initView pages.size()= " + pages.size());
        mAllPageAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(),pages);
        viewPager.setAdapter(mAllPageAdapter);
        viewPager.setCurrentItem(PreferControler.instance().getClockIndex());//default set 0
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LogUtils.d("fengqing onPageScrolled position=" + position);
    }

    @Override
    public void onPageSelected(int position) {
        LogUtils.d("fengqing onPageSelected position=" + position);
        Intent intent = new Intent();
        intent.putExtra(ActivityConstant.RESULT_STRING,position);
        setResult(ActivityConstant.RESULT_CODE,intent);
        PreferControler.instance().setClockIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        LogUtils.d("fengqing onPageScrollStateChanged state=" + state);
    }

}
