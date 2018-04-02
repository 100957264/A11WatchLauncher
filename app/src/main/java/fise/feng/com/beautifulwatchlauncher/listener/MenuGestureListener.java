package fise.feng.com.beautifulwatchlauncher.listener;

import android.content.ComponentName;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.aty.SOSEditActivity;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;


/**
 * @author mare
 * @Description:TODO
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/12/21
 * @time 19:16
 */
public class MenuGestureListener implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    @Override
    public void onClick(View v) {
        LogUtils.e("onMenuClick");
        Intent intent = new Intent();
        ComponentName name = null;

        switch (v.getId()) {
            case R.id.iv_sysui_light:
                Intent displayIntent = new Intent("com.android.settings.DISPLAY_SETTINGS");
                v.getContext().startActivity(displayIntent);
                break;
            case R.id.iv_sysui_set://sos
                Intent sosIntent = new Intent(v.getContext(), SOSEditActivity.class);
                v.getContext().startActivity(sosIntent);
                break;
            case R.id.iv_sysui_voice://音量
                Intent soundIntent = new Intent();
                soundIntent.setClassName("com.android.settings","com.android.settings.Settings$SoundSettingsActivity");
                v.getContext().startActivity(soundIntent);
                break;
            case R.id.iv_sysui_clean:
                v.getContext().sendBroadcast(new Intent("fise.action.ACTION_TOGGLE_RECENTS"));
                break;
        }

    }

    @Override
    public boolean onLongClick(View v) {
        Intent intent = new Intent();
        ComponentName name = null;
        String action = null;
        boolean newTask = true;
        switch (v.getId()) {
            case R.id.iv_sysui_set:

                break;
            case R.id.iv_sysui_voice:
                name = new ComponentName("com.mediatek.factorymode",
                        "com.mediatek.factorymode.FactoryTest");
                break;
            case R.id.iv_sysui_clean:
//                name = new ComponentName(KApplication.sContext.getPackageName(),
//                        PhoneContactorActivity.class.getName());
//                newTask = false;
                break;
        }

        if (null != intent) {
            try {
                if (newTask) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (null != name) {
                    intent.setComponent(name);
                }
                if (null != action) {
                    intent.setAction(action);
                }
                v.getContext().startActivity(intent);
            } catch (Exception e) {
                LogUtils.e(v.getClass().getSimpleName() + "skip act " + e);
            }
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;//下拉了就强制消费事件
    }
}
