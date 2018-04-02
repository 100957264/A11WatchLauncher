package fise.feng.com.beautifulwatchlauncher.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by qingfeng on 2018/1/13.
 */
@Entity
public class BTBondEntity {
    @Id(autoincrement = true)
    public Long id;
    public String btAdress;
    public String imei;

    @Generated(hash = 609940972)
    public BTBondEntity(Long id, String btAdress, String imei) {
        this.id = id;
        this.btAdress = btAdress;
        this.imei = imei;
    }

    @Generated(hash = 1978168768)
    public BTBondEntity() {
    }

    @Override
    public String toString() {
        return "BTBondEntity{" +
                "id=" + id +
                ", btAdress='" + btAdress + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBtAdress() {
        return this.btAdress;
    }

    public void setBtAdress(String btAdress) {
        this.btAdress = btAdress;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
