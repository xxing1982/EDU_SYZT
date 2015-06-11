package com.syzton.sunread.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Table(name = "admin")
@DiscriminatorValue("A")
@Configurable
public class Admin extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1138189518479020939L;
	
	private long campusId;
	
	@JoinColumn(name = "super_admin")
	private boolean superAdmin;
	
	public long getCampusId() {
		return campusId;
	}

	public void setCampusId(long campusId) {
		this.campusId = campusId;
	}

	
	
	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
}
