package com.syzton.sunread.model.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.util.DateSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by jerry on 3/13/15.
 */
@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;


    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime creationTime;

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }
    
    public void setCreationTime(DateTime creationTime) {
    	this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        return this.id.equals(that.getId());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }


}
