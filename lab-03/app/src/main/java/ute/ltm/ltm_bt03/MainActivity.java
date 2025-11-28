package ute.ltm.ltm_bt03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ute.ltm.ltm_bt03.listview.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnListView = findViewById(R.id.btnListViewBasic);
        btnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewBasicActivity.class);
                startActivity(intent);
            }
        });

        Button btnListViewCustom = findViewById(R.id.btnListViewCustomAdapter);
        btnListViewCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ute.ltm.ltm_bt03.listview.ListViewCustomActivity.class);
                startActivity(intent);
            }
        });

        Button btnGridViewBasic = findViewById(R.id.btnGridViewBasic);
        btnGridViewBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ute.ltm.ltm_bt03.gridview.GridViewBasicActivity.class);
                startActivity(intent);
            }
        });

        Button btnGridViewCustomAdapter = findViewById(R.id.btnGridViewCustomAdapter);
        btnGridViewCustomAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ute.ltm.ltm_bt03.gridview.GridViewCustomActivity.class);
                startActivity(intent);
            }
        });

        Button btnRecyclerViewBasic = findViewById(R.id.btnRecyclerViewBasic);
        btnRecyclerViewBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ute.ltm.ltm_bt03.recyclerview.RecyclerViewBasicActivity.class);
                startActivity(intent);
            }
        });

        Button btnRecyclerViewMultiType = findViewById(R.id.btnRecyclerViewMultiType);
        btnRecyclerViewMultiType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ute.ltm.ltm_bt03.recyclerview.RecyclerViewMultiTypeActivity.class);
                startActivity(intent);
            }
        });

        Button btnRecyclerViewAnimation = findViewById(R.id.btnRecyclerViewAnimation);
        btnRecyclerViewAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ute.ltm.ltm_bt03.recyclerview.RecyclerViewAnimationActivity.class);
                startActivity(intent);
            }
        });
    }
}