package ute.ltm.ltm_bt03.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ute.ltm.ltm_bt03.R;
import ute.ltm.ltm_bt03.adapter.MultiTypeAdapter;
import ute.ltm.ltm_bt03.model.UserModel;

public class RecyclerViewMultiTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultiTypeAdapter adapter;
    private List<Object> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view_multi_type);

        recyclerView = findViewById(R.id.rv_multipe_view_type);

        itemsList = new ArrayList<>();
        itemsList.add(new UserModel("Nguyễn Văn A", "TP. Hồ Chí Minh"));
        itemsList.add("Đây là một item dạng Text");
        itemsList.add(R.drawable.kotlin_logo);
        itemsList.add(new UserModel("Trần Thị B", "Hà Nội"));
        itemsList.add("Đây là một item khác");
        itemsList.add(R.drawable.java_logo);
        itemsList.add(new UserModel("Phạm Văn C", "Đà Nẵng"));

        adapter = new MultiTypeAdapter(this, itemsList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
