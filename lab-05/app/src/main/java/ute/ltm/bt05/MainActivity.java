package ute.ltm.bt05;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcCategory;
    APIService apiService;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rcCategory = findViewById(R.id.rc_category);
        rcCategory.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getClient().create(APIService.class);

        getCategoryData();
    }

    private void getCategoryData() {
        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categories = response.body();
                    categoryAdapter = new CategoryAdapter(MainActivity.this, categories);
                    rcCategory.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi lấy dữ liệu" + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}