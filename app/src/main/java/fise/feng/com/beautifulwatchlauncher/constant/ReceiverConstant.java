package fise.feng.com.beautifulwatchlauncher.constant;

/**
 * @author mare
 * @Description:
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/8/31
 * @time 11:29
 */
public class ReceiverConstant {

    /**
     * 隐藏来电去电界面
     */
    public static final String ACTION_HIDE_INCALL_UI = "com.fise.broadcast.ACTION_LISTEN";
    /****短信相关****/
    public static final String ACTION_SMS_RELATIVE = "com.android.action.fise.sms.relative";
    public static final String EXTRA_SMS_FLAG = "flag_state";
    public static final String EXTRA_SMS_SEND_NUM = "flag_send_num";
    public static final int EXTRA_SMS_SEND_FLAG = 1000;//短信是否发送成功
    public static final int EXTRA_SMS_DELIVERY_FLAG = 1001;//短信发送是否被收到

}
