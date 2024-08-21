package com.example.attendanceapplication.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

import com.blankj.utilcode.util.ToastUtils;
import com.example.attendanceapplication.App;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.adapters.CoursesAdapter;
import com.example.attendanceapplication.db.dao.AttendanceDao;
import com.example.attendanceapplication.db.dao.CoursesDao;
import com.example.attendanceapplication.db.dao.TasksDao;
import com.example.attendanceapplication.db.dao.TimetableDao;
import com.example.attendanceapplication.models.Attendance;
import com.example.attendanceapplication.models.Courses;
import com.example.attendanceapplication.models.Tasks;
import com.example.attendanceapplication.models.Timetable;
import com.example.attendanceapplication.utils.LocationUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.client.android.MNScanManager;
import com.google.zxing.client.android.other.MNScanCallback;

import java.util.List;


/**
 * MainPage for students
 */
public class StudentMainActivity extends AppCompatActivity {
    CoursesAdapter coursesAdapter;
    CoursesDao coursesDao;
    TasksDao taskDao;
    TimetableDao timetableDao;
    AttendanceDao attendanceDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        coursesDao = new CoursesDao(this);
        taskDao = new TasksDao(this);
        timetableDao = new TimetableDao(this);
        attendanceDao = new AttendanceDao(this);
        setNavigationView();
        DrawerLayout drawer = findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.btn_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MNScanManager.startScan(StudentMainActivity.this, new MNScanCallback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        switch (resultCode) {
                            case MNScanManager.RESULT_SUCCESS:
                                String resultSuccess = data.getStringExtra(MNScanManager.INTENT_KEY_RESULT_SUCCESS);
                                if (!resultSuccess.isEmpty() && isNumericWithDecimal(resultSuccess)) {
                                    ToastUtils.showLong(resultSuccess);
                                    int taskId = Integer.parseInt(resultSuccess);
                                    Tasks tasks = taskDao.queryByTaskId(taskId);//任务存在
                                    if (tasks == null) {
                                        ToastUtils.showLong("Attendance Task does not exist");
                                        return;
                                    }
                                    boolean b2 = timetableDao.existsByCourseId(tasks.getCourseId(), App.user.getUserId());
                                    if (!b2) {
                                        ToastUtils.showLong("Can not sign in without selecting this course");
                                        return;
                                    }

                                    boolean b3 = attendanceDao.existsByTaskIdAndStudentId(taskId, App.user.getUserId());
                                    if (b3) {
                                        ToastUtils.showLong("Already signed in");
                                        return;
                                    }
                                    long l = System.currentTimeMillis();
                                    if (l < tasks.getLimitTime()) {
                                        ToastUtils.showLong("Not started");
                                        return;
                                    }
                                    if (l > tasks.getEndTime()) {
                                        ToastUtils.showLong("Closed");
                                        return;
                                    }
                                    String[] split = tasks.getLocation().split(",");
                                    double distance = LocationUtils.calculateDistance(App.latitude, App.longitude, Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                                    if (distance > 0.05) {
                                        ToastUtils.showLong("Not in the sign-in area");
                                        return;
                                    }
                                    Attendance attendance = new Attendance();
                                    attendance.setSignTime(System.currentTimeMillis());
                                    attendance.setTaskId(taskId);
                                    attendance.setStudentId(App.user.getUserId());
                                    boolean b1 = attendanceDao.create(attendance);
                                    if (b1) {
                                        ToastUtils.showLong("Sign in Successfully");
                                    } else {
                                        ToastUtils.showLong("Failed to sign in");
                                    }
                                }

                                break;
                            case MNScanManager.RESULT_FAIL:
                                String resultError = data.getStringExtra(MNScanManager.INTENT_KEY_RESULT_ERROR);
                                break;
                            case MNScanManager.RESULT_CANCLE:
                                ToastUtils.showLong("Cancel the QR code scanning");
                                break;
                        }
                    }
                });
            }
        });

        RecyclerView recycleView = findViewById(R.id.recycle_view);
        coursesAdapter = new CoursesAdapter(this, true, new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Courses item) {
                boolean b = timetableDao.existsByCourseId(item.getCourseId(), App.user.getUserId());
                if (b) {
                    ToastUtils.showLong("This course has been selected");
                } else {
                    showSemesterDialog(item);
                }

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemDelete(Courses item) {


            }
        });
        recycleView.setAdapter(coursesAdapter);
        loadData();
    }

    public void loadData() {
        List<Courses> courses = coursesDao.queryListAllCourses();
        coursesAdapter.setList(courses);

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

    public boolean isNumericWithDecimal(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    String semester = null;

    public void showSemesterDialog(Courses item) {
        semester = null;
        final String[] items = {"First semester", "Second semester", "Third semester"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please choose one");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                semester = items[which];
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (semester == null) {
                    ToastUtils.showLong("Please choose your semester");
                    return;
                }
                Timetable timetable = new Timetable();
                timetable.setSemester(semester);
                timetable.setCourseId(item.getCourseId());
                timetable.setStudentId(App.user.getUserId());
                boolean b = timetableDao.create(timetable);
                if (b) {
                    Toast.makeText(getBaseContext(), "This course has been selected ", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
