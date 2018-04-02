package fise.feng.com.beautifulwatchlauncher.event;

/**
 * @author mare
 * @Description:TODO
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/9/4
 * @time 15:02
 */
public class DataActivityUpdateEvent {

    public int direction;
    public static final int DATA_ACTIVITY_IN = 1;
    public static final int DATA_ACTIVITY_INOUT = 3;
    public static final int DATA_ACTIVITY_NONE = 0;
    public static final int DATA_ACTIVITY_OUT = 2;

//    DATA_ACTIVITY_DORMANT：数据连接处于活动状态，可是物理连接时关闭的
//    DATA_ACTIVITY_IN：数据连接处于活动状态,当前接受IP PPP流量
//    DATA_ACTIVITY_INOUT：接受和发送IP PPP流量
//    DATA_ACTIVITY_NONE：数据连接处于活动状态，可是无流量
//    DATE_ACTIVITY_OUT：数据连接属于连接状态，发送IP PPP流量

    public DataActivityUpdateEvent(int direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "DataActivityUpdateEvent{" +
                "direction=" + direction +
                '}';
    }
}
