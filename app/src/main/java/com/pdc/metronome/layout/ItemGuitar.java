package com.pdc.metronome.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
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

public class ItemGuitar extends LinearLayout {

    private ImageView imgGuitar;
    private TextView txtGuitar;
    private ImageView imgChecked;
    private int position;
    private IOnClickItem onClickItem;
    private LayoutParams paramImgGuitar;
    private LayoutParams paramTxt;
    private LayoutParams paramImg;
    private String text;

    public ItemGuitar(Context context, int res1, String text, int res2, int position, int height) {
        super(context);
        this.position = position;
        this.text = text;
        initView(res1, text, res2, height);
    }

    private void initView(int res1, String text, int res2, int height) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        imgGuitar = new ImageView(getContext());
        imgGuitar.setAdjustViewBounds(true);
        paramImgGuitar = new LayoutParams(dpToPx(80), ViewGroup.LayoutParams.WRAP_CONTENT);

        txtGuitar = new TextView(getContext());
        txtGuitar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        paramTxt = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramTxt.setMargins(dpToPx(8), 0, 0, 0);

        imgChecked = new ImageView(getContext());
        imgChecked.setAdjustViewBounds(true);
        imgChecked.setVisibility(INVISIBLE);
        paramImg = new LayoutParams(dpToPx(24), ViewGroup.LayoutParams.WRAP_CONTENT);
        paramImg.setMargins(dpToPx(4), 0, 0, 0);

        setCointainer(res1, text, res2);

        addView(imgGuitar);
        addView(txtGuitar);
        addView(imgChecked);

        txtGuitar.setTextColor(getResources().getColor(R.color.tempo_txt_tap));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickInterface(position);
            }
        });
    }

    private void setCointainer(int res1, String text, int res2) {
        Glide.with(getContext()).load(res1).into(imgGuitar);
        Glide.with(getContext()).load(res2).into(imgChecked);
        txtGuitar.setText(text);

        imgGuitar.setLayoutParams(paramImgGuitar);
        imgChecked.setLayoutParams(paramImg);
        txtGuitar.setLayoutParams(paramTxt);
    }

    public String getText() {
        return text;
    }

    public void setOnClickItem(IOnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public interface IOnClickItem {
        void onClickInterface(int position);
    }

    public void guitarChoice(boolean isChoosen) {
        if (isChoosen) {
            txtGuitar.setTypeface(Typeface.DEFAULT_BOLD);
            txtGuitar.setTextColor(getResources().getColor(R.color.color_beat_choosen));
            imgChecked.setVisibility(VISIBLE);
            imgChecked.setColorFilter(getResources().getColor(R.color.color_beat_choosen));
            Hawk.put(Key.GUITAR, txtGuitar.getText().toString());
        } else {
            txtGuitar.setTypeface(Typeface.DEFAULT);
            txtGuitar.setTextColor(getResources().getColor(R.color.tempo_txt_tap));
            imgChecked.setVisibility(INVISIBLE);
        }
    }
}
