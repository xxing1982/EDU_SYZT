package com.syzton.sunread.model.exam;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.exam.CapacityQuestion.CapacityQuestionType;
@Entity
public class CapacityExamHistory extends AbstractEntity {
	
	private int firstPass;
	
	private int firstFail;
	
	private int secondPass;
	
	private int secondFail;
	
	private int thirdPass;
	
	private int thirdFail;
	
	private int fourthPass;
	
	private int fourthFail;
	
	private int fifthPass;
	
	private int fifthFail;
	
	private int sixthPass;
	
	private int sixthFail;
	
	private int seventhPass;
	
	private int seventhFail;
	
	private int eightthPass;
	
	private int eightthFail;
	
	private Long studentId;
	
	private Long examId;
	
	private boolean isPass;

	public int getEightthPass() {
		return eightthPass;
	}

	public void setEightthPass(int eightthPass) {
		this.eightthPass = eightthPass;
	}

	public int getEightthFail() {
		return eightthFail;
	}

	public void setEightthFail(int eightthFail) {
		this.eightthFail = eightthFail;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public int getFirstPass() {
		return firstPass;
	}

	public void setFirstPass(int firstPass) {
		this.firstPass = firstPass;
	}

	public int getFirstFail() {
		return firstFail;
	}

	public void setFirstFail(int firstFail) {
		this.firstFail = firstFail;
	}

	public int getSecondPass() {
		return secondPass;
	}

	public void setSecondPass(int secondPass) {
		this.secondPass = secondPass;
	}

	public int getSecondFail() {
		return secondFail;
	}

	public void setSecondFail(int secondFail) {
		this.secondFail = secondFail;
	}

	public int getThirdPass() {
		return thirdPass;
	}

	public void setThirdPass(int thirdPass) {
		this.thirdPass = thirdPass;
	}

	public int getThirdFail() {
		return thirdFail;
	}

	public void setThirdFail(int thirdFail) {
		this.thirdFail = thirdFail;
	}

	public int getFourthPass() {
		return fourthPass;
	}

	public void setFourthPass(int fourthPass) {
		this.fourthPass = fourthPass;
	}

	public int getFourthFail() {
		return fourthFail;
	}

	public void setFourthFail(int fourthFail) {
		this.fourthFail = fourthFail;
	}

	public int getFifthPass() {
		return fifthPass;
	}

	public void setFifthPass(int fifthPass) {
		this.fifthPass = fifthPass;
	}

	public int getFifthFail() {
		return fifthFail;
	}

	public void setFifthFail(int fifthFail) {
		this.fifthFail = fifthFail;
	}

	public int getSixthPass() {
		return sixthPass;
	}

	public void setSixthPass(int sixthPass) {
		this.sixthPass = sixthPass;
	}

	public int getSixthFail() {
		return sixthFail;
	}

	public void setSixthFail(int sixthFail) {
		this.sixthFail = sixthFail;
	}

	public int getSeventhPass() {
		return seventhPass;
	}

	public void setSeventhPass(int seventhPass) {
		this.seventhPass = seventhPass;
	}

	public int getSeventhFail() {
		return seventhFail;
	}

	public void setSeventhFail(int seventhFail) {
		this.seventhFail = seventhFail;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	public Map<CapacityQuestionType,Integer> getEveryTypePassRate(){
		Map<CapacityQuestionType,Integer> map = new HashMap<CapacityQuestionType,Integer>();
		for(CapacityQuestionType type:CapacityQuestionType.values()){
			map.put(type, getPassRateByType(type));
		}
		return map;
	}
	
	public Map<CapacityQuestionType,Integer> getEveryTypePassCount(){
		Map<CapacityQuestionType,Integer> map = new HashMap<CapacityQuestionType,Integer>();
		for(CapacityQuestionType type:CapacityQuestionType.values()){
			map.put(type, getPassRateByType(type));
		}
		return map;
	}
	
	public Map<CapacityQuestionType,Integer> getEveryTypeTotalCount(){
		Map<CapacityQuestionType,Integer> map = new HashMap<CapacityQuestionType,Integer>();
		for(CapacityQuestionType type:CapacityQuestionType.values()){
			map.put(type, getPassRateByType(type));
		}
		return map;
	}
	
	public int getPassRateByType(CapacityQuestionType type){
		int count = 0;
		if(type.equals(CapacityQuestionType.FIRST)){
			count = firstFail+firstPass;
			if(count>0){
				return firstPass*100/count;
			}
			return 0;
		}else if(type.equals(CapacityQuestionType.SECOND)){
			count = secondFail+secondPass;
			if(count>0){
				return secondPass*100/count;
			}
			return 0;
		}else if(type.equals(CapacityQuestionType.THIRD)){
			count = thirdFail+thirdPass;
			if(count>0){
				return thirdPass*100/count;
			}
			return 0;
		}else if(type.equals(CapacityQuestionType.FOURTH)){
			count = fourthFail+fourthPass;
			if(count>0){
				return fourthPass*100/count;
			}
			return 0;
		}else if(type.equals(CapacityQuestionType.FIFTH)){
			count = fifthFail+fifthPass;
			if(count>0){
				return fifthPass*100/count;
			}
			return 0;
		}else if(type.equals(CapacityQuestionType.SIXTH)){
			count = sixthFail+sixthPass;
			if(count>0){
				return sixthPass*100/count;
			}
			return 0;
		}else if(type.equals(CapacityQuestionType.SEVENTH)){
			count = seventhFail+seventhPass;
			if(count>0){
				return seventhPass*100/count;
			}
			return 0;
		}else if(type.equals(CapacityQuestionType.EIGHTTH)){
			count = eightthFail+eightthPass;
			if(count>0){
				return eightthPass*100/count;
			}
			return 0;
		}else if(type.equals(CapacityQuestionType.SECOND)){
			count = secondFail+secondPass;
			if(count>0){
				return secondPass*100/count;
			}
			return 0;
		}else{
			return 0;
		}
	}
	
	public int getPassCountByType(CapacityQuestionType type){
		if(type.equals(CapacityQuestionType.FIRST)){
			return firstPass;
		}else if(type.equals(CapacityQuestionType.SECOND)){
			return secondPass;
		}else if(type.equals(CapacityQuestionType.THIRD)){
			return thirdPass;
		}else if(type.equals(CapacityQuestionType.FOURTH)){
			return fourthPass;
		}else if(type.equals(CapacityQuestionType.FIFTH)){
			return fifthPass;
		}else if(type.equals(CapacityQuestionType.SIXTH)){
			return sixthPass;
		}else if(type.equals(CapacityQuestionType.SEVENTH)){
			return seventhPass;
		}else if(type.equals(CapacityQuestionType.EIGHTTH)){
			return eightthPass;
		}else{
			return 0;
		}
	}
	
	public int getCountByType(CapacityQuestionType type){
		if(type.equals(CapacityQuestionType.FIRST)){
			return firstPass+firstFail;
		}else if(type.equals(CapacityQuestionType.SECOND)){
			return secondPass+secondFail;
		}else if(type.equals(CapacityQuestionType.THIRD)){
			return thirdPass+thirdFail;
		}else if(type.equals(CapacityQuestionType.FOURTH)){
			return fourthPass+fourthFail;
		}else if(type.equals(CapacityQuestionType.FIFTH)){
			return fifthPass+fifthFail;
		}else if(type.equals(CapacityQuestionType.SIXTH)){
			return sixthPass+sixthFail;
		}else if(type.equals(CapacityQuestionType.SEVENTH)){
			return seventhPass+seventhFail;
		}else if(type.equals(CapacityQuestionType.EIGHTTH)){
			return eightthPass+eightthFail;
		}else{
			return 0;
		}
	}
}
