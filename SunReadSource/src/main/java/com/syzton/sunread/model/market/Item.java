package com.syzton.sunread.model.market;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.syzton.sunread.model.common.AbstractEntity;

@Entity
@Table(name="item")
public class Item extends AbstractEntity{
	
	@OneToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,optional = false)
	private Good good;
	
	private int Num;

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}
}
