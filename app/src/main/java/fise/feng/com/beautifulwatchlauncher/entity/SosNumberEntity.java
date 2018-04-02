package fise.feng.com.beautifulwatchlauncher.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by qingfeng on 2018/1/22.
 */
@Entity
public class SosNumberEntity {
    @Id(autoincrement = true)
    public Long id;

    public String number;
    public String name;
    @Generated(hash = 2104299698)
    public SosNumberEntity(Long id, String number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }
    @Generated(hash = 460704632)
    public SosNumberEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
