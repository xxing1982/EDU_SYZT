package com.syzton.sunread.model.organization;

/**
 * Created by jerry on 4/26/15.
 */
public class ClazzSumStatistic {

    private double avgRead;

    private int maxRead;

    private int minRead;

    private double avgCoin;

    private int maxCoin;

    private int minCoin;

    private double avgReadWord;

    private int maxReadWord;

    private int minReadWord;

    public ClazzSumStatistic(double avgRead,int maxRead,int minRead,
                             double avgReadWord,int maxReadWord,int minReadWord,
                             double avgCoin,int maxCoin,int minCoin){
        this.avgRead = avgRead;
        this.maxRead = maxRead;
        this.minRead = minRead;
        this.avgCoin = avgCoin;
        this.maxCoin = maxCoin;
        this.minCoin = minCoin;
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

    public double getAvgCoin() {
        return avgCoin;
    }

    public int getMaxCoin() {
        return maxCoin;
    }

    public int getMinCoin() {
        return minCoin;
    }
}
