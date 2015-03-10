package com.syzton.sunread.model.tag;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.IndexColumn;
import com.syzton.sunread.model.booktag.BookTag;

import javax.persistence.*;

import java.util.Collection;


/**
 * @author chenty
 *
 */
@Entity
@Table(name="Tag")
public class Tag {

    public static final int MAX_LENGTH_NAME = 20;
    public static final int MAX_LENGTH_VALUE = 20;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name",unique = true,nullable = false,length = MAX_LENGTH_NAME)
    private String name;

    @Column(name ="value",nullable = false,length = MAX_LENGTH_VALUE)
    private String value;

	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="tag_id")
	@IndexColumn(name="idx")
	private Collection<BookTag> bookTags;

    
    public Tag() {

    }

    public static Builder getBuilder(String name,String value) {
        return new Builder(name, value);
    }

    public Long getId() {
        return id;   
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
