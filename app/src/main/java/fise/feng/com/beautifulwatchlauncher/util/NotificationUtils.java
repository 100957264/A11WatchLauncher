package fise.feng.com.beautifulwatchlauncher.util;

import fise.feng.com.beautifulwatchlauncher.entity.MsgStr;
import fise.feng.com.beautifulwatchlauncher.entity.NotficationTxt;
import fise.feng.com.beautifulwatchlauncher.entity.NotificationEntity;
import fise.feng.com.beautifulwatchlauncher.event.NotificationComingEvent;
import fise.feng.com.beautifulwatchlauncher.socket.BluetoothSppHelper;
import fise.feng.com.beautifulwatchlauncher.socket.message.StringMessage;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qingfeng on 2018/1/18.
 */

public class NotificationUtils {

    public static void sendMsg(NotficationTxt notficationTxt){
        StringMessage message = new StringMessage();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(notficationTxt);
        MsgStr msgStr=new MsgStr("N",jsonStr);
        String msgJson = gson.toJson(msgStr);
        message.setContent(msgJson,"");
        BluetoothSppHelper.instance().write(message);
    }

    public static void recvMsg(StringMessage msg){
        String msgContent =   msg.getContent();
        LogUtils.d("msgContent =" + msgContent);
        Gson gson = new Gson();
        MsgStr msgStr =  gson.fromJson(msgContent,MsgStr.class);
        String msgType = msgStr.getMsgTye();
        switch (msgType){
            case "N":
                String notficationTxtStr = msgStr.getMsgContent();
                NotficationTxt notficationTxt =  gson.fromJson(notficationTxtStr,NotficationTxt.class);
                String title = notficationTxt.getTitle();
                String content = notficationTxt.getContent();
                String appName = notficationTxt.getAppName();
                long time = notficationTxt.getTime();
                setNotificationEntity(appName,title,time,content,msgType,"0");
                LogUtils.d("title =" + title + ",content =" + content + ",appName =" + appName);
                break;
            case "S":
                String mmsTxtStr = msgStr.getMsgContent();
                NotficationTxt mmsTxt =  gson.fromJson(mmsTxtStr,NotficationTxt.class);
                String mmstitle = mmsTxt.getTitle();
                String mmscontent = mmsTxt.getContent();
                String mmsappName = mmsTxt.getAppName();
                long mmstime = mmsTxt.getTime();
                setNotificationEntity(mmsappName,mmstitle,mmstime,mmscontent,msgType,"0");
                //NotificationUtils.recvMsg((StringMessage) message);
                break;
            default:
                break;
        }
    }
    public static void setNotificationEntity(String appName, String title, long time, String content, String type, String isRead){
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setPackageName(appName);
        notificationEntity.setTitle(title);
        notificationEntity.setContent(content);
        notificationEntity.setTime(time);
        notificationEntity.setType(type);
        notificationEntity.setIsReaded(isRead);
        NotificationComingEvent notificationComingEvent = new NotificationComingEvent(notificationEntity);
        EventBus.getDefault().post(notificationComingEvent);
    }
}
