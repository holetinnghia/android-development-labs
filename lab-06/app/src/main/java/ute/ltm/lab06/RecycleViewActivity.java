package ute.ltm.lab06;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {
    RecyclerView rcIcon;
    IconAdapter iconAdapter;
    List<IconModel> arrayList1;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        rcIcon = findViewById(R.id.rcIcon);
        searchView = findViewById(R.id.searchView);

        arrayList1 = new ArrayList<>();
        arrayList1.add(new IconModel(R.mipmap.ic_launcher, "Giày dép"));
        arrayList1.add(new IconModel(R.mipmap.ic_launcher, "Quần áo"));
        arrayList1.add(new IconModel(R.mipmap.ic_launcher, "Điện thoại"));
        arrayList1.add(new IconModel(R.mipmap.ic_launcher, "Laptop"));
        arrayList1.add(new IconModel(R.mipmap.ic_launcher, "Mỹ phẩm"));
        arrayList1.add(new IconModel(R.mipmap.ic_launcher, "Sách"));
        arrayList1.add(new IconModel(R.mipmap.ic_launcher, "Đồ chơi"));

        iconAdapter = new IconAdapter(this, arrayList1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(gridLayoutManager);
        rcIcon.setAdapter(iconAdapter);

        rcIcon.addItemDecoration(new LinePagerIndicatorDecoration());

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return true;
            }
        });
    }

    private void filterListener(String text) {
        List<IconModel> list = new ArrayList<>();
        for (IconModel iconModel : arrayList1) {
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                list.add(iconModel);
            }
        }

        if (list.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            iconAdapter.setListenerList(list);
        }
    }
}