package fise.feng.com.beautifulwatchlauncher.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
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

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.adapter.AppListAdapter;
import fise.feng.com.beautifulwatchlauncher.aty.BluetoothActivity;
import fise.feng.com.beautifulwatchlauncher.entity.AppEntity;
import fise.feng.com.beautifulwatchlauncher.util.ListUtils;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.view.ViewManager;

/**
 * Created by qingfeng on 2017/12/14.
 */

public class ApplistFragment extends Fragment {

    GridView gridView;
    AppListAdapter appListAdapter;

    public static ApplistFragment newInstance() {
        Bundle args = new Bundle();
        ApplistFragment fragment = new ApplistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_app_list_page, container, false);
        gridView = view.findViewById(R.id.applist_grid_view);
        View  emptyView = view.findViewById(R.id.app_list_none);
        List<AppEntity> apps = scanLocalInstallAppList();
        appListAdapter = new AppListAdapter(getActivity(),apps );
        if (ListUtils.isEmpty(apps)) {
            emptyView.setVisibility(View.VISIBLE);
        }
        gridView.setAdapter(appListAdapter);
        return view;
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
            AppEntity appInfo = new AppEntity(appName, appIcon, intent,pkgName);
            mListAppInfo.add(appInfo);
            LogUtils.e("pkgName ==" + pkgName);
        }
        return mListAppInfo;
    }

    public List<AppEntity> scanLocalInstallAppList() {
        List<AppEntity> installAppInfos = new ArrayList<AppEntity>();
        try {
            PackageManager pm = getActivity().getPackageManager();
            List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                String pkgName = packageInfo.packageName;
                if (pkgName.contains("com.baidu.map.location")){
                    continue;
                }
                Drawable appIcon = pm.getApplicationIcon(pkgName);
                Intent intent = pm.getLaunchIntentForPackage(pkgName);
                String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
                AppEntity installAppEntity = new AppEntity(appName, appIcon, intent,pkgName);
                installAppInfos.add(installAppEntity);
            }
        } catch (Exception e) {
        }
        return installAppInfos;
    }
}
