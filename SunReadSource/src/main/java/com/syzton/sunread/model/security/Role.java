package com.syzton.sunread.model.security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.User;

@Entity
public class Role  extends AbstractEntity{
	
	public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 32;
	
    @Column(name = "rolename",nullable = false,length = MAX_LENGTH_NAME )
	private String name;
	

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
	private String desc;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<User> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	} 
}
