package com.example.finalhai.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalhai.R;
import com.example.finalhai.databinding.LayoutCourseitemBinding;
import com.example.finalhai.model.Course;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.CourseViewHolder> {
    List<Course> AdapterCourseList;
    OnItemClickListener onItemClickListener;

    public courseAdapter(List<Course> adapterCourseList) {
        AdapterCourseList = adapterCourseList;
    }

    public courseAdapter(List<Course> adapterCourseList, OnItemClickListener onItemClickListener) {
        AdapterCourseList = adapterCourseList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCourseitemBinding binding = LayoutCourseitemBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        CourseViewHolder holder = new CourseViewHolder(binding.getRoot(),binding);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateLD = LocalDate.parse(AdapterCourseList.get(position).getCourseDate(),formatter1);   //Str -> LocalDate
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
        String dateStr = formatter2.format(dateLD);
        holder.binding.txtViewNameAndDate.setText(AdapterCourseList.get(position).getCourseName() + " \n" + dateStr);

        holder.binding.imgViewCoursePic.setImageResource(AdapterCourseList.get(position).getCourseDrawable());

        if(AdapterCourseList.get(position).getCourseDiscount()==1){
            holder.binding.imgViewDiscount.setImageResource(R.drawable.discount);
        } else {
            holder.binding.imgViewDiscount.setImageResource(0);
        }

        holder.binding.txtViewSessionNum.setText(Integer.toString(AdapterCourseList.get(position).getSessionNum()));

        if(AdapterCourseList.get(position).getSessionNum() > 0){
            if(AdapterCourseList.get(position).getCourseDiscount() == 1){
                holder.binding.txtViewPrice.setText(Double.toString(AdapterCourseList.get(position).subTotal()*0.9));
            } else {
                holder.binding.txtViewPrice.setText(Double.toString(AdapterCourseList.get(position).subTotal()));
            }
        } else {
            holder.binding.txtViewPrice.setText(Double.toString(AdapterCourseList.get(position).getCoursePrice()));
        }
    }

    @Override
    public int getItemCount() {
        return AdapterCourseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        LayoutCourseitemBinding binding;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public CourseViewHolder(@NonNull View itemView, LayoutCourseitemBinding binding) {
            super(itemView);
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(getAdapterPosition());
                    } 
                }
            });
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(int i);
    }

}
