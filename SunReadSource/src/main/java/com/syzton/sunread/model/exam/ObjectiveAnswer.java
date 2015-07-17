package com.syzton.sunread.model.exam;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("objective")
public class ObjectiveAnswer extends Answer {
	
	@OneToOne(cascade=CascadeType.REFRESH)  
    @JoinColumn(name="option_id")  
	private Option option;
	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static class Builder {

		private ObjectiveAnswer built;

		public Builder() {
			built = new ObjectiveAnswer();
		}

		public ObjectiveAnswer build() {
			return built;
		}

	 
	}
	

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}
}
