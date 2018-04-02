package fise.feng.com.beautifulwatchlauncher.event;

/**
 * Created by qingfeng on 2018/1/24.
 */

public class BTStateChangeEvent {
   public  boolean isBTOn = true;

    public BTStateChangeEvent(boolean isBTOn) {
        this.isBTOn = isBTOn;
    }

    @Override
    public String toString() {
        return "BTStateChangeEvent{" +
                "isBTOn=" + isBTOn +
                '}';
    }
}
