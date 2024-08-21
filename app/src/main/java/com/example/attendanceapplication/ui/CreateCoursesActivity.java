package com.example.attendanceapplication.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.example.attendanceapplication.App;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.adapters.CoursesAdapter;
import com.example.attendanceapplication.db.dao.CoursesDao;
import com.example.attendanceapplication.db.dao.TasksDao;
import com.example.attendanceapplication.models.Courses;
import com.example.attendanceapplication.models.Tasks;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.List;

/**
 * MainPage for teachers
 */
public class CreateCoursesActivity extends AppCompatActivity {
    CoursesAdapter coursesAdapter;
    CoursesDao coursesDao;
    TasksDao taskDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_courses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        coursesDao = new CoursesDao(this);
        taskDao = new TasksDao(this);
        setNavigationView();
        DrawerLayout drawer = findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        RecyclerView recycleView = findViewById(R.id.recycle_view);
        Button btnCreate = findViewById(R.id.btn_create);
        coursesAdapter = new CoursesAdapter(this, false, new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Courses item) {
                if (taskDao.existsByCourseId(item.getCourseId())) {
                    Intent intent = new Intent(CreateCoursesActivity.this, TaskDetailActivity.class);
                    intent.putExtra("courses", item);
                    startActivity(intent);
                } else {
                    showTimePickerDialog(item);
                }

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemDelete(Courses item) {
                boolean b = coursesDao.deleteById(item.getCourseId());
                if (b) {
                    coursesAdapter.dataList.remove(item);
                    coursesAdapter.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "Deleted successfully", Toast.LENGTH_LONG).show();
                }

            }
        });
        recycleView.setAdapter(coursesAdapter);
        loadData();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

    }


    public void loadData() {
        List<Courses> courses = coursesDao.queryListByTeacherId(App.user.getUserId());
        coursesAdapter.setList(courses);

    }

    private void showTimePickerDialog(Courses item) {
        StringBuffer stringBuffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        stringBuffer.append(year + "/" + month + "/" + dayOfMonth + " ");

                        new TimePickerDialog(CreateCoursesActivity.this
                                , new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                stringBuffer.append(hourOfDay + ":" + minute);
                                String location = App.latitude + "," + App.longitude + "," + App.address;
                                long startTime = TimeUtils.string2Millis(stringBuffer.toString(), "yyyy/MM/dd HH:mm");
                                Tasks tasks = new Tasks();
                                tasks.setCourseId(item.getCourseId());
                                tasks.setTeacherId(item.getTeacherId());
                                tasks.setLocation(location);
                                tasks.setLimitTime(startTime);
                                tasks.setEndTime((startTime + item.getDuration() * 60 * 1000));
                                boolean b = taskDao.create(tasks);
                                if (b) {
                                    Toast.makeText(getBaseContext(), "Created successfully", Toast.LENGTH_LONG).show();
                                }

                            }
                        }, hour, minute, true).show();
                    }
                },
                year, month, day).show();
    }

    public void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Courses");
        LayoutInflater inflater = LayoutInflater.from(this);
        View customLayout = inflater.inflate(R.layout.layout_input, null);
        final EditText input1 = customLayout.findViewById(R.id.ed_input1);
        final EditText input2 = customLayout.findViewById(R.id.ed_input2);
        builder.setView(customLayout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input1.getText().toString();
                String time = input2.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please enter the Course Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (time.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please enter the duration time", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean b = coursesDao.create(new Courses(name, App.user.getUserId(), Integer.parseInt(time)));
                if (b) {
                    Toast.makeText(getBaseContext(), "Added successfully", Toast.LENGTH_LONG).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


    }

    public void setNavigationView() {
        NavigationView navView = findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        TextView tv_user_id = headerView.findViewById(R.id.tv_user_id);
        TextView tv_user_name = headerView.findViewById(R.id.tv_user_name);
        TextView tv_location = headerView.findViewById(R.id.tv_location);
        TextView tv_role = headerView.findViewById(R.id.tv_role);
        TextView tv_email = headerView.findViewById(R.id.tv_email);
        TextView tv_major = headerView.findViewById(R.id.tv_major);
        tv_user_id.setText("UserId:" + App.user.getUserId().toString());
        tv_user_name.setText("UserName:" + App.user.getUserName().toString());
        tv_location.setText("Location:" + App.user.getCurrentLocation().split(",")[2]);
        tv_role.setText("Role:" + App.user.getRole().toString());
        tv_email.setText("Email:" + App.user.getEmail().toString());
        tv_major.setText("Major:" + App.user.getMajor().toString());

        headerView.findViewById(R.id.btn_login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
