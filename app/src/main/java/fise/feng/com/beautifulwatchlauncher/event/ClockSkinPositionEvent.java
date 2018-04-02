package fise.feng.com.beautifulwatchlauncher.event;

/**
 * Created by qingfeng on 2018/1/15.
 */

public class ClockSkinPositionEvent {
    public int position;

    public ClockSkinPositionEvent(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ClockSkinPositionEvent{" +
                "position=" + position +
                '}';
    }
}
