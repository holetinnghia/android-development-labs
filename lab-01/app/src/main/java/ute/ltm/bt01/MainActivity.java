package ute.ltm.bt01;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the status bar and navigation bar
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            //noinspection deprecation
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        // Views for number sorter and string reverser
        ConstraintLayout numberSorterLayout = findViewById(R.id.number_sorter_layout);
        ConstraintLayout stringReverserLayout = findViewById(R.id.string_reverser_layout);

        // Buttons for navigation
        Button btnShowStudent = findViewById(R.id.btn_show_student);
        Button btnShowNumbers = findViewById(R.id.btn_show_numbers);
        Button btnShowString = findViewById(R.id.btn_show_string);

        // --- Requirement 2: Sort odd and even numbers ---
        EditText etNumbers = findViewById(R.id.et_numbers);
        Button btnSortNumbers = findViewById(R.id.btn_sort_numbers);

        btnSortNumbers.setOnClickListener(v -> {
            String numbersInput = etNumbers.getText().toString();
            if (numbersInput.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập một chuỗi số", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<Integer> evenNumbers = new ArrayList<>();
            ArrayList<Integer> oddNumbers = new ArrayList<>();

            for (char c : numbersInput.toCharArray()) {
                if (Character.isDigit(c)) {
                    int digit = Character.getNumericValue(c);
                    if (digit % 2 == 0) {
                        evenNumbers.add(digit);
                    } else {
                        oddNumbers.add(digit);
                    }
                }
            }

            // Format the output to be a comma-separated string
            String evenResult = evenNumbers.stream().map(String::valueOf).collect(Collectors.joining(", "));
            String oddResult = oddNumbers.stream().map(String::valueOf).collect(Collectors.joining(", "));

            Log.d("EvenNumbers", "Các số chẵn: " + evenResult);
            Log.d("OddNumbers", "Các số lẻ: " + oddResult);
            Toast.makeText(MainActivity.this, "Đã lọc số. Vui lòng kiểm tra Logcat.", Toast.LENGTH_SHORT).show();
        });

        // --- Requirement 3: Reverse and uppercase a string ---
        EditText etInput = findViewById(R.id.et_input);
        Button btnReverse = findViewById(R.id.btn_reverse);
        TextView tvResult = findViewById(R.id.tv_result);

        btnReverse.setOnClickListener(v -> {
            String input = etInput.getText().toString();
            List<String> words = Arrays.asList(input.split("\\s"));
            Collections.reverse(words);
            String reversedString = String.join(" ", words).toUpperCase();

            tvResult.setText(reversedString);
            Toast.makeText(MainActivity.this, reversedString, Toast.LENGTH_SHORT).show();
        });

        // --- Navigation Logic ---
        btnShowStudent.setOnClickListener(v -> {
            numberSorterLayout.setVisibility(View.GONE);
            stringReverserLayout.setVisibility(View.GONE);
        });

        btnShowNumbers.setOnClickListener(v -> {
            numberSorterLayout.setVisibility(View.VISIBLE);
            stringReverserLayout.setVisibility(View.GONE);
        });

        btnShowString.setOnClickListener(v -> {
            numberSorterLayout.setVisibility(View.GONE);
            stringReverserLayout.setVisibility(View.VISIBLE);
        });

        // Set initial state
        btnShowStudent.performClick();
    }
}