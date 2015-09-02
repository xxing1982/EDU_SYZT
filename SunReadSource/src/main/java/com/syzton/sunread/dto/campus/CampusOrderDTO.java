package com.syzton.sunread.dto.campus;

import java.util.List;

import com.syzton.sunread.model.user.UserStatistic;

/**
 * Created by Eiddlechen on 4/26/15.
 */
public class CampusOrderDTO {
	
	public String name;
	public long point;
	public long testPasses;
	public long coin;
	public long notes;
	
	public CampusOrderDTO (String name, double point, double testPasses, double coin, double notes){
		this.name = name;
		this.coin = (long) coin;
		this.point = (long) point;
		this.testPasses = (long) testPasses;
		this.notes = (long) notes;
	}
	

}
