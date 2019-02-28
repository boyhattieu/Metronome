package com.pdc.metronome.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.pdc.metronome.R;
import com.pdc.metronome.adapter.viewpager.PagerAdapter;
import com.pdc.metronome.layout.ItemTab;
import com.pdc.metronome.ui.frags.BeatFrag;
import com.pdc.metronome.ui.frags.LibraryFrag;
import com.pdc.metronome.ui.frags.MetronomeFrag;
import com.pdc.metronome.ui.frags.TempoFrag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragments;
    private ViewPager viewPagerMain;
    private LinearLayout lnTabMain;

    private List<ItemTab> itemTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPagerMain = findViewById(R.id.vpg_main);
        lnTabMain = findViewById(R.id.ln_tab);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initTab();
        initFrag();
        initViewPager();
        registerListener();
    }

    private void registerListener() {
        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                onSelectedTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initViewPager() {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPagerMain.setAdapter(adapter);
    }

    private void initFrag() {
        fragments = new ArrayList<>();
        fragments.add(new MetronomeFrag());
        fragments.add(new TempoFrag());
        fragments.add(new BeatFrag());
        fragments.add(new LibraryFrag());
    }

    private void initTab() {
        int width = WidthScreen()/4;
        itemTabs = new ArrayList<>();
        itemTabs.add(new ItemTab(this, R.drawable.ic_metronome, "Metronome", width, 0));
        itemTabs.add(new ItemTab(this, R.drawable.ic_tempo, "Tempo", width, 1));
        itemTabs.add(new ItemTab(this, R.drawable.ic_beat, "Beat", width, 2));
        itemTabs.add(new ItemTab(this, R.drawable.ic_library, "Library", width, 3));

        itemTabs.get(0).selected(true);
        lnTabMain.setWeightSum(itemTabs.size());
        for (int i = 0; i< itemTabs.size(); i++){
            final ItemTab itemTab = itemTabs.get(i);
            itemTab.setOnClickItem(new ItemTab.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    viewPagerMain.setCurrentItem(position);
                    onSelectedTab(position);
                }
            });
            lnTabMain.addView(itemTab);
        }
    }

    private int WidthScreen(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    private void onSelectedTab(int position){
        for (int i =0; i<itemTabs.size(); i++){
            itemTabs.get(i).selected(false);
        }
        itemTabs.get(position).selected(true);
    }
}
