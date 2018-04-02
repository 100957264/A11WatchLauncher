package fise.feng.com.beautifulwatchlauncher.holder;

import java.io.Serializable;

/**
 * Created by mare on 2017/8/27 0027.
 */

public class PageHolder implements Serializable {
    private int iconId;

    public PageHolder(int iconId) {
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    @Override
    public String toString() {
        return "PageHolder{" +
                "iconId=" + iconId +
                '}';
    }
}
