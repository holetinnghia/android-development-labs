package ute.ltm.lab06.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import java.util.List;

import ute.ltm.lab06.R;
import ute.ltm.lab06.model.ImagesSlider;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private List<ImagesSlider> imagesList;

    public SliderAdapter(Context context, List<ImagesSlider> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_slider, container, false);
        ImageView imageView = view.findViewById(R.id.imgSlider);

        Glide.with(context)
                .load(imagesList.get(position).getAvatar())
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() { return imagesList != null ? imagesList.size() : 0; }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}