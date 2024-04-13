package com.example.finalhai.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.example.finalhai.R;
import com.example.finalhai.adapters.courseAdapter;
import com.example.finalhai.database.CourseDatabase;
import com.example.finalhai.databinding.ActivitySecondBinding;
import com.example.finalhai.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecondActivity extends AppCompatActivity {

    CourseDatabase cdb;
    ActivitySecondBinding binding;
    StringBuilder outputText = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        binding.txtViewTitle2.setText("Hello 2nd act");
        setContentView(binding.getRoot());
        cdb = Room.databaseBuilder(getApplicationContext(),CourseDatabase.class,"course.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("QUAN1",cdb.courseDao().GetAllCourses().size() + " items");
                Log.d("QUAN2",cdb.courseDao().GetSelectedCourses().size() + " selected");
                List<Course> selectedCourseList = cdb.courseDao().GetSelectedCourses();
                int totalPrice = 0;
                for(Course course:selectedCourseList){
                    if(course.getCourseDiscount() == 1){
                        totalPrice+=course.subTotal()*0.9;
                    } else {
                        totalPrice+=course.subTotal();
                    }
                }
                binding.recyclerViewPurchasedCourse.setAdapter(new courseAdapter(selectedCourseList));
                outputText = new StringBuilder(String.format("Total Price is:  CAD %-10s\n", totalPrice));
                binding.txtViewCartTotal.setText(outputText);
            }
        });
        binding.recyclerViewPurchasedCourse.setLayoutManager(new LinearLayoutManager(this));
    }
}