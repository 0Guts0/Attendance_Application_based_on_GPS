package com.example.attendanceapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceapplication.R;
import com.example.attendanceapplication.models.Courses;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {
    private Context context;
    private boolean isStudent;

    public List<Courses> dataList = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public CoursesAdapter(Context context, boolean isStudent, OnItemClickListener listener) {
        this.context = context;
        this.isStudent = isStudent;
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Courses item);

        void onItemDelete(Courses item);

    }

    public void setList(List<Courses> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_courses, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Courses dataModel = dataList.get(position);
        holder.textView.setText(dataModel.getCourseName() + "(" + dataModel.getDuration() + "分钟)");
        if (isStudent) {
            holder.tvDel.setVisibility(View.GONE);
        } else {
            holder.tvDel.setVisibility(View.VISIBLE);

        }
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemDelete(dataModel);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(dataModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView tvDel;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title);
            tvDel = itemView.findViewById(R.id.tv_del);

        }
    }

}
