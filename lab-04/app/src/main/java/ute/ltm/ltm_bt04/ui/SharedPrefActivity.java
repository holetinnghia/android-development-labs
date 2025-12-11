package ute.ltm.ltm_bt04.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Import R từ package gốc
import ute.ltm.ltm_bt04.R;

public class SharedPrefActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private CheckBox cbRemember;
    private Button btnLogin;

    private static final String SHARED_PREF_NAME = "my_login_pref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_REMEMBER = "is_remember";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        loadData();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SharedPrefActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cbRemember.isChecked()) {
                    saveData(email, password, true);
                    Toast.makeText(SharedPrefActivity.this, "Đăng nhập thành công & Đã lưu mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    clearData();
                    Toast.makeText(SharedPrefActivity.this, "Đăng nhập thành công (Không lưu)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData(String email, String password, boolean isRemember) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_IS_REMEMBER, isRemember);
        editor.apply();
    }

    private void loadData() {
        boolean isRemember = sharedPreferences.getBoolean(KEY_IS_REMEMBER, false);

        if (isRemember) {
            String email = sharedPreferences.getString(KEY_EMAIL, "");
            String password = sharedPreferences.getString(KEY_PASSWORD, "");

            edtEmail.setText(email);
            edtPassword.setText(password);
            cbRemember.setChecked(true);
        }
    }

    private void clearData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}