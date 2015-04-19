package com.syzton.sunread.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.security.Role;
import com.syzton.sunread.util.DateSerializer;

import com.syzton.sunread.util.NumberUtil;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="users")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING,length=10)
@DiscriminatorValue("U")
public class User extends AbstractEntity implements UserDetails{

    public static final int MAX_LENGTH_USERNAME = 15;
    public static final int MAX_LENGTH_PASSWORD = 128;
    public static final int MAX_LENGTH_NICKNAME = 15;
    public static final int MAX_LENGTH_PHONENUMBER = 11;
    public static final int MAX_LENGTH_ADDRESS = 100;

    public static final String DEFAULT_USER_PICTURE_URL ="";

    @NotEmpty
    @Column(nullable = false,length = MAX_LENGTH_USERNAME )
    private String username;

    private String userId;

    private String picture = DEFAULT_USER_PICTURE_URL;

    @NotEmpty
    @Column(nullable = false,length = MAX_LENGTH_PASSWORD )
    private String password;


    @Column(length = MAX_LENGTH_NICKNAME )
    private String nickname;

    @Range(max = 130)
    private int age;

    @NotEmpty
    @Column(nullable = false,length = MAX_LENGTH_PHONENUMBER)
    private String phoneNumber;

    private String contactPhone;

    @JsonIgnore
    @Column(name = "birthday")
    @JsonSerialize(using = DateSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthdayDB;

    @Transient
    private long birthday;
    @JsonIgnore
    @Column(name="expireTime")
    @JsonSerialize(using = DateSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime expireTimeDB;

    @Transient
    private long expireTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.valid;

    @Enumerated(EnumType.STRING)
    private GenderType gender = GenderType.male;

    
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="e_user_role",    
    joinColumns={    
        @JoinColumn(name="u_id",referencedColumnName="id")    
        },    
        inverseJoinColumns={    
         @JoinColumn(name="r_id",referencedColumnName="id")    
    }) 
    private List<Role> roles = new ArrayList<Role>();


    private String email;

    @Column(length = MAX_LENGTH_ADDRESS)
    private String address;

    private String qqId;

    private String wechatId;

    private boolean enabled;
    
    private boolean accountNonExpired;
    
    private boolean accountNonLocked;
    
    private boolean credentialsNonExpired;



    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.userId = String.valueOf(NumberUtil.generateRandom16());
        this.expireTimeDB = new DateTime(this.expireTime);
        this.birthdayDB = new DateTime(this.birthday);

        //TODO need to define user expireTime default value
    }


    public enum GenderType {

        male,famale

    }

    public enum Status {
        valid,invalid,locked
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public void setRoles(List<Role> roles)   {
		this.roles = roles;
	}

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public long getExpireTime() {

        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }


    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		   for( Role role : this.getRoles() ){
		      GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
		      authorities.add(authority);
		   }
		   return authorities;
	}
    @Transient
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
    @Transient
	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
    @Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
    @Transient
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public List<Role> getRoles() {
        return Collections.unmodifiableList(this.roles);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public boolean hasRole(Role role) {
        return (this.roles.contains(role));
    }

}

