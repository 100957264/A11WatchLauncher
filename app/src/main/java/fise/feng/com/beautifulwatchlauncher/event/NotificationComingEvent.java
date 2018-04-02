package fise.feng.com.beautifulwatchlauncher.event;

import fise.feng.com.beautifulwatchlauncher.entity.NotificationEntity;

/**
 * Created by qingfeng on 2018/1/22.
 */

public class NotificationComingEvent {
   public NotificationEntity notificationEntity;

    public NotificationComingEvent(NotificationEntity notificationEntity) {
        this.notificationEntity = notificationEntity;
    }

    @Override
    public String toString() {
        return "NotificationComingEvent{" +
                "notificationEntity=" + notificationEntity +
                '}';
    }
}
