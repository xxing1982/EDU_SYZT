package com.syzton.sunread.model.tag;
import com.syzton.sunread.dto.tag.TagDTO;
import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;


/**
 * @author chenty
 *
 */
@Entity
@Table(name="tag")
public class Tag extends AbstractEntity {

    public static final int MAX_LENGTH_NAME = 20;
    public static final int MAX_LENGTH_VALUE = 20;

    @Column(name="name",nullable = false,length = MAX_LENGTH_NAME)
    private String name;

    @Column(name ="value",nullable = false,length = MAX_LENGTH_VALUE)
    private String value;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tag")
    @Basic(fetch = FetchType.LAZY)
	private Set<BookTag> bookTags;
    
    
    public Tag() {

    }

    public static Builder getBuilder(String name,String value) {
        return new Builder(name, value);
    }
    
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    
//    public Set<BookTag> getBookTags() {
//		return bookTags;
//	}

	public void update(String name, String value) {
        this.name = name;
        this.value = value;
    }

    
    public static class Builder {

        private Tag built;

        public Builder(String name, String value) {
            built = new Tag();
            built.name = name;
            built.value = value;
        }

        public Tag build() {
            return built;
        }
    }

    public TagDTO createDTO(Tag model) {
        TagDTO dto = new TagDTO();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setValue(model.getValue());

        return dto;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
