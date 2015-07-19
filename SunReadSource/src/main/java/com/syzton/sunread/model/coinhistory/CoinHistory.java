package com.syzton.sunread.model.coinhistory;

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
@Table(name="coin_history")
public class CoinHistory extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private CoinType coinType;
	
	public enum CoinType{IN, OUT};
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private CoinFrom coinFrom;
	
	public enum CoinFrom{FROM_NOTE, FROM_BOOK, FROM_TEACHER,FROM_VERIFY_TEST,FROM_GIFT}
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
	private Student student;
	
	@Column(name="num")
	private int num;
	
	@Column(name="form_id")
	private Long fromId;
    
	@Column(name="reason")
	private String reason;
	
    public Long getStudentId() {
		return student.getId();
	}
    
    public String getStudentUsername() {
		return student.getUsername();
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

	public CoinHistory() {

    }

	public CoinType getCoinType() {
		return coinType;
	}

	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
    
	
	public CoinFrom getCoinFrom() {
		return coinFrom;
	}

	public void setCoinFrom(CoinFrom coinFrom) {
		this.coinFrom = coinFrom;
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
