package com.syzton.sunread.model.coinhistory;

import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.User;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

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
	
	public enum CoinType{IN, OUT}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private CoinFrom coinFrom;
	
	public enum CoinFrom{FROM_NOTE, FROM_BOOK, FROM_TEACHER}
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User user;
    
	
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

//    public Set<BookCoinHistory> getBookCoinHistorys() {
//		return bookCoinHistorys;
//	}

    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}