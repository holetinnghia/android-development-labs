package ute.ltm.ltm_bt02;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout bg;
    private ArrayList<Integer> backgroundDrawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bg = findViewById(R.id.constraintLayout1);

        initializeBackgrounds();

        TextView myText = findViewById(R.id.text_id);
        myText.setText("Text View day ne!");

        EditText myEdit = findViewById(R.id.edit_id);
        myEdit.setText("Edit View day ne!");

        AutoCompleteTextView myAuto = findViewById(R.id.auto_id);
        String[] defaultText = new String[]{
                "Đây là dòng chữ được hoàn thành tự động"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                defaultText
        );

        myAuto.setAdapter(adapter);
        myAuto.setText("Đây là AutoComplete");

        TextView txtSoN = findViewById(R.id.textViewSo);
        Button btnRnd = findViewById(R.id.buttonRnd);

        btnRnd.setOnClickListener(view -> {
            Random random = new Random();
            int number = random.nextInt(10);
            txtSoN.setText(String.valueOf(number));
        });

        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bg.setBackgroundResource(R.drawable.bg1);
                Toast.makeText(MainActivity.this, "Đã chuyển sang nền 1", Toast.LENGTH_SHORT).show();
            } else {
                bg.setBackgroundResource(R.drawable.bg2);
                Toast.makeText(MainActivity.this, "Đã chuyển sang nền 2", Toast.LENGTH_SHORT).show();
            }
        });

        CheckBox ck1 = findViewById(R.id.checkBox1);
        ck1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bg.setBackgroundResource(R.drawable.bg3);
            } else {
                bg.setBackgroundResource(R.drawable.bg4);
            }
        });

        RadioGroup radioGroup = findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButton1) {
                bg.setBackgroundResource(R.drawable.bg3);
            } else if (checkedId == R.id.radioButton2) {
                bg.setBackgroundResource(R.drawable.bg4);
            }
        });

        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setOnClickListener(v -> {
            int current = progressBar.getProgress();
            if (current >= progressBar.getMax()) {
                progressBar.setProgress(0);
            } else {
                progressBar.setProgress(current + 10);
            }
        });

        Button btnGoToLinearLayout = findViewById(R.id.btnGoToLinearLayout);
        btnGoToLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LinearLayoutActivity.class);
            startActivity(intent);
        });

        Button btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeBackgroundRandomly();
    }

    private void initializeBackgrounds() {
        backgroundDrawables = new ArrayList<>();
        backgroundDrawables.add(R.drawable.bg1);
        backgroundDrawables.add(R.drawable.bg2);
        backgroundDrawables.add(R.drawable.bg3);
        backgroundDrawables.add(R.drawable.bg4);
    }

    private void changeBackgroundRandomly() {
        if (backgroundDrawables != null && !backgroundDrawables.isEmpty()) {
            Random random = new Random();
            int vitri = random.nextInt(backgroundDrawables.size());
            bg.setBackgroundResource(backgroundDrawables.get(vitri));
        }
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCanceledOnTouchOutside(false);
        EditText editMSSV = dialog.findViewById(R.id.editMSSV);
        EditText editHoTen = dialog.findViewById(R.id.editHoTen);
        Button buttonCapNhat = dialog.findViewById(R.id.buttonCapNhat);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        buttonCapNhat.setOnClickListener(v -> {
            String mssv = editMSSV.getText().toString();
            String hoTen = editHoTen.getText().toString();
            Toast.makeText(MainActivity.this, "Cập nhật: " + mssv + " - " + hoTen, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        buttonHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuSetting) {
            showCustomDialog();
        } else if (itemId == R.id.menuShare) {
            Toast.makeText(this, "Bạn chọn Share", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menuLogout) {
            Toast.makeText(this, "Bạn chọn Logout", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
