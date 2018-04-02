package fise.feng.com.beautifulwatchlauncher.util;

import android.telephony.TelephonyManager;

/**
 * @author mare
 * @Description:TODO
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/11/18
 * @time 21:26
 */
public class DataActivityUtils {

    /**
     * 解析数据流量上下行方向
     *
     * @param direction
     * @return result[0]上行是否可见 result[1]下行是否可见
     */
    public static boolean[] getMobileDataVis(int direction) {
        LogUtils.i("解析数据流量方向 " + direction);
        boolean[] result = new boolean[2];
        switch (direction) {
            case TelephonyManager.DATA_ACTIVITY_IN://1
                result[1] = true;
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT://2
                result[0] = true;
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT://3
                result[0] = true;
                result[1] = true;
                break;
            case TelephonyManager.DATA_ACTIVITY_NONE:// 0
            case TelephonyManager.DATA_ACTIVITY_DORMANT://4
            default:
                LogUtils.i("没有使用数据流量 ！！！");
                break;
        }
        return result;
    }
}
