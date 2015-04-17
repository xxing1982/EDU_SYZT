package com.syzton.sunread.model.exam;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.util.DateSerializer;

@Entity
@DiscriminatorValue("subjective")
public class SubjectiveAnswer extends Answer {
	
	@Column
	private String content;
	
	@Column
	private String comment;
	
	@JsonSerialize(using = DateSerializer.class)
	@Column(name = "comment_time", nullable = true)
	private DateTime commentTime;
	 
	public String getContent() {
		return content;
	}

	public String getComment() {
		return comment;
	}

	public void setCommentTime(DateTime commentTime) {
		this.commentTime = commentTime;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public DateTime getCommentTime() {
		return commentTime;
	}

	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static class Builder {

		private SubjectiveAnswer built;

		public Builder() {
			built = new SubjectiveAnswer();
		}

		public SubjectiveAnswer build() {
			return built;
		}

	 
	}

}
