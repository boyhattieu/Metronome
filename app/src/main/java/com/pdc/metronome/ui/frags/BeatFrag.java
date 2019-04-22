package com.pdc.metronome.ui.frags;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.pdc.metronome.R;
import com.pdc.metronome.constant.Constant;
import com.pdc.metronome.constant.Key;
import com.pdc.metronome.layout.ItemBeat;

import java.util.ArrayList;
import java.util.List;

public class BeatFrag extends Fragment {

    private static final String TAG = "BeatFrag";

    private ImageView imgBackground;
    private List<ItemBeat> itemBeats;
    private LinearLayout lnBeat;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_beat, container, false);
        lnBeat = rootView.findViewById(R.id.ln_beat);

        initData();
        initView();
        return rootView;
    }

    private void initView() {
        imgBackground = rootView.findViewById(R.id.img_bg);

        Glide.with(getContext()).load(R.drawable.bg_1).into(imgBackground);
    }

    private void initData() {
        int height = (HeightScreen() - dpToPx(144)) / 11;
        itemBeats = new ArrayList<>();
        itemBeats.add(new ItemBeat(getContext(),"1/4", R.drawable.img_checked,0, height));
        itemBeats.add(new ItemBeat(getContext(),"2/4", R.drawable.img_checked,1, height));
        itemBeats.add(new ItemBeat(getContext(),"3/4", R.drawable.img_checked,2, height));
        itemBeats.add(new ItemBeat(getContext(),"4/4", R.drawable.img_checked,3, height));
        itemBeats.add(new ItemBeat(getContext(),"5/4", R.drawable.img_checked,4, height));
        itemBeats.add(new ItemBeat(getContext(),"7/4", R.drawable.img_checked,5, height));
        itemBeats.add(new ItemBeat(getContext(),"5/8", R.drawable.img_checked,6, height));
        itemBeats.add(new ItemBeat(getContext(),"6/8", R.drawable.img_checked,7, height));
        itemBeats.add(new ItemBeat(getContext(),"7/8", R.drawable.img_checked,8, height));
        itemBeats.add(new ItemBeat(getContext(),"9/8", R.drawable.img_checked,9, height));
        itemBeats.add(new ItemBeat(getContext(),"12/8", R.drawable.img_checked,10, height));


        for (int i = 0; i < itemBeats.size(); i++){
            if (itemBeats.get(i).getText().equals(Hawk.get(Key.BEAT, Constant.BEAT[3]))){
                itemBeats.get(i).beatChoice(true);
            }
            final ItemBeat itemBeat = itemBeats.get(i);
            itemBeat.setOnClickItem(new ItemBeat.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    onSelectedBeat(position);
                }
            });
            lnBeat.addView(itemBeat);
        }
    }

    private void onSelectedBeat(int position) {
        for (int i = 0; i < itemBeats.size(); i++){
            itemBeats.get(i).beatChoice(false);
        }
        itemBeats.get(position).beatChoice(true);
    }

    private int HeightScreen(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return height;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
