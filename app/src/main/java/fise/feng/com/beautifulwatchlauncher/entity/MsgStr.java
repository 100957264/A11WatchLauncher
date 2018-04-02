package fise.feng.com.beautifulwatchlauncher.entity;

/**
 * Created by qingfeng on 2018/1/19.
 */

public class MsgStr {
    public String msgTye;
    public String msgContent;

    public MsgStr(String msgTye, String msgContent) {
        this.msgTye = msgTye;
        this.msgContent = msgContent;
    }

    public String getMsgTye() {
        return msgTye;
    }

    public void setMsgTye(String msgTye) {
        this.msgTye = msgTye;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public String toString() {
        return "MsgStr{" +
                "msgTye='" + msgTye + '\'' +
                ", msgContent='" + msgContent + '\'' +
                '}';
    }
}
