package com.syzton.sunread.model.organization;

import javax.persistence.Column;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public class EducationSys extends AbstractEntity {

    public static final int MAX_LENGTH_DESCRIPTION = 500;

    @Column(name = "name", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimea")
    protected String name;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    protected String description;

    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime creationTime = DateTime.now();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @PrePersist
    public void prePersist() {
        this.creationTime = DateTime.now();
    }


    public void update(String name) {
        this.name = name;
    }


    public static class Builder {

        private EducationSys built;

        public Builder() {
            // TODO Auto-generated constructor stub
            built = new EducationSys();
        }

        public EducationSys build() {
            return built;
        }

        public Builder(String name) {
            built = new EducationSys();
            built.name = name;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }
    }
}

