package com.syzton.sunread.model.exam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.syzton.sunread.dto.exam.OptionDTO;
import com.syzton.sunread.model.common.AbstractEntity;

@Entity
@Table(name = "question_option")
public class Option extends AbstractEntity {

	@Column(name = "tag", nullable = false)
	private String tag;

	@Column(name = "content", nullable = false)
	private String content;

	// @JsonIgnore
	// @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional =
	// true)
	// @JoinColumn(name="question_id")
	// private ObjectiveQuestion question;
	//
	// public ObjectiveQuestion getQuestion() {
	// return question;
	// }
	//
	// public void setQuestion(ObjectiveQuestion question) {
	// this.question = question;
	// }

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Option)) {
			return false;
		}
		Option option = (Option) obj;
		return (option.id).equals(this.id);
	}

	public static Builder getBuilder(String tag) {
		return new Builder(tag);
	}

	public static class Builder {

		private Option built;

		public Builder(String tag) {
			built = new Option();
			built.tag = tag;

		}

		public Builder id(Long id) {
			built.id = id;
			return this;
		}

		public Option build() {
			return built;
		}

		public Builder content(String content) {
			built.content = content;
			return this;
		}

	}

	public void setCreationTime(String creationTime) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		// 时间解析
		DateTime dateTime2 = DateTime.parse(creationTime, format);
		this.creationTime = dateTime2;
	}

	public OptionDTO createDTO() {
		OptionDTO optionDTO = new OptionDTO();

		return optionDTO;
	}
}
