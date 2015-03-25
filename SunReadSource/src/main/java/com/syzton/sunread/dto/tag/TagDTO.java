package com.syzton.sunread.dto.tag;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import com.syzton.sunread.model.tag.Tag;


/**
 * @author chenty
 *
 */
public class TagDTO {
	
    private Long id;
    
    @NotEmpty
    @Length(max = Tag.MAX_LENGTH_NAME)
	private String name;


    @NotEmpty
    @Length(max = Tag.MAX_LENGTH_VALUE)
    private String value;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
