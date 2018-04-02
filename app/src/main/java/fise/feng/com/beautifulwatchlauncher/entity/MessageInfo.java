package fise.feng.com.beautifulwatchlauncher.entity;

import android.graphics.drawable.Drawable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingfeng on 2017/12/30.
 */
@Entity
public class MessageInfo {
    @Id
    private Long id;

    public String title;
    public String content;
    @Transient
    public Drawable icon;
    public String pkgName;
    @Generated(hash = 1526115751)
    public MessageInfo(Long id, String title, String content, String pkgName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pkgName = pkgName;
    }
    @Generated(hash = 1292770546)
    public MessageInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPkgName() {
        return this.pkgName;
    }
    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
