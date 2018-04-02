package fise.feng.com.beautifulwatchlauncher.event;

/**
 * Created by qingfeng on 2018/1/22.
 */

public class BlueSocketStatusEvent {
    public String status;

    public BlueSocketStatusEvent(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BlueSocketStatusEvent{" +
                "status='" + status + '\'' +
                '}';
    }
}
