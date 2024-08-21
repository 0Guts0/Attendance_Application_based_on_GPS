package com.example.attendanceapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.attendanceapplication.App;
import com.example.attendanceapplication.R;
import com.example.attendanceapplication.db.dao.UserDao;
import com.example.attendanceapplication.models.User;

public class RegisterActivity extends AppCompatActivity {
    private String role = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
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

        UserDao userDao = new UserDao(this);
        EditText edUserName = findViewById(R.id.ed_user_name);
        EditText edUserEmail = findViewById(R.id.ed_email);
        EditText edMajor = findViewById(R.id.ed_major);
        EditText edPwd = findViewById(R.id.ed_pwd);
        Button btnRegister = findViewById(R.id.btn_register);
        RadioGroup rgRole = findViewById(R.id.rg_role);
        rgRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_teacher) {
                    role = "teacher";
                } else {
                    role = "student";

                }

            }
        });
        rgRole.check(R.id.rb_student);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edUserName.getText().toString();
                String userEmail = edUserEmail.getText().toString();
                String major = edMajor.getText().toString();
                String pwd = edPwd.getText().toString();

                if (userName.isEmpty() || userEmail.isEmpty() || major.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please complete the information", Toast.LENGTH_LONG).show();
                } else {
                    String location = App.latitude + "," + App.longitude + "," + App.address;
                    boolean register = userDao.register(new User(userName, location, role, userEmail, major, pwd));
                    if (register) {
                        Toast.makeText(getApplicationContext(), "Register successfully", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "This E-mail has already in use", Toast.LENGTH_LONG).show();

                    }

                }

            }
        });


    }
}
