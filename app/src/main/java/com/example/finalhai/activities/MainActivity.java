package com.example.finalhai.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.finalhai.R;
import com.example.finalhai.adapters.courseAdapter;
import com.example.finalhai.database.CourseDatabase;
import com.example.finalhai.databinding.ActivityMainBinding;
import com.example.finalhai.model.Course;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<Course> courseList = new ArrayList<>();
    CourseDatabase cdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        readCourseData();
        courseList = readCourseData();

        cdb = Room.databaseBuilder(getApplicationContext(), CourseDatabase.class,"course.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cdb.courseDao().insertCoursesFromList(courseList);
                List<Course> CoursesDB = cdb.courseDao().GetAllCourses();
                Log.d("QUANTEST",courseList.size() + " added to db");
            }
        });

        binding.recyclerViewCourse.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewCourse.setAdapter(new courseAdapter(courseList, new courseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                //which one is click => get ID:
                int courseId = courseList.get(i).getCourseId();
                courseList.get(i).setSessionNum(courseList.get(i).getSessionNum() + 1);
                binding.recyclerViewCourse.getAdapter().notifyDataSetChanged();

                ExecutorService executorService1 = Executors.newSingleThreadExecutor();
                executorService1.execute((new Runnable() {
                    @Override
                    public void run() {
                        cdb.courseDao().IncreaseSessionNumberForSelectedCourse(courseId);

                    }
                }));
            }
        }));
        binding.btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }

    private List<Course> readCourseData(){
        List<Course> courseList1 = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.courses);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String courseLine;
        try{
            while((courseLine = reader.readLine()) != null){
                String[] eachCourseFields = courseLine.split(",");

                int courseId = Integer.parseInt(eachCourseFields[0]);
                String courseDateStr = eachCourseFields[1];
                String courseName = eachCourseFields[2];
                String courseDrawableStr = eachCourseFields[3];
                int courseDrawable = getResources().getIdentifier(courseDrawableStr,"drawable",getPackageName());
                double coursePrice = Double.parseDouble(eachCourseFields[4]);
                int courseDiscount = Integer.parseInt(eachCourseFields[5]);
                int sessionNum = 0;

                Course eachCourse = new Course(courseId,courseDateStr,courseName,courseDrawable,coursePrice,courseDiscount,sessionNum);
                courseList1.add(eachCourse);
            }
        } catch (IOException error){
            throw new RuntimeException(error);
        }
        return courseList1;
    }
}