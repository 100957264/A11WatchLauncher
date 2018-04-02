package fise.feng.com.beautifulwatchlauncher.view;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.fragment.ClockPageFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.LeftFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.MainFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.RightFragment;
import fise.feng.com.beautifulwatchlauncher.holder.PageHolder;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingfeng on 2017/12/14.
 */

public class ViewManager {
    private static class SingletonHolder {
        private static final ViewManager INSTANCE = new ViewManager();
    }

    public static ViewManager instance() {
        return SingletonHolder.INSTANCE;
    }
    public static int[] icons = {R.drawable.icon_dial,R.drawable.icon_mms,
            R.drawable.icon_camera2,R.drawable.icon_gallery,R.drawable.icon_settings,R.drawable.icon_file_manager,
            R.drawable.icon_blue,R.drawable.icon_step,R.drawable.icon_heartrate,R.drawable.icon_volume,
            R.drawable.icon_google,R.drawable.icon_playstore,R.drawable.icon_whatsapp,R.drawable.icon_browser,
           /* R.drawable.icon_weather,R.drawable.icon_pressure,*/R.drawable.icon_download,R.drawable.icon_record,
            R.drawable.icon_calendar,R.drawable.icon_clock,R.drawable.icon_voice_search,R.drawable.icon_apps
            };

    public static int[] iconName = {R.string.app_dial,R.string.app_mms,R.string.app_camera,R.string.app_gallery,
            R.string.app_setting,R.string.app_file_manager,R.string.app_blue,R.string.app_step,R.string.app_heart,R.string.app_volume,R.string.app_chrome,
            R.string.app_playstore,R.string.app_whatsapp,R.string.app_browser,
            /*R.string.app_weather,R.string.app_pressure,*/R.string.app_download,R.string.app_record,
            R.string.app_calendar,R.string.app_clock,R.string.app_voice_search,R.string.app_list
            };

    public static int[] clocks = {R.drawable.page1,R.drawable.page2,R.drawable.page3,R.drawable.page4,
            R.drawable.page5,R.drawable.page6,R.drawable.page7,R.drawable.page8,R.drawable.page9,R.drawable.page10,
            R.drawable.page11,R.drawable.page12,R.drawable.page13,R.drawable.page14,R.drawable.page15,R.drawable.page16,
            R.drawable.page17,R.drawable.page18,R.drawable.page19,R.drawable.page20,R.drawable.page21,R.drawable.page22,
            R.drawable.page23,R.drawable.page24,R.drawable.page25,R.drawable.page26,R.drawable.page27,R.drawable.page28,
            R.drawable.page29,R.drawable.page30,R.drawable.page31,R.drawable.page32,R.drawable.page33,R.drawable.page34,
            R.drawable.page35
    };
    public static int currentClock = 0;

    private List<Fragment> allFragments = new ArrayList<>();
    public List<Fragment> initAllPages(){
        allFragments.clear();
//        allFragments.add(LeftFragment.newInstance());
        allFragments.add(MainFragment.newInstance());
        allFragments.add(RightFragment.newInstance());
        return allFragments;
    }
    private List<Fragment> clockFragments = new ArrayList<>();
    PageHolder holder;
    public List<Fragment> initClockPages(){
        clockFragments.clear();
        for (int i =0 ;i< clocks.length -1;i ++){
            holder = new PageHolder(i) ;
            clockFragments.add(ClockPageFragment.newInstance(holder));
        }
        return clockFragments;
    }
}
