package com.example.finalhai.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="courseId")
    private int courseId;
    @ColumnInfo(name="courseDate")
    private String courseDate;
    @ColumnInfo(name="courseName")
    private String courseName;
    @ColumnInfo(name="courseDrawable")
    private int courseDrawable;
    @ColumnInfo(name="coursePrice")
    private double coursePrice;
    @ColumnInfo(name="courseDiscount")
    private int courseDiscount;
    @ColumnInfo(name="sessionNum")
    private int sessionNum;

    public double subTotal(){
        double price = coursePrice * sessionNum;
        return price;
    }

    public Course() {
    }

    public Course(@NonNull int courseId, String courseDate, String courseName, int courseDrawable, double coursePrice, int courseDiscount, int sessionNum) {
        this.courseId = courseId;
        this.courseDate = courseDate;
        this.courseName = courseName;
        this.courseDrawable = courseDrawable;
        this.coursePrice = coursePrice;
        this.courseDiscount = courseDiscount;
        this.sessionNum = sessionNum;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseDrawable() {
        return courseDrawable;
    }

    public void setCourseDrawable(int courseDrawable) {
        this.courseDrawable = courseDrawable;
    }

    public double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public int getCourseDiscount() {
        return courseDiscount;
    }

    public void setCourseDiscount(int courseDiscount) {
        this.courseDiscount = courseDiscount;
    }

    public int getSessionNum() {
        return sessionNum;
    }

    public void setSessionNum(int sessionNum) {
        this.sessionNum = sessionNum;
    }
}
