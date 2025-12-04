package ute.android.lab_07;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageView imgProfileAvatar;
    Button btnOpenBluetooth; // Khai báo nút mới

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ các component
        imgProfileAvatar = findViewById(R.id.imgProfileAvatar);
        btnOpenBluetooth = findViewById(R.id.btnOpenBluetooth); // Ánh xạ nút mới

        // 1. Sự kiện mở màn hình Upload Images (MainActivity)
        imgProfileAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);

                // Flags để clear back stack
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });

        // 2. Sự kiện mở màn hình Bluetooth (BluetoothActivity)
        btnOpenBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, BluetoothActivity.class);
                startActivity(intent);
            }
        });
    }
}