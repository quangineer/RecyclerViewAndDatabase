package com.example.finalhai.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.finalhai.model.Course;

import java.util.List;

@Dao
public interface courseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCourses(Course...courses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertCoursesFromList(List<Course> courseList);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneCourse(Course course);
    @Query("SELECT * FROM courses")
    List<Course> GetAllCourses();
    @Query("UPDATE courses SET sessionNum = sessionNum + 1 WHERE courseId = :courseId")
    int IncreaseSessionNumberForSelectedCourse(int courseId);
    @Query("SELECT * FROM courses WHERE sessionNum > 0")
    List<Course> GetSelectedCourses();
}
