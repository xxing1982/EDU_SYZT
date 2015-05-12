/**
 * 
 */
package com.syzton.sunread.dto.recommend;

import javax.validation.constraints.NotNull;

/**
 * @author Morgan-Leon
 * @Date 2015年5月12日
 * 
 */
public class RecommendDTO {
	
	@NotNull
	private Long bookId;
	
    private String bookName = "";
    
    private Long teacherId;
    
    private String teacherName = "";
    
    private Long bookshelfId;
    
    private String studentName = "";
	
    @NotNull
    private boolean bookAttribute;
    
    
    private boolean readState = false;
    
	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public boolean bookAttribute() {
		return bookAttribute;
	}

	public void setMandatory(boolean bookAttribute) {
		this.bookAttribute = bookAttribute;
	}

	public boolean getBookAttribute() {
		return bookAttribute;
	}

	public boolean getReadState() {
		return readState;
	}

	public void setReadState(boolean readState) {
		this.readState = readState;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Long getStrudentId() {
		return bookshelfId;
	}

	public void setStrudentId(Long strudentId) {
		this.bookshelfId = strudentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public static Builder getBuilder(Long bookId,String bookName,Long teacherId,String teacherName
				,Long studentId,String studentName,boolean bookAttribut,boolean readState) {		
		return new Builder(bookId, bookName, teacherId, teacherName, studentId, studentName, bookAttribut, readState);	
	}
	
	public static  class Builder {
		private RecommendDTO built;
		
		public RecommendDTO build(){
			return built;
		}
		
		public Builder(Long bookId,String bookName,Long teacherId,String teacherName
				,Long studentId,String studentName,boolean bookAttribut,boolean readState){
			built = new RecommendDTO();
			built.bookId = bookId;
			built.bookName = bookName;
			built.teacherId = teacherId;
			built.teacherName = teacherName;
			built.studentName = studentName;
			built.bookshelfId = studentId;
			built.setMandatory(bookAttribut);
			built.setReadState(readState);
		}
		
	}

}
