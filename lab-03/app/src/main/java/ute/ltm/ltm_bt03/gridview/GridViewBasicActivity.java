package ute.ltm.ltm_bt03.gridview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import ute.ltm.ltm_bt03.R;

public class GridViewBasicActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    EditText editTextMonHoc;
    Button btnThem, btnCapNhat;
    int vitri = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_grid_view_basic);

        gridView = (GridView) findViewById(R.id.gridview1);
        editTextMonHoc = (EditText) findViewById(R.id.editTextMonHoc);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);

        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");
        arrayList.add("Python");
        arrayList.add("Swift");
        arrayList.add("Go");

        adapter = new ArrayAdapter<>(
                GridViewBasicActivity.this,
                android.R.layout.simple_list_item_1,
                arrayList
        );

        gridView.setAdapter(adapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextMonHoc.getText().toString();
                arrayList.add(name);
                adapter.notifyDataSetChanged();
                editTextMonHoc.setText("");
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editTextMonHoc.setText(arrayList.get(i));
                vitri = i;
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitri != -1) {
                    arrayList.set(vitri, editTextMonHoc.getText().toString());
                    adapter.notifyDataSetChanged();
                    editTextMonHoc.setText("");
                    vitri = -1;
                } else {
                    Toast.makeText(GridViewBasicActivity.this, "Bạn chưa chọn môn học", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String monHoc = arrayList.get(i);

                arrayList.remove(i);
                adapter.notifyDataSetChanged();

                Toast.makeText(GridViewBasicActivity.this, "Đã xóa: " + monHoc, Toast.LENGTH_SHORT).show();

                editTextMonHoc.setText("");
                vitri = -1;

                return true;
            }
        });
    }
}
