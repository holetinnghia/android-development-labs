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

    // Khai báo các biến giao diện
    private EditText edtEmail, edtPassword;
    private CheckBox cbRemember;
    private Button btnLogin;

    // Tên file Shared Preferences và các Key lưu trữ
    private static final String SHARED_PREF_NAME = "my_login_pref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_REMEMBER = "is_remember";

    // Đối tượng SharedPreferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        // 1. Ánh xạ View
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);

        // 2. Khởi tạo SharedPreferences
        // MODE_PRIVATE: Chỉ ứng dụng này mới đọc được file này
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // 3. Load dữ liệu đã lưu (nếu có) ngay khi mở màn hình
        loadData();

        // 4. Xử lý sự kiện nút Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SharedPrefActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem người dùng có check "Remember Me" không
                if (cbRemember.isChecked()) {
                    // Nếu có check -> Lưu dữ liệu
                    saveData(email, password, true);
                    Toast.makeText(SharedPrefActivity.this, "Đăng nhập thành công & Đã lưu mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu không check -> Xóa dữ liệu đã lưu (để lần sau không hiện nữa)
                    clearData();
                    Toast.makeText(SharedPrefActivity.this, "Đăng nhập thành công (Không lưu)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm lưu dữ liệu vào Shared Preferences
    private void saveData(String email, String password, boolean isRemember) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_IS_REMEMBER, isRemember);
        editor.apply(); // Lưu xuống file xml
    }

    // Hàm đọc dữ liệu từ Shared Preferences hiển thị lên UI
    private void loadData() {
        // Lấy trạng thái checkbox, mặc định là false nếu không tìm thấy
        boolean isRemember = sharedPreferences.getBoolean(KEY_IS_REMEMBER, false);

        if (isRemember) {
            // Nếu trước đó đã chọn Remember, lấy Email và Pass điền vào
            String email = sharedPreferences.getString(KEY_EMAIL, "");
            String password = sharedPreferences.getString(KEY_PASSWORD, "");

            edtEmail.setText(email);
            edtPassword.setText(password);
            cbRemember.setChecked(true);
        }
    }

    // Hàm xóa dữ liệu (khi người dùng bỏ chọn Remember)
    private void clearData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Xóa toàn bộ dữ liệu trong file pref này
        editor.apply();
    }
}