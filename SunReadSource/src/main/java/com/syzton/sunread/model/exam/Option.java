package com.syzton.sunread.model.exam;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.syzton.sunread.dto.exam.OptionDTO;
import com.syzton.sunread.todo.model.Todo;
import com.syzton.sunread.todo.model.Todo.Builder;

@Entity 
@Table(name = "option")  
public class Option {
	
	@Id   
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private String tag;
	@Column(nullable=false)
	private String content;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Option)){
			return false;
		}
		Option option = (Option)obj;
		return (option.tag+option.content).equals(this.tag+this.content);
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
	        
	        public Builder id(Long id){
	        	built.id = id;
	        	return this;
	        }
	        
	        public Option build(){
	        	return built;
	        }
	        
	        public Builder content(String content){
	        	built.content = content;
	        	return this;
	        }

	    }
	
	public OptionDTO createDTO(){
		OptionDTO optionDTO = new OptionDTO();
		
		return optionDTO;
	}
}
