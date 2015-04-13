/**
 * 
 */
package com.syzton.sunread.dto.bookshelf;

import java.util.ArrayList;

/**
 * @author Morgan-Leon
 * @Date 2015年4月9日
 * 
 */
public class BookshelfStatisticsDTO {
	
	private String username;
	
	private ArrayList<String> monthly;
	
	private ArrayList<Integer> monthlyVerified;
	
	private ArrayList<Integer> monthlyPoints;
	
	private int semesterVerified;

	private int semesterPoints;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<String> getMonthly() {
		return monthly;
	}

	public void setMonthly(ArrayList<String> monthly) {
		this.monthly = monthly;
	}

	public int getSemesterVerified() {
		return semesterVerified;
	}

	public void setSemesterVerified(int semesterVerified) {
		this.semesterVerified = semesterVerified;
	}

	public ArrayList<Integer> getMonthlyVerified() {
		return monthlyVerified;
	}

	public void setMonthlyVerified(ArrayList<Integer> monthlyVerified) {
		this.monthlyVerified = monthlyVerified;
	}

	public ArrayList<Integer> getMonthlyPoints() {
		return monthlyPoints;
	}

	public void setMonthlyPoints(ArrayList<Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	public int getSemesterPoints() {
		return semesterPoints;
	}

	public void setSemesterPoints(int semesterPoints) {
		this.semesterPoints = semesterPoints;
	}
	
	
	
	

}
