package com.pdc.metronome.ui.frags;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pdc.metronome.R;

public class TempoFrag extends Fragment {

    private ImageView imgBackground, imgTap, imgRotate, imgTapBG;
    private TextView txtTap;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_tempo, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        imgBackground = rootView.findViewById(R.id.img_bg);
        imgTap = rootView.findViewById(R.id.img_tap);
        imgRotate = rootView.findViewById(R.id.rotate_cd);
        imgTapBG = rootView.findViewById(R.id.img_tap_bg);
        txtTap = rootView.findViewById(R.id.txt_tap);

        Glide.with(getContext()).load(R.drawable.bg_wooden_3).into(imgBackground);
        Glide.with(getContext()).load(R.drawable.btn_tap).into(imgTap);
        Glide.with(getContext()).load(R.drawable.bg_wooden_cd).into(imgRotate);
        Glide.with(getContext()).load(R.drawable.img_round_tap).into(imgTapBG);

        txtTap.setText("128");
    }
}
