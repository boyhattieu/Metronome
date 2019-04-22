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
import com.github.florent37.viewanimator.ViewAnimator;
import com.pdc.metronome.R;

public class TempoFrag extends Fragment {

    private ImageView imgBackground;
    private ImageView imgTapOutside;
    private ImageView imgTapInside;
    private ImageView imgTapBG;
    private TextView txtTempo;
    private TextView txtTap;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_tempo, container, false);
        initView();
        onListeners();
        return rootView;
    }

    private void onListeners() {
        imgTapInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAnimator.animate(imgTapInside).scale(1f, 0.8f, 1f).duration(100).start();
                ViewAnimator.animate(txtTap).scale(1f, 0.8f, 1f).duration(100).start();
            }
        });
    }

    private void initView() {
        imgBackground = rootView.findViewById(R.id.img_bg);
        imgTapOutside = rootView.findViewById(R.id.img_tap_outside);
        imgTapInside = rootView.findViewById(R.id.img_tap_inside);
        imgTapBG = rootView.findViewById(R.id.img_tempo);
        txtTempo = rootView.findViewById(R.id.txt_tempo);
        txtTap = rootView.findViewById(R.id.txt_tap);

        Glide.with(getContext()).load(R.drawable.bg_1).into(imgBackground);
        Glide.with(getContext()).load(R.drawable.bg_tempo).into(imgTapBG);
        Glide.with(getContext()).load(R.drawable.black_round_tap_outside).into(imgTapOutside);
        Glide.with(getContext()).load(R.drawable.black_round_tap_inside).into(imgTapInside);

        txtTempo.setText("128");
    }
}
