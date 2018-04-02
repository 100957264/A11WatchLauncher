package fise.feng.com.beautifulwatchlauncher.event;

/**
 * Created by qingfeng on 2018/2/28.
 */

public class SOSListUpdateEvent {
     public String number="";
     public String name = "";

    public SOSListUpdateEvent(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return "SOSListUpdateEvent{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
