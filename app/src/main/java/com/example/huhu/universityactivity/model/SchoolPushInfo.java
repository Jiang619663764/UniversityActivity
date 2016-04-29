package com.example.huhu.universityactivity.model;

import cn.bmob.v3.BmobObject;

/**
 * 大学推广信息
 * Created by Administrator on 2016/4/28.
 */
public class SchoolPushInfo extends BmobObject{

    private String schoolName;
    private String province;
    private String city;
    private String area;
    private Integer studentCount;
    private Integer newStudentCount;
    private String monsterName;
    private String monsterPhone;
    private String managerName;
    private String managerPhone;
    private String userName;
    private String progress;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getNewStudentCount() {
        return newStudentCount;
    }

    public void setNewStudentCount(Integer newStudentCount) {
        this.newStudentCount = newStudentCount;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public String getMonsterPhone() {
        return monsterPhone;
    }

    public void setMonsterPhone(String monsterPhone) {
        this.monsterPhone = monsterPhone;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
