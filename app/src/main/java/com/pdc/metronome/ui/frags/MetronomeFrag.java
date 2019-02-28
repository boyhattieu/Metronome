package com.pdc.metronome.ui.frags;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.pdc.metronome.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MetronomeFrag extends Fragment {

    private ImageView btnPlay;
    private ImageView btnPause;
    private ImageView btnSetting;
    private ImageView btnRotate;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_metronome,container, false);
        initBackground();
        initView();
        onListener();
        return rootView;
    }

    private void initBackground() {
        // TODO: 27/02/2019 set background using Glide
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onListener() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setImageAlpha(0);
                btnPause.setImageAlpha(1);
            }
        });
    }

    private void initView() {
        btnPlay = rootView.findViewById(R.id.btn_play);
        btnPause = rootView.findViewById(R.id.btn_pause);
        btnSetting = rootView.findViewById(R.id.btn_setting);
        btnRotate = rootView.findViewById(R.id.rotate_cd);


        Glide.with(getContext()).load(R.drawable.btn_setting).into(btnSetting);
        Glide.with(getContext()).load(R.drawable.btn_play).into(btnPlay);
        Glide.with(getContext()).load(R.drawable.btn_pause).into(btnPause);
        Glide.with(getContext()).load(R.drawable.bg_wooden_cd).into(btnRotate);
    }


}
