package com.syzton.sunread.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="teacher")
@DiscriminatorValue("T")
public class Teacher extends User{

}

