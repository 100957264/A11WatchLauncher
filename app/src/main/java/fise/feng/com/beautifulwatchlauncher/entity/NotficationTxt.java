package fise.feng.com.beautifulwatchlauncher.entity;

import fise.feng.com.beautifulwatchlauncher.KApplicationContext;
import fise.feng.com.beautifulwatchlauncher.util.Base64Utils;

/**
 * Created by qingfeng on 2018/1/18.
 */

public class NotficationTxt {
    String title;
    String content;
    long time;
    String appName;
    String appIcon;

    public NotficationTxt(String title, String content, long time, String appName,int iconId) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.appName = appName;
        appIcon = Base64Utils.drawableToByte(KApplicationContext.sContext.getDrawable(iconId));
    }

    public NotficationTxt(String title, String content, long time, String appName, String appIcon) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.appName = appName;
        this.appIcon = appIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", appName='" + appName + '\'' +
                ", appIcon='" + appIcon + '\'' +
                '}';
    }
}
