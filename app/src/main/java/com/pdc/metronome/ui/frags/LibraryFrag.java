package com.pdc.metronome.ui.frags;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pdc.metronome.R;

public class LibraryFrag extends Fragment {

    private View rootView;
    private ImageView imgBackground;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_library, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        imgBackground = rootView.findViewById(R.id.img_bg);
        Glide.with(getContext()).load(R.drawable.bg_wooden_3).into(imgBackground);
    }
}
