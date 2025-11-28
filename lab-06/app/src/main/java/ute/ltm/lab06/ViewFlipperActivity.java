package ute.ltm.lab06;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class ViewFlipperActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);

        viewFlipper = findViewById(R.id.viewFlipperMain);

        actionViewFlipper();
    }

    private void actionViewFlipper() {
        List<String> listUrl = new ArrayList<>();
        listUrl.add("http://app.iotstar.vn:8081/appfoods/flipper/quangcao.png");
        listUrl.add("http://app.iotstar.vn:8081/appfoods/flipper/coffee.jpg");
        listUrl.add("http://app.iotstar.vn:8081/appfoods/flipper/companypizza.jpeg");
        listUrl.add("http://app.iotstar.vn:8081/appfoods/flipper/themoingon.jpeg");

        for (int i = 0; i < listUrl.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());

            Glide.with(getApplicationContext())
                    .load(listUrl.get(i))
                    .into(imageView);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
}