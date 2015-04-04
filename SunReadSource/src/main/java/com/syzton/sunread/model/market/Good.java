package com.syzton.sunread.model.market;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.syzton.sunread.model.common.AbstractEntity;

@Entity
@Table(name="good")
public class Good extends AbstractEntity{
	
	private String name;
	
	private int storeCount;
	
	private int coin;
	
	private String imagePath; 
	
	private String describe;
	
	public String size;
	
	public String color;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(int storeCount) {
		this.storeCount = storeCount;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
