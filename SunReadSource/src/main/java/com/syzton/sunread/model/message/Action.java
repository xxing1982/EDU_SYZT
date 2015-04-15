package com.syzton.sunread.model.message;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="action")
public class Action extends AbstractEntity{
	
	public static final int MAX_LENGTH_CONTENT = 1000;
	public static final int MAX_LENGTH_TITLE = 100;

	@Column(nullable = false,length = MAX_LENGTH_TITLE)
	private String title;

	@Column(nullable = false,length = MAX_LENGTH_CONTENT)
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
