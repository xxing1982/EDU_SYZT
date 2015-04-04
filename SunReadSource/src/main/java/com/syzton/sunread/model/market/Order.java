package com.syzton.sunread.model.market;

import java.util.List;

import javax.persistence.Column;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.util.DateSerializer;

public class Order {

	private Long StudentId;

	@JsonSerialize(using = DateSerializer.class)
	@Column
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime Date;
	
	
	private List<Item> items;
	
	private String address;
}
