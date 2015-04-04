package com.syzton.sunread.dto.exam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.syzton.sunread.model.exam.Option;

public class OptionDTO {
	private Long id;
	
	@NotEmpty
	private String tag;
	
	@NotEmpty
	private String content;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
	
	public Option OTD(){
//		Option option = Option.getBuilder(tag).content(content).id(id).build();
		return null;
	}
}
