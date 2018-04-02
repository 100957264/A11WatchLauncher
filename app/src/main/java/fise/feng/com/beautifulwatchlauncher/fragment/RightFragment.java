package fise.feng.com.beautifulwatchlauncher.fragment;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.adapter.AppListAdapter;
import fise.feng.com.beautifulwatchlauncher.aty.AppsListActivity;
import fise.feng.com.beautifulwatchlauncher.aty.BluetoothActivity;
import fise.feng.com.beautifulwatchlauncher.aty.CompassActivity;
import fise.feng.com.beautifulwatchlauncher.aty.PressureActivity;
import fise.feng.com.beautifulwatchlauncher.entity.AppEntity;
import fise.feng.com.beautifulwatchlauncher.view.ViewManager;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qingfeng on 2017/12/14.
 */

public class RightFragment extends Fragment implements AdapterView.OnItemClickListener {

    GridView gridView;
    String[] from = {"image", "text"};
    int[] to = {R.id.image, R.id.text};
    SimpleAdapter simpleAdapter;
    AppListAdapter appListAdapter;

    public static RightFragment newInstance() {
        Bundle args = new Bundle();
        RightFragment fragment = new RightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_right_page, container, false);
        gridView = view.findViewById(R.id.right_grid_view);
        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.items, from, to);
        // appListAdapter = new AppListAdapter(getActivity(),getAppNameIcon());
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(this);
        return view;
    }

    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        ;
        for (int i = 0; i < ViewManager.icons.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", ViewManager.icons[i]);
            map.put("text", getActivity().getResources().getString(ViewManager.iconName[i]));

            dataList.add(map);
        }

        return dataList;
    }

    private List<AppEntity> getAppNameIcon() {
        List<AppEntity> mListAppInfo = new ArrayList<AppEntity>();
        PackageManager pm = getActivity().getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo resolveInfo : resolveInfos) {
            String activityName = resolveInfo.activityInfo.name;
            String pkgName = resolveInfo.activityInfo.packageName;
            String appName = (String) resolveInfo.loadLabel(pm);
            Drawable appIcon = resolveInfo.loadIcon(pm);
            if (appName == null || appIcon == null || pkgName == null || activityName == null) {
                continue;
            }
            Intent intent = new Intent();
            intent.setClassName(pkgName, activityName);
            AppEntity appInfo = new AppEntity(appName, appIcon, intent, pkgName);
            mListAppInfo.add(appInfo);
        }
        return mListAppInfo;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startTargetApp(position);
    }

    private void startTargetApp(int position) {
        switch (position) {
            case 0://dial
                Intent dialintent = new Intent("android.intent.action.DIAL");
                dialintent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(dialintent);
                break;
            case 1://mms
                Intent mmsIntent = new Intent(Intent.ACTION_MAIN);
                mmsIntent.addCategory("android.intent.category.APP_MESSAGING");
                startActivity(mmsIntent);
                break;
            case 2://camera
                try {
                    Intent cameraIntent = new Intent();
                    cameraIntent.setClassName("com.mediatek.camera", "com.android.camera.CameraLauncher");
                    startActivity(cameraIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3://gallery
                Intent galleryIntent = new Intent(Intent.ACTION_MAIN);
                galleryIntent.addCategory("android.intent.category.APP_GALLERY");
                startActivity(galleryIntent);

                break;
            case 4://Settings
                try {
                    Intent settingsIntent = new Intent("android.settings.SETTINGS");
                    startActivity(settingsIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 5://music
                Intent filemanagerIntent = new Intent("com.mediatek.hotknot.action.FILEMANAGER_FILE_RECEIVED");
                startActivity(filemanagerIntent);
                break;
            case 6://bluetooth
                try {
                    Intent bluetoothIntent = new Intent();
                    bluetoothIntent.setClassName("com.android.BluetoothSocketMsg","com.android.BluetoothSocketMsg.BluetoothSettingsActivity");
                    startActivity(bluetoothIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 7://stepcounter
                try {
                    Intent stepcounterIntent = new Intent();
                    stepcounterIntent.setClassName("cn.com.fise.fisestepcounter", "cn.com.fise.fisestepcounter.view.MainActivity");
                    startActivity(stepcounterIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                try {
                    Intent heartIntent = new Intent();
                    heartIntent.setClassName("com.fise.heartrate", "com.fise.heartrate.MainActivity");
                    startActivity(heartIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 9:
                try {
                    Intent musicIntent = new Intent(Intent.ACTION_MAIN);
                    musicIntent.addCategory("android.intent.category.APP_MUSIC");
                    startActivity(musicIntent);
//                    Intent musicIntent = new Intent();
//                    musicIntent.setClassName("com.android.music", "com.android.music.TrackBrowserActivity");
//                    startActivity(musicIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 10:
                try {
                    Intent chromeIntent = new Intent(Intent.ACTION_MAIN);
                    chromeIntent.addCategory("android.intent.category.APP_BROWSER");
                    startActivity(chromeIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 11:
                try {
                    Intent playIntent = new Intent();
                    playIntent.setClassName("com.android.vending", "com.android.vending.AssetBrowserActivity");
                    startActivity(playIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 12:
                try {
                    Intent playIntent = new Intent();
                    playIntent.setClassName("com.whatsapp", "com.whatsapp.Main");
                    startActivity(playIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 13:
                try {
                    Intent browserIntent = new Intent();
                    browserIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
//            case 14:
//                try {
//                    Intent weatherIntent = new Intent(getActivity(), CompassActivity.class);
//                    startActivity(weatherIntent);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                break;
//            case 15://气压计
//                try {
//                    //com.szjx.thomas.watchweather/.PressureActivity
//                    Intent pressureIntent = new Intent(getActivity(), PressureActivity.class);
//                    startActivity(pressureIntent);
//
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                break;
            case 14:
                try {
                    Intent downloadIntent = new Intent();
                    downloadIntent.setAction("android.intent.action.VIEW_DOWNLOADS");
                    startActivity(downloadIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 15://录音
                try {
                    Intent soundRecordIntent = new Intent("com.android.soundrecorder.SoundRecorder");
                    startActivity(soundRecordIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 16://日历
                try {
                    Intent calendarIntent = new Intent();
                    calendarIntent.setClassName("com.android.calendar", "com.android.calendar.AllInOneActivity");
                    startActivity(calendarIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 17://时钟
                try {
                    Intent alarmClockIntent = new Intent();
                    alarmClockIntent.setClassName("com.android.deskclock", "com.android.deskclock.DeskClock");
                    startActivity(alarmClockIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 18://语音搜索
                try {
                    Intent alarmClockIntent = new Intent();
                    alarmClockIntent.setClassName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity");
                    startActivity(alarmClockIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Application is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 19:
                Intent appIntent = new Intent(getActivity(), AppsListActivity.class);
                startActivity(appIntent);
                break;
        }
    }
}
