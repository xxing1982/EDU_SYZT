package com.syzton.sunread.dto.clazz;

import java.util.List;

/**
 * Created by jerry on 4/26/15.
 */
public class ClazzSumStatisticDTO {
    
	public long maxCoin;
	public long minCoin;
	public long avgCoin;
	
	public long maxReadNum;
	public long minReadNum;
	public long avgReadNum;
	
	public long maxReadWord;
	public long minReadWord;
	public long avgReadWord;
	
	public List<ClassSum> classSums;
	public ClassCategoryData classCategoryData;
	
    public class ClassSum {
    	public String name;
    	public long coin;
    	public long readNum;
    	public long readWord;
    }
    
    public class ClassCategoryData {
    	public List<String> labels;
    	public List<Data> dataList;
    	public long maxData;
    	public long minData;
    	public long avgData;
     	public class Data {
     		public List<Integer> data;     		
     	}
    }
}
