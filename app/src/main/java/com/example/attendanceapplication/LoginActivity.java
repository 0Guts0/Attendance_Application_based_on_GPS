package com.example.attendanceapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceapplication.api.DatabaseHelper;
import com.example.attendanceapplication.models.Users;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        dbHelper = new DatabaseHelper(this);

        Log.d("LoginActivity", "DatabaseHelper initialized: " + (dbHelper != null));


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    new ValidateLoginTask().execute(email, password);
                }
            }
        });
    }

    private class ValidateLoginTask extends AsyncTask<String, Void, Users> {

        @Override
        protected Users doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return dbHelper.getUserByEmailAndPassword(db, email, password);
        }

        @Override
        protected void onPostExecute(Users user) {
            if (user != null) {
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("is_logged_in", true);
                editor.putString("user_role", user.getRole());
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
