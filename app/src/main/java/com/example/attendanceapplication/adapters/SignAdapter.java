package com.example.attendanceapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.models.Courses;
import com.example.attendanceapplication.models.User;

import java.util.ArrayList;
import java.util.List;

public class SignAdapter extends RecyclerView.Adapter<SignAdapter.MyViewHolder> {
    private Context context;
    private boolean isStudent;

    public List<User> dataList = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public SignAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Courses item);

        void onItemDelete(Courses item);

    }

    public void setList(List<User> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sign, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User dataModel = dataList.get(position);
        holder.tvName.setText(dataModel.getUserName());
        if (dataModel.getSignTime() > 0) {
            holder.tvStatus.setText("Signed in");
            holder.tvTime.setText(TimeUtils.millis2String(dataModel.getSignTime()));
        } else {
            holder.tvStatus.setText("Not signed in");
            holder.tvTime.setText("");

        }
        holder.tvSemester.setText(dataModel.getSemester());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvTime;
        TextView tvStatus;
        TextView tvSemester;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvSemester = itemView.findViewById(R.id.tv_semester);
            tvStatus = itemView.findViewById(R.id.tv_status);

        }
    }

}
