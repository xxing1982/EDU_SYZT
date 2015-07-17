/**
 * 
 */
package com.syzton.sunread.dto.bookshelf;

import java.util.ArrayList;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.util.DateSerializer;

/**
 * @author Morgan-Leon
 * @Date 2015年4月9日
 * 
 */
public class BookshelfStatisticsDTO {
	
	public class VerifiedBook {
		public String bookName;
		
		public String author;
		
		public int wordCount; 
		
		public int point;
		
	    @JsonSerialize(using = DateSerializer.class)
	    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
		public DateTime verifiedTime;
	    
		public String category;
	}
		
	public int semesterReadNum;

	public ArrayList<VerifiedBook> semesterVerified;
	
	public int[] monthlyVerifiedNums;
	
	public int[] monthlyPoints;
}
