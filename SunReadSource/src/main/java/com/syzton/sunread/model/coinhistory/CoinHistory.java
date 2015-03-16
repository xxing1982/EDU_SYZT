package com.syzton.sunread.model.coinhistory;

import com.syzton.sunread.model.common.AbstractEntity;
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
	
//  @OneToMany(cascade = CascadeType.ALL,mappedBy = "coinHistory")
//  @Basic(fetch = FetchType.LAZY)
//	private Set<BookCoinHistory> bookCoinHistorys;
//
	
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
