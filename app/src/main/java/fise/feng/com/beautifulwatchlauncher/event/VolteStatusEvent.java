package fise.feng.com.beautifulwatchlauncher.event;

/**
 * Created by qingfeng on 2017/9/25.
 */

public class VolteStatusEvent {
    public int volteStatus = 0;
    public VolteStatusEvent(int volteStatus){
        this.volteStatus = volteStatus;
    }
    @Override
    public String toString() {
        return "VolteStatusEvent {" +
                "volteStatus=" + volteStatus +
                '}';
    }
}
