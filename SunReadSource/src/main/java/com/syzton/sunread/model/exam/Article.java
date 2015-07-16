package com.syzton.sunread.model.exam;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import com.syzton.sunread.model.common.AbstractEntity;

@Entity
@Table(name = "article")
public class Article extends AbstractEntity {
	@Column
	private String topic;
	@Column
	private String author;
	@Column
	private long charCount;
	
	@Basic(fetch = FetchType.LAZY) 
    @Column(name="content", columnDefinition="TEXT")
	private String content;
	@Column
	private int level;
	@Column
	private boolean isTraining;
	                

	public boolean isTraining() {
		return isTraining;
	}

	public void setTraining(boolean isTraining) {
		this.isTraining = isTraining;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getCharCount() {
		return charCount;
	}

	public void setCharCount(long charCount) {
		this.charCount = charCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	
	
}
