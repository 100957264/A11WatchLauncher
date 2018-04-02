package fise.feng.com.beautifulwatchlauncher.info;

import android.graphics.drawable.Drawable;

/**
 * 作者：Rance on 2016/12/21 16:22
 * 邮箱：rance935@163.com
 */
public class ImgPreviewInfo {
    private int locationX;
    private int locationY;
    private int width;
    private int height;
    private int resourceId;
    private Drawable imageBg;

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Drawable getImageBg() {
        return imageBg;
    }

    public void setImageBg(Drawable imageBg) {
        this.imageBg = imageBg;
    }
}
