package ute.ltm.ltm_bt04.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ute.ltm.ltm_bt04.R;
import ute.ltm.ltm_bt04.adapter.UserAdapter;
import ute.ltm.ltm_bt04.model.User;
// Class Binding tự sinh cho Activity
import ute.ltm.ltm_bt04.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {

    private ActivityDataBindingBinding binding;
    private User userDemo;
    private UserAdapter adapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập binding thay cho setContentView thông thường
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);

        // Đặt Toolbar tùy chỉnh làm ActionBar
        setSupportActionBar(binding.toolbar);

        // --- DEMO 1: Cập nhật dữ liệu đơn ---
        userDemo = new User("Nguyen", "Van A");
        binding.setCurrentUser(userDemo); // Gán biến user vào layout

        binding.btnChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDemo.setFirstName("Tran");
                userDemo.setLastName("Thi B");
                Toast.makeText(DataBindingActivity.this, "Dữ liệu đã thay đổi!", Toast.LENGTH_SHORT).show();
            }
        });

        // --- DEMO 2: RecyclerView ---
        userList = new ArrayList<>();
        userList.add(new User("Le", "Van C"));
        userList.add(new User("Pham", "Van D"));
        userList.add(new User("Hoang", "Thi E"));
        userList.add(new User("Vo", "Van F"));

        adapter = new UserAdapter(userList, new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Toast.makeText(DataBindingActivity.this,
                        "Chọn: " + user.getFirstName() + " " + user.getLastName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        binding.rcvUsers.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvUsers.setAdapter(adapter);
    }
}
