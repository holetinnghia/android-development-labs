package ute.ltm.lab06.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import ute.ltm.lab06.R;
import ute.ltm.lab06.adapter.ViewPager2Adapter;

public class Lab1Activity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);
        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPager2Adapter adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                if (tab != null) {
                    tab.select();
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAction);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.google.android.material.snackbar.Snackbar.make(view, "Bạn vừa bấm vào nút thư!", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (tabLayout.getTabCount() == 0) {
            tabLayout.addTab(tabLayout.newTab().setText("New Order"));
            tabLayout.addTab(tabLayout.newTab().setText("Pickup"));
            tabLayout.addTab(tabLayout.newTab().setText("Delivery"));
        }
    }
}