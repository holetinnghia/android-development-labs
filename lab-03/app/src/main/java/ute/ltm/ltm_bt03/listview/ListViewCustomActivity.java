package ute.ltm.ltm_bt03.listview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

import ute.ltm.ltm_bt03.R;
import ute.ltm.ltm_bt03.adapter.MonhocAdapter;
import ute.ltm.ltm_bt03.model.MonHoc;

public class ListViewCustomActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<MonHoc> arrayList;
    MonhocAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_custom);

        AnhXa();

        adapter = new MonhocAdapter(ListViewCustomActivity.this,
                R.layout.row_monhoc,
                arrayList
        );

        listView.setAdapter(adapter);
    }

    private void AnhXa() {
        listView = (ListView) findViewById(R.id.listviewCustom);

        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java", "Java 1", R.drawable.java_logo));
        arrayList.add(new MonHoc("C#", "C# 1", R.drawable.c_sharp_logo));
        arrayList.add(new MonHoc("PHP", "PHP 1", R.drawable.php_logo));
        arrayList.add(new MonHoc("Kotlin", "Kotlin 1", R.drawable.kotlin_logo));
        arrayList.add(new MonHoc("Dart", "Dart 1", R.drawable.dart_logo));
    }
}
