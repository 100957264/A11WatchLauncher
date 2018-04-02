package fise.feng.com.beautifulwatchlauncher.entity;

/**
 * Created by qingfeng on 2018/1/22.
 */

public class SettingsEntity {
    public String title;
    public int drawableId;

    public SettingsEntity(String title, int drawableId) {
        this.title = title;
        this.drawableId = drawableId;
    }
    public SettingsEntity(){}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    @Override
    public String toString() {
        return "SettingsEntity{" +
                "title='" + title + '\'' +
                ", drawableId=" + drawableId +
                '}';
    }
}
