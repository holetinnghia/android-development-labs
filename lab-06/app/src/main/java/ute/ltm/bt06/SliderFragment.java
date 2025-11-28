package ute.ltm.bt06;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator3;

public class SliderFragment extends Fragment {

    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Photo> mListPhoto;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = viewPager2.getCurrentItem();
            if (currentPosition == mListPhoto.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(currentPosition + 1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);

        viewPager2 = view.findViewById(R.id.viewPagerSlider);
        circleIndicator3 = view.findViewById(R.id.indicator);

        mListPhoto = getListPhoto();
        SliderAdapter adapter = new SliderAdapter(mListPhoto);
        viewPager2.setAdapter(adapter);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 3000);
            }
        });

        return view;
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.mipmap.apple));
        list.add(new Photo(R.mipmap.google));
        list.add(new Photo(R.mipmap.riot));
        return list;
    }
}