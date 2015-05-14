/**
 * 
 */
package com.syzton.sunread.dto.recommend;

import javassist.bytecode.stackmap.TypeData.ClassName;

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
    
    private String campusName = "";
    
    private String className = "";
    
    private String description = "";
	
    @NotNull
    private boolean bookAttribute;
    
    /*
     * recommendState is used to mark if recommend success or not.
     * -1:is default value
     * 0: success add, this book first added to the student bookshelf
     * 1: success recommend, the book is not first added to the student for the reason of the book had been added by student himself
     * 2: update bookAttribute. the book has been recommended.
     * 3: failed,if the book is deleted, add it to the student.
     */
    private int recommendState = -1;
    
    private String recommendStateStr = ""; 
    
    private boolean readState = false;
    
    
	public int getRecommendState() {
		return recommendState;
	}

	public void setRecommendState(int recommendState) {
		this.recommendState = recommendState;
	}

	
	public String getRecommendStateStr() {
		return recommendStateStr;
	}

	public void setRecommendStateStr(String recommendStateStr) {
		this.recommendStateStr = recommendStateStr;
	}

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
	
	public void setBookAttribute(boolean bookAttribute) {
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
	

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public static Builder getBuilder(String bookName,String campusName, String className, String studentName, boolean bookAttibute, String description){
		return new Builder(bookName, campusName, className, studentName, bookAttibute, description);
	}
	
	public static Builder getBuilder(Long bookId,String bookName,Long teacherId,String teacherName
				,Long studentId,String studentName,boolean bookAttribut,boolean readState,int recommendState) {		
		return new Builder(bookId, bookName, teacherId, teacherName, studentId, studentName, bookAttribut, readState,recommendState);	
	}
	
	public static  class Builder {
		private RecommendDTO built;
		
		public RecommendDTO build(){
			return built;
		}
		
		public Builder(String bookName,String campusName, String className, String studentName, boolean bookAttribute, String description){
			built = new RecommendDTO();
			built.setBookName(bookName);
			built.setCampusName(campusName);
			built.setClassName(className);
			built.setStudentName(studentName);
			built.setMandatory(bookAttribute);
			built.setDescription(description);
		}
		
		public Builder(Long bookId,String bookName,Long teacherId,String teacherName
				,Long studentId,String studentName,boolean bookAttribute,boolean readState,int recommendState){
			built = new RecommendDTO();
			built.bookId = bookId;
			built.bookName = bookName;
			built.teacherId = teacherId;
			built.teacherName = teacherName;
			built.studentName = studentName;
			built.bookshelfId = studentId;
			built.setMandatory(bookAttribute);
			built.setReadState(readState);
			built.setRecommendState(recommendState);
			switch (recommendState) {
			case 0:
				built.setRecommendStateStr(built.getBookName() +" added successfully to the student "+built.getStudentName() );
				break;
			case 1:
				built.setRecommendStateStr(built.getBookName() +" recommended successfully to the student "+built.getStudentName() );
			case 2:
				built.setRecommendStateStr(built.getBookName() +" recommended failed because the book had been recommended to the student "+built.getStudentName()+"But changed the book attribute" );
			case 3:
				built.setRecommendStateStr(built.getBookName() +" recommended failed because the book had been recommended to the student "+built.getStudentName() );
			default:
				break;
			}
		}
		
	}

}
