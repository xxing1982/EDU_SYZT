package com.syzton.sunread.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Table(name = "system_admin")
@DiscriminatorValue("SA")
@Configurable
public class SystemAdmin extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1138189518479020939L;
	
	@JoinColumn(name = "super_admin")
	private boolean superAdmin;
	
	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
}
