package ute.ltm.bt06;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<Photo> mListPhoto;

    public SliderAdapter(List<Photo> mListPhoto) {
        this.mListPhoto = mListPhoto;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Photo photo = mListPhoto.get(position);
        if (photo == null) return;
        Glide.with(holder.itemView.getContext()).load(photo.getResourceId()).into(holder.imgSlider);
    }

    @Override
    public int getItemCount() {
        return mListPhoto != null ? mListPhoto.size() : 0;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSlider;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSlider = itemView.findViewById(R.id.imgSlider);
        }
    }
}