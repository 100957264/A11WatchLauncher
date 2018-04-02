package fise.feng.com.beautifulwatchlauncher.entity;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by qingfeng on 2018/2/6.
 */

public class AppEntity {
    public String appName;
    public Drawable appIcon;
    public Intent intent;
    public String pkgName;

    public AppEntity(String appName, Drawable appIcon, Intent intent, String pkgName) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.intent = intent;
        this.pkgName = pkgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    @Override
    public String toString() {
        return "AppEntity{" +
                "appName='" + appName + '\'' +
                ", appIcon=" + appIcon +
                ", intent=" + intent +
                '}';
    }
}
