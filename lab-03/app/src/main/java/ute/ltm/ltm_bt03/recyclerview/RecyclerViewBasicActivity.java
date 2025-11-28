package ute.ltm.ltm_bt03.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ute.ltm.ltm_bt03.R;
import ute.ltm.ltm_bt03.adapter.SongAdapter;
import ute.ltm.ltm_bt03.model.SongModel;

public class RecyclerViewBasicActivity extends AppCompatActivity {

    private RecyclerView rvSongs;
    private SongAdapter mSongAdapter;
    private List<SongModel> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view_basic);

        rvSongs = findViewById(R.id.rv_songs);

        mSongs = new ArrayList<>();
        mSongs.add(new SongModel("50001", "Nơi này có anh", "Em là ai từ đâu bước đến...", "Sơn Tùng M-TP"));
        mSongs.add(new SongModel("50002", "Chúng ta của tương lai", "Liệu mai sau...", "Sơn Tùng M-TP"));
        mSongs.add(new SongModel("50003", "Bật tình yêu lên", "Bật tình yêu lên...", "Tăng Duy Tân, Hòa Minzy"));
        mSongs.add(new SongModel("50004", "Ngày mai người ta lấy chồng", "Ngày mai em lấy chồng...", "Thành Đạt"));
        mSongs.add(new SongModel("50005", "Từng quen", "Mình đã từng quen nhau...", "Wren Evans"));
        mSongs.add(new SongModel("50006", "See tình", "Gia Tìna...","Hoàng Thùy Linh"));
        mSongs.add(new SongModel("50007", "Ghé qua", "Ghé qua thăm em...", "Dick, Tofu"));
        mSongs.add(new SongModel("50008", "Cắt đôi nỗi sầu", "Cắt đôi nỗi sầu...", "Tăng Duy Tân"));
        mSongs.add(new SongModel("50009", "À lôi", "Tại vì thích em nhiều quá...", "Double2T"));
        mSongs.add(new SongModel("50010", "Lệ lưu ly", "Lệ lưu ly...", "Vũ Phụng Tiên, DT"));
        mSongs.add(new SongModel("50011", "Waiting for you", "Waiting for you...", "MONO"));
        mSongs.add(new SongModel("50012", "Không thể say", "Không thể say...", "HIEUTHUHAI"));
        mSongs.add(new SongModel("50013", "Em đồng ý (I do)", "Em đồng ý, I do...", "Đức Phúc, 911"));
        mSongs.add(new SongModel("50014", "Chuyện đôi ta", "Chuyện đôi ta...", "Emcee L (Da LAB)"));
        mSongs.add(new SongModel("50015", "Gió", "Gió...", "Jank"));
        mSongs.add(new SongModel("50016", "Luôn yêu đời", "Luôn yêu đời...", "Đen Vâu"));
        mSongs.add(new SongModel("50017", "Mascara", "Mascara...", "Chillies"));
        mSongs.add(new SongModel("50018", "Nếu lúc đó", "Nếu lúc đó...", "tlinh"));
        mSongs.add(new SongModel("50019", "Bên trên tầng lầu", "Bên trên tầng lầu...", "Tăng Duy Tân"));
        mSongs.add(new SongModel("50020", "vaicaunoicokhiennguoithaydoi", "vaicaunoicokhiennguoithaydoi", "GREY D, tlinh"));
        mSongs.add(new SongModel("50021", "Anh đã ổn hơn", "Anh đã ổn hơn...", "Rhyder"));
        mSongs.add(new SongModel("50022", "Để tôi ôm em bằng giai điệu này", "Để tôi ôm em bằng giai điệu này...", "Kai Đinh, MIN"));
        mSongs.add(new SongModel("50023", "Rồi ta sẽ ngắm pháo hoa cùng nhau", "Rồi ta sẽ ngắm pháo hoa cùng nhau...", "O.lew"));

        mSongAdapter = new SongAdapter(this, mSongs);
        rvSongs.setAdapter(mSongAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvSongs.setLayoutManager(linearLayoutManager);
    }
}
