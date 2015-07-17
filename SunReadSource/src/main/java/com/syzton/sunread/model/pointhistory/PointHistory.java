package com.syzton.sunread.model.pointhistory;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.Student;

/**
 * @author chenty
 *
 */
@Entity
@Table(name="point_history")
public class PointHistory extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private PointType pointType;
	
	public enum PointType{IN, OUT}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private PointFrom pointFrom;
	
	public enum PointFrom{FROM_NOTE, FROM_BOOK, FROM_TEACHER,FROM_VERIFY_TEST}
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
	private Student student;
	
	@Column
	private int num;
    
	@Column(name="form_id")
	private Long fromId;
	
    
	@Column(name="reason")
	private String reason;
	
    public PointHistory() {

    }

	public PointType getPointType() {
		return pointType;
	}

	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}
    
	
	public PointFrom getPointFrom() {
		return pointFrom;
	}

	public void setPointFrom(PointFrom pointFrom) {
		this.pointFrom = pointFrom;
	}
    
    public Long getStudentId() {
		return student.getId();
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
    public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
