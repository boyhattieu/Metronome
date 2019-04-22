package com.pdc.metronome.layout;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.pdc.metronome.R;
import com.pdc.metronome.constant.Key;

public class ItemBeat extends LinearLayout {

    private TextView txtBeat;
    private ImageView imgChecked;
    private int position;
    private IOnClickItem onClickItem;
    private LayoutParams paramTxt;
    private LayoutParams paramImg;
    private String text;

    public void setOnClickItem(IOnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public ItemBeat(Context context, String text, int res, int position, int height) {
        super(context);
        this.position = position;
        this.text = text;
        initView(text, res, height);
    }

    public String getText() {
        return text;
    }

    private void initView(String text, int res, int height) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        txtBeat = new TextView(getContext());
        paramTxt = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtBeat.setLayoutParams(paramTxt);
        txtBeat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);

        imgChecked = new ImageView(getContext());
        paramImg = new LayoutParams(dpToPx(28), ViewGroup.LayoutParams.WRAP_CONTENT);
        imgChecked.setLayoutParams(paramImg);
        paramImg.setMargins(dpToPx(8), 0, 0, 0);
        imgChecked.setVisibility(INVISIBLE);
        imgChecked.setAdjustViewBounds(true);

        setContainer(text, res);

        addView(txtBeat);
        addView(imgChecked);

        txtBeat.setTextColor(getResources().getColor(R.color.tempo_txt_tap));
        imgChecked.setColorFilter(getResources().getColor(R.color.tempo_txt_tap));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickInterface(position);
            }
        });
    }

    private void setContainer(String text, int res) {
        txtBeat.setText(text);
        Glide.with(getContext()).load(res).into(imgChecked);
        txtBeat.setLayoutParams(paramTxt);
        imgChecked.setLayoutParams(paramImg);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public interface IOnClickItem {
        void onClickInterface(int position);
    }

    public void beatChoice(boolean isChoosen) {
        if (isChoosen) {
            txtBeat.setTypeface(Typeface.DEFAULT_BOLD);
            txtBeat.setTextColor(getResources().getColor(R.color.color_beat_choosen));
            imgChecked.setVisibility(VISIBLE);
            imgChecked.setColorFilter(getResources().getColor(R.color.color_beat_choosen));
            Hawk.put(Key.BEAT, txtBeat.getText());
        } else {
            txtBeat.setTypeface(Typeface.DEFAULT);
            txtBeat.setTextColor(getResources().getColor(R.color.tempo_txt_tap));
            imgChecked.setVisibility(INVISIBLE);
        }
    }
}
