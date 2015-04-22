package com.syzton.sunread.dto.fish;

/**
 * Created by jerry on 4/21/15.
 */
public class StudentFishDTO {
    private String studentName;
    private long studentId;
    private String studentIdentity;
    private String role;
    private long fishId;
    private String campusName;
    private long campusId;
    private String clazzName;
    private long clazzId;
    private int readNum;
    private int coin;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentIdentity() {
        return studentIdentity;
    }

    public void setStudentIdentity(String studentIdentity) {
        this.studentIdentity = studentIdentity;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getFishId() {
        return fishId;
    }

    public void setFishId(long fishId) {
        this.fishId = fishId;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public long getCampusId() {
        return campusId;
    }

    public void setCampusId(long campusId) {
        this.campusId = campusId;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public long getClazzId() {
        return clazzId;
    }

    public void setClazzId(long clazzId) {
        this.clazzId = clazzId;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
