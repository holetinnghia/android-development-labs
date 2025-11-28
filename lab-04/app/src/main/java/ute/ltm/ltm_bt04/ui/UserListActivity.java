package ute.ltm.ltm_bt04.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ute.ltm.ltm_bt04.R;
import ute.ltm.ltm_bt04.adapter.UserAdapter;
import ute.ltm.ltm_bt04.databinding.ActivityUserListBinding;
import ute.ltm.ltm_bt04.model.User;

public class UserListActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    private ActivityUserListBinding binding;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list);

        setupRecyclerView();
        loadUsers();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, this);
        binding.recyclerView.setAdapter(userAdapter);
    }

    private void loadUsers() {
        // Add some sample users
        userList.add(new User("John", "Doe"));
        userList.add(new User("Jane", "Smith"));
        userList.add(new User("Peter", "Jones"));
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(User user) {
        Toast.makeText(this, "You clicked: " + user.getFirstName(), Toast.LENGTH_SHORT).show();
    }
}
