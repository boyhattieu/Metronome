package com.pdc.metronome.ui.frags;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private static final String TAG = "BeatFrag";

    private TextView txtBeat;
    private ImageView imgBackground, imgChecked;
    private List<BeatItems> beatItems;
    private RecyclerView rcvBeat;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_beat, container, false);
        initData();
        initView();
        return rootView;
    }

    private void initView() {
        BeatRecyclerAdapter beatRecyclerAdapter = new BeatRecyclerAdapter(beatItems);
        beatRecyclerAdapter.setOnClickItem(new BeatRecyclerAdapter.IOnClickItem() {
            @Override
            public void onClickInterface(int position) {
                for (int i = 0; i < beatItems.size(); i++) {
                    beatItems.set(i, new BeatItems(beatItems.get(i).getTxtBeat(), 0));
                    Log.d(TAG, "onClickInterfaceFor: " + i + " : " + beatItems.get(i).getTxtBeat());
                }
                beatItems.get(position).setImgChecked(R.drawable.img_checked);
                Log.d(TAG, "onClickInterface: " + position + " : " + beatItems.get(position).getImgChecked());
            }
        });

        rcvBeat = rootView.findViewById(R.id.rcv_beat);
        rcvBeat.setAdapter(beatRecyclerAdapter);
        rcvBeat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        txtBeat = rootView.findViewById(R.id.txt_beat);
        imgBackground = rootView.findViewById(R.id.img_bg);
        imgChecked = rootView.findViewById(R.id.img_checked);
//        imgChecked.setColorFilter(Color.parseColor("#c3c3c3"));

        Glide.with(getContext()).load(R.drawable.bg_wooden_3).into(imgBackground);
    }

    private void initData() {
        beatItems = new ArrayList<>();
        beatItems.add(new BeatItems("1/4", 0));
        beatItems.add(new BeatItems("2/4", 0));
        beatItems.add(new BeatItems("3/4", 0));
        beatItems.add(new BeatItems("4/4", 0));
        beatItems.add(new BeatItems("5/4", 0));
        beatItems.add(new BeatItems("7/4", 0));
        beatItems.add(new BeatItems("5/8", 0));
        beatItems.add(new BeatItems("6/8", 0));
        beatItems.add(new BeatItems("7/8", 0));
        beatItems.add(new BeatItems("9/8", 0));
        beatItems.add(new BeatItems("12/8", 0));
    }
}
