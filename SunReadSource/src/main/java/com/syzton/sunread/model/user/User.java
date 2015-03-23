package com.syzton.sunread.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.security.Role;
import com.syzton.sunread.util.DateSerializer;

import com.syzton.sunread.util.NumberUtil;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.UUID;

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
    public static final int MAX_LENGTH_PASSWORD = 16;
    public static final int MAX_LENGTH_NICKNAME = 15;
    public static final int MAX_LENGTH_PHONENUMBER = 11;
    public static final int MAX_LENGTH_ADDRESS = 100;

    @NotEmpty
    @Column(nullable = false,length = MAX_LENGTH_USERNAME )
    private String username;

    private String userId;

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

    @JsonSerialize(using = DateSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthday;

    @Enumerated(EnumType.STRING)
    private GenderType gender = GenderType.MALE;

    
    @ManyToMany(mappedBy="users",cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
    private List<Role> roles = new ArrayList<Role>();


    private String email;

    @Column(length = MAX_LENGTH_ADDRESS)
    private String address;





    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.userId = String.valueOf(NumberUtil.generateRandom16());
    }


    public enum GenderType {

        MALE,FAMALE

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

    public DateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
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

    
	public void setRoles(List<Role> roles) {
		this.roles = roles;
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

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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

