package com.pdc.metronome.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.pdc.metronome.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgBg;
    private ImageView imgDiscShadow;
    private ImageView imgDisc;
    private ImageView imgGuitarShadow;
    private ImageView imgGuitar;
    private ImageView imgCompanyName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initImage();
        animation();
        changeActivity();
    }

    private void initImage() {
        Glide.with(SplashActivity.this).load(R.drawable.img_ls_bg).into(imgBg);
        Glide.with(SplashActivity.this).load(R.drawable.img_guitar).into(imgGuitar);
        Glide.with(SplashActivity.this).load(R.drawable.img_company_name).into(imgCompanyName);
        Glide.with(SplashActivity.this).load(R.drawable.img_guitar_shadow).into(imgGuitarShadow);
        Glide.with(SplashActivity.this).load(R.drawable.img_disc_shadow).into(imgDiscShadow);
        Glide.with(SplashActivity.this).load(R.drawable.img_cd).into(imgDisc);
    }

    private void changeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 5000);
    }

    private void animation() {

        ViewAnimator.animate(imgDisc).rotation(1080).duration(1000).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGuitar.setVisibility(View.VISIBLE);
                ViewAnimator.animate(imgGuitar).slideTopIn().duration(500).start();
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                visibleView();
                ViewAnimator.animate(imgGuitarShadow).fadeIn().duration(500).start();
                ViewAnimator.animate(imgDiscShadow).fadeIn().duration(500).start();
                ViewAnimator.animate(imgCompanyName).fadeIn().duration(500).start();
            }
        }, 2500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                invisibleView();
            }
        }, 4500);
    }

    private void invisibleView() {
        ViewAnimator.animate(imgGuitar).fadeOut().duration(501).start();
        ViewAnimator.animate(imgGuitarShadow).fadeOut().duration(501).start();
        ViewAnimator.animate(imgDisc).fadeOut().duration(501).start();
        ViewAnimator.animate(imgDiscShadow).fadeOut().duration(501).start();
        ViewAnimator.animate(imgCompanyName).fadeOut().duration(501).start();
        ViewAnimator.animate(imgBg).fadeOut().duration(501).start();
    }

    private void visibleView() {
        imgGuitarShadow.setVisibility(View.VISIBLE);
        imgDiscShadow.setVisibility(View.VISIBLE);
        imgCompanyName.setVisibility(View.VISIBLE);
    }

    private void initView() {
        imgBg = findViewById(R.id.img_bg);
        imgDiscShadow = findViewById(R.id.img_disc_shadow);
        imgDisc = findViewById(R.id.img_disc);
        imgGuitarShadow = findViewById(R.id.img_guitar_shadow);
        imgGuitar = findViewById(R.id.img_guitar);
        imgCompanyName = findViewById(R.id.img_company_name);
    }
}
