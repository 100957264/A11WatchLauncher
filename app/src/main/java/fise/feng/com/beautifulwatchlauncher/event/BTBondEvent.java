package fise.feng.com.beautifulwatchlauncher.event;

/**
 * Created by qingfeng on 2018/1/13.
 */

public class BTBondEvent {
    public boolean ret = false;
    public String imei = "";
    public String btAdreess = "";

    public BTBondEvent(boolean ret, String imei, String btAdreess) {
        this.ret = ret;
        this.imei = imei;
        this.btAdreess = btAdreess;
    }

    @Override
    public String toString() {
        return "BTBondEvent{" +
                "ret=" + ret +
                ", imei='" + imei + '\'' +
                ", btAdreess='" + btAdreess + '\'' +
                '}';
    }
}
