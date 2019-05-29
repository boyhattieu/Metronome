package com.pdc.metronome.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.pdc.metronome.R;
import com.pdc.metronome.adapter.viewpager.NonSwipeViewPager;
import com.pdc.metronome.adapter.viewpager.PagerAdapter;
import com.pdc.metronome.constant.Key;
import com.pdc.metronome.layout.ItemTab;
import com.pdc.metronome.ui.frags.BeatFrag;
import com.pdc.metronome.ui.frags.MetronomeFrag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragments;
    private NonSwipeViewPager viewPagerMain;
    private LinearLayout lnTabMain;
    private ImageView imgAppLogo;
    private ImageView imgCompanyLogo;
    private ImageView topBackground;

    private List<ItemTab> itemTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPagerMain = findViewById(R.id.vpg_main);
        lnTabMain = findViewById(R.id.ln_tab);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (!isGranted(Manifest.permission.RECORD_AUDIO)) {
            permission(Manifest.permission.RECORD_AUDIO);
        } else {
            loadApp();
        }
    }

    private void loadApp() {
        initView();
        setImage();
        initTab();
        initFrag();
        initViewPager();
        registerListener();
    }

    private void setImage() {
        Glide.with(this).load(R.drawable.bg_black_bamboo).into(topBackground);
        Glide.with(this).load(R.drawable.txt_metronome_2).into(imgAppLogo);
        Glide.with(this).load(R.drawable.logo_panda_company).into(imgCompanyLogo);
    }

    private void initView() {
        imgAppLogo = findViewById(R.id.img_app_logo);
        imgCompanyLogo = findViewById(R.id.img_company_logo);
        topBackground = findViewById(R.id.top_bg);
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
        fragments.add(new BeatFrag());
    }

    private void initTab() {
        int width = WidthScreen() / 2;
        itemTabs = new ArrayList<>();
        itemTabs.add(new ItemTab(this, R.drawable.ic_metronome, "Metronome", width, 0));
        itemTabs.add(new ItemTab(this, R.drawable.ic_beat, "Frequency", width, 1));

        itemTabs.get(0).selected(true);
        lnTabMain.setWeightSum(itemTabs.size());
        for (int i = 0; i < itemTabs.size(); i++) {
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

    private int WidthScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    private void onSelectedTab(int position) {
        for (int i = 0; i < itemTabs.size(); i++) {
            itemTabs.get(i).selected(false);
        }
        itemTabs.get(position).selected(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Key.REQUEST_CODE_PERMISSION:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED && permissions[i].equals(Manifest.permission.RECORD_AUDIO)) {
                        loadApp();
                    } else {
                        this.finish();
                    }
                }
                break;

            default:
                break;
        }
    }

    private void permission(String... strings) {
        ActivityCompat.requestPermissions(
                this,
                strings,
                Key.REQUEST_CODE_PERMISSION
        );
    }

    private boolean isGranted(String permission) {
        if (isMarsMallow()) {
            return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private boolean isMarsMallow() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }
}
