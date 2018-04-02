package fise.feng.com.beautifulwatchlauncher;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import fise.feng.com.beautifulwatchlauncher.clock.util.ClockSkinUtil;
import fise.feng.com.beautifulwatchlauncher.util.Utils;

/**
 * Created by qingfeng on 2017/12/20.
 */

public class KApplicationContext extends Application{
    public static Context sContext;
    public static Typeface lanTingBoldBlackTypeface = null;
    public static Typeface cardFragmentTypeface = null;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Utils.init(this);
        appInit();
        lanTingBoldBlackTypeface = Typeface.createFromAsset(getAssets(),"fonts/LanTingBoldBlack.TTF");
        cardFragmentTypeface = Typeface.createFromAsset(getAssets(),"fonts/DINCond-Medium.otf");
    }

    private void appInit() {
        ClockSkinUtil.initAllClockIndex();
    }

}
