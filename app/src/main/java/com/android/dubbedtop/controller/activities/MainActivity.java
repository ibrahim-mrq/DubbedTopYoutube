package com.android.dubbedtop.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.android.dubbedtop.controller.adapter.TabAdapter;
import com.android.dubbedtop.controller.fragments.PlayListFragment;
import com.android.dubbedtop.controller.fragments.VideosFragment;
import com.android.dubbedtop.helpers.ZoomOutPageTransformer;
import com.android.dubbedtop.model.TabClass;
import com.android.dubbedtop.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        ArrayList<TabClass> tabs = new ArrayList<>();
        tabs.add(new TabClass(getString(R.string.videos), VideosFragment.newInstance(0)));
        tabs.add(new TabClass(getString(R.string.play_list), PlayListFragment.newInstance()));
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        VideosFragment.newInstance(0);
                        break;
                    case 1:
//                        PlayListFragment.newInstance().recyclerView.smoothScrollToPosition(0);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        VideosFragment.newInstance(0);
                        break;
                    case 1:
//                        PlayListFragment.newInstance().recyclerView.smoothScrollToPosition(0);
                        break;
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return false;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}