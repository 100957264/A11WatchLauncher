package fise.feng.com.beautifulwatchlauncher.clock.util;

/**
 * Created by qingfeng on 2017/12/29.
 */

public class SportsStepCountInfo {
    private long getTime;
    private long stepCount;
    private double distance;
    private double kcal;

    public SportsStepCountInfo() {

    }

    public long getStepCount() {
        return stepCount;
    }

    public void setStepCount(long stepCount) {
        this.stepCount = stepCount;
    }

    public long getGetTime() {
        return getTime;
    }

    public void setGetTime(long getTime) {
        this.getTime = getTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    @Override
    public String toString() {
        return "SportsStepCountInfo{" +
                "getTime=" + getTime +
                ", stepCount=" + stepCount +
                ", distance=" + distance +
                ", kcal=" + kcal +
                '}';
    }
}
