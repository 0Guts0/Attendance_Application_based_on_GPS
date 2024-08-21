package com.example.attendanceapplication.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.adapters.SignAdapter;
import com.example.attendanceapplication.db.dao.AttendanceDao;
import com.example.attendanceapplication.db.dao.TasksDao;
import com.example.attendanceapplication.db.dao.TimetableDao;
import com.example.attendanceapplication.db.dao.UserDao;
import com.example.attendanceapplication.models.Attendance;
import com.example.attendanceapplication.models.Courses;
import com.example.attendanceapplication.models.Tasks;
import com.example.attendanceapplication.models.Timetable;
import com.example.attendanceapplication.models.User;
import com.example.attendanceapplication.utils.QRCodeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskDetailActivity extends AppCompatActivity {
    UserDao userDao;

    TasksDao tasksDao;
    AttendanceDao attendanceDao;
    TimetableDao timetableDao;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recycleView = findViewById(R.id.recycle_view);
        userDao = new UserDao(this);
        tasksDao = new TasksDao(this);
        attendanceDao = new AttendanceDao(this);
        timetableDao = new TimetableDao(this);
        SignAdapter signAdapter = new SignAdapter(this, null);
        recycleView.setAdapter(signAdapter);
        Courses courses = (Courses) getIntent().getSerializableExtra("courses");
        Tasks tasks = tasksDao.queryByCourseId(courses.getCourseId());

        ((TextView) findViewById(R.id.tv_class_name)).setText(courses.getCourseName());
        ((TextView) findViewById(R.id.tv_location)).setText(tasks.getLocation());
        ((TextView) findViewById(R.id.tv_duration)).setText(courses.getDuration() + "minutes");
        ((TextView) findViewById(R.id.tv_start_time)).setText(TimeUtils.millis2String(tasks.getLimitTime()));
        ((TextView) findViewById(R.id.tv_end_time)).setText(TimeUtils.millis2String(tasks.getEndTime()));
        ImageView qtImage = findViewById(R.id.iv_qr);

        Bitmap bitmap = QRCodeGenerator.generateQRCode(tasks.getTaskId().toString(), 800, 800);
        qtImage.setImageBitmap(bitmap);
        List<Attendance> attendances = attendanceDao.queryListByTaskId(tasks.getTaskId());

        List<Timetable> timetables = timetableDao.queryByCourseId(courses.getCourseId());
        Set<Integer> userIds = timetables.stream()
                .map(Timetable::getStudentId)
                .collect(Collectors.toSet());
        List<User> usersByIds = userDao.getUsersByIds(new ArrayList<>(userIds));

        usersByIds.forEach(e -> {
            attendances.forEach(a -> {
                if (e.getUserId().equals(a.getStudentId())) {
                    e.setSignTime(a.getSignTime());
                }

            });

            timetables.forEach(t -> {
                if (e.getUserId().equals(t.getStudentId())) {
                    e.setSemester(t.getSemester());
                }

            });
        });
        signAdapter.setList(usersByIds);

    }


}
