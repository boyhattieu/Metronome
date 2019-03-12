package com.pdc.metronome.ui.frags;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.pdc.metronome.R;

public class MetronomeFrag extends Fragment {

    private static final String TAG = "MetronomeFrag";

    private ImageView btnPlay, btnSetting, btnRotate, imgBackground, imgBpmBar, btnPlus, btnMinus;
    private TextView txtBpm;
    private boolean isStart = true;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_metronome, container, false);
        initView();
        onListener();
        return rootView;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onListener() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = !isStart;
                ViewAnimator.animate(btnPlay).scale(1f, 0.9f, 1f).duration(100).start();
                if (isStart) {
                    Glide.with(getContext()).load(R.drawable.btn_wooden_cd_play).into(btnPlay);
                } else {
                    Glide.with(getContext()).load(R.drawable.btn_wooden_cd_pause).into(btnPlay);
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBpm.setText(String.valueOf(Integer.parseInt(txtBpm.getText().toString()) + 1));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBpm.setText(String.valueOf(Integer.parseInt(txtBpm.getText().toString()) - 1));
            }
        });


    }

    private void initView() {
        btnPlay = rootView.findViewById(R.id.btn_play);
        btnSetting = rootView.findViewById(R.id.btn_setting);
        btnRotate = rootView.findViewById(R.id.rotate_cd);
        imgBackground = rootView.findViewById(R.id.img_bg);
        imgBpmBar = rootView.findViewById(R.id.img_bpm_bar);
        btnPlus = rootView.findViewById(R.id.btn_plus);
        btnMinus = rootView.findViewById(R.id.btn_minus);
        txtBpm = rootView.findViewById(R.id.txt_bpm);

        txtBpm.setText("128");

        Glide.with(getContext()).load(R.drawable.btn_setting).into(btnSetting);
        Glide.with(getContext()).load(R.drawable.btn_wooden_cd_play).into(btnPlay);
        Glide.with(getContext()).load(R.drawable.bg_wooden_cd).into(btnRotate);
        Glide.with(getContext()).load(R.drawable.bg_wooden_3).into(imgBackground);
        Glide.with(getContext()).load(R.drawable.img_wooden_bar_2).into(imgBpmBar);
        Glide.with(getContext()).load(R.drawable.btn_plus).into(btnPlus);
        Glide.with(getContext()).load(R.drawable.btn_minus).into(btnMinus);
    }


}
