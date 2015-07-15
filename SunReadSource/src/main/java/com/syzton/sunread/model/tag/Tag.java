package com.syzton.sunread.model.tag;
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

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	private Type type;

    @Column(name ="name",nullable = false,length = MAX_LENGTH_NAME)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tag")
    @Basic(fetch = FetchType.LAZY)
	private Set<BookTag> bookTags;
    
    
    public Tag() {

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
	public void update(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
