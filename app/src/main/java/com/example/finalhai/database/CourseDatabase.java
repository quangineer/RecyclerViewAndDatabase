package com.example.finalhai.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.finalhai.interfaces.courseDao;
import com.example.finalhai.model.Course;

@Database(entities = {Course.class},version = 1,exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract courseDao courseDao();

}
