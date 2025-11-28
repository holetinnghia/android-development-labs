package ute.ltm.lab06;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class HomeworkActivity extends AppCompatActivity {
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circle_indicator);

        callApiGetImages();
    }

    private void callApiGetImages() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.iotstar.vn:8081/appfoods/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        apiService.getImageSlider(1).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ImagesSlider> list = response.body().getResult();

                    SliderAdapter adapter = new SliderAdapter(HomeworkActivity.this, list);
                    viewPager.setAdapter(adapter);
                    circleIndicator.setViewPager(viewPager);
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
            }
        });
    }
}