package fise.feng.com.beautifulwatchlauncher.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingfeng on 2018/1/22.
 */
@Entity
public class NotificationEntity {
    @Id(autoincrement = true)
    public Long id;
    public String packageName;
    public String title;
    public String content;
    public long time;
    public String type;
    public String isReaded;
    @Generated(hash = 816379542)
    public NotificationEntity(Long id, String packageName, String title,
            String content, long time, String type, String isReaded) {
        this.id = id;
        this.packageName = packageName;
        this.title = title;
        this.content = content;
        this.time = time;
        this.type = type;
        this.isReaded = isReaded;
    }
    @Generated(hash = 1877229834)
    public NotificationEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPackageName() {
        return this.packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
    public long getTime() {
        return this.time;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getIsReaded() {
        return this.isReaded;
    }
    public void setIsReaded(String isReaded) {
        this.isReaded = isReaded;
    }
    public void setTime(long time) {
        this.time = time;
    }



}
