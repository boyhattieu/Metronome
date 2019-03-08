package com.pdc.metronome.ui.frags;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pdc.metronome.R;
import com.pdc.metronome.adapter.recycleradapter.BeatRecyclerAdapter;
import com.pdc.metronome.item.BeatItems;

import java.util.ArrayList;
import java.util.List;

public class BeatFrag extends Fragment {

    private TextView txtBeat;
    private ImageView imgBackground;
    private List<BeatItems> beatItems;
    private RecyclerView rcvBeat;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_beat, container, false);
        initData();
        initView();
//        onListeners();
        return rootView;
    }

    private void onListeners() {
        txtBeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBeat.setTypeface(txtBeat.getTypeface(), Typeface.BOLD);
            }
        });
    }

    private void initView() {
        rcvBeat = rootView.findViewById(R.id.rcv_beat);
        rcvBeat.setAdapter(new BeatRecyclerAdapter(beatItems));
        rcvBeat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        txtBeat = rootView.findViewById(R.id.txt_beat);
        imgBackground = rootView.findViewById(R.id.img_bg);
        Glide.with(getContext()).load(R.drawable.bg_wooden_3).into(imgBackground);
    }

    private void initData() {
        beatItems = new ArrayList<>();
        beatItems.add(new BeatItems("1/4"));
        beatItems.add(new BeatItems("2/4"));
        beatItems.add(new BeatItems("3/4"));
        beatItems.add(new BeatItems("4/4"));
        beatItems.add(new BeatItems("5/4"));
        beatItems.add(new BeatItems("7/4"));
        beatItems.add(new BeatItems("5/8"));
        beatItems.add(new BeatItems("6/8"));
        beatItems.add(new BeatItems("7/8"));
        beatItems.add(new BeatItems("9/8"));
        beatItems.add(new BeatItems("12/8"));
    }
}
