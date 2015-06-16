package com.syzton.sunread.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="analyst")
@DiscriminatorValue("A")
public class Analyst extends User{


    private Long regionId;

    private Long eduGroupId;

    private Long schoolDistrictId;

    private Long campusId;


    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getEduGroupId() {
        return eduGroupId;
    }

    public void setEduGroupId(Long eduGroupId) {
        this.eduGroupId = eduGroupId;
    }

    public Long getSchoolDistrictId() {
        return schoolDistrictId;
    }

    public void setSchoolDistrictId(Long schoolDistrictId) {
        this.schoolDistrictId = schoolDistrictId;
    }

    public Long getCampusId() {
        return campusId;
    }

    public void setCampusId(Long campusId) {
        this.campusId = campusId;
    }
}

