package com.syzton.sunread.model.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="users")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING,length=20)
@DiscriminatorValue("U")
public class User extends AbstractEntity{

    public static final int MAX_LENGTH_USERNAME = 15;
    public static final int MAX_LENGTH_PASSWORD = 16;
    public static final int MAX_LENGTH_NICKNAME = 15;
    public static final int MAX_LENGTH_PHONENUMBER = 11;


    @Column(name = "username",nullable = false,length = MAX_LENGTH_USERNAME )
    private String username;

    @Column(nullable = false,length = MAX_LENGTH_PASSWORD )
    private String password;


    @Column(length = MAX_LENGTH_NICKNAME )
    private String nickname;

    @Range(max = 130)
    private int age;

    @Column(nullable = false,length = MAX_LENGTH_PHONENUMBER)
    private String phoneNumber;

    @JsonSerialize(using = DateSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthday;


    private boolean gender;

    @Email
    private String email;


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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

