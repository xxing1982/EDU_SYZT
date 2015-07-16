package com.syzton.sunread.model.exam;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.syzton.sunread.model.common.AbstractEntity;

@Entity
@Table(name = "speed_test_record")
public class SpeedTestRecord extends AbstractEntity{
	private long userId;
	private long articleId;
	private int speed;
	private int score;
	private int time;
	private long schoolId;

	public long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
