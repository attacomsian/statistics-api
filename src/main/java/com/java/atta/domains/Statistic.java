package com.java.atta.domains;

/**
 *
 * Created by atta on 22/6/18
 */
public class Statistic {

    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;

    public Statistic() {
    }

    public Statistic(double sum, double avg, double max, double min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public double getAvg() {
        return avg;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Statistic{" + "sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count + '}';
    }
}
