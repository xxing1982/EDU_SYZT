package com.syzton.sunread.model.exam;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("speed")
public class SpeedQuestion extends ObjectiveQuestion{
	@Column(name="article_id")
	private long articleId;

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

}
