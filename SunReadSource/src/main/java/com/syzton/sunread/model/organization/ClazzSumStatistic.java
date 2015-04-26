package com.syzton.sunread.model.organization;

/**
 * Created by jerry on 4/26/15.
 */
public class ClazzSumStatistic {

    private double avgRead;

    private int maxRead;

    private int minRead;

    private double avgReadWord;

    private int maxReadWord;

    private int minReadWord;

    public ClazzSumStatistic(double avgRead,int maxRead,int minRead,
                             double avgReadWord,int maxReadWord,int minReadWord){
        this.avgRead = avgRead;
        this.maxRead = maxRead;
        this.minRead = minRead;
        this.avgReadWord = avgReadWord;
        this.maxReadWord = maxReadWord;
        this.minReadWord = minReadWord;
    }

    public double getAvgRead() {
        return avgRead;
    }

    public int getMaxRead() {
        return maxRead;
    }

    public int getMinRead() {
        return minRead;
    }

    public double getAvgReadWord() {
        return avgReadWord;
    }

    public int getMaxReadWord() {
        return maxReadWord;
    }

    public int getMinReadWord() {
        return minReadWord;
    }
}
