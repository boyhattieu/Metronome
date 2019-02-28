package com.pdc.metronome.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pdc.metronome.R;

public class ItemTab  extends LinearLayout {

    private ImageView imgTab;
    private TextView txtTab;
    private int position;
    private IOnClickItem onClickItem;
    private LayoutParams paramsImg;
    private LayoutParams paramsTxt;

    public void setOnClickItem(IOnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public ItemTab(Context context, int res, String text, int width, int position) {
        super(context);
        this.position = position;
        initView(res, text, width);
    }

    public void selected(boolean isSelect){
        if (isSelect){
            txtTab.setTextColor(Color.parseColor("#754123"));
            imgTab.setColorFilter(Color.parseColor("#754123"));
        } else {
            txtTab.setTextColor(Color.GRAY);
            imgTab.setColorFilter(Color.GRAY);
        }
    }

    private void initView(int res, String text, int width) {
        LayoutParams layoutParams = new LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        imgTab = new ImageView(getContext());
        txtTab = new TextView(getContext());
        setOrientation(VERTICAL);
        paramsImg = new LayoutParams(100,100);
        paramsTxt = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContainer(res, text);
        addView(imgTab);
        addView(txtTab);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickInterface(position);
            }
        });
    }

    private void setContainer(int res, String text) {
        imgTab.setImageResource(res);
        txtTab.setText(text);
        imgTab.setLayoutParams(paramsImg);
        txtTab.setLayoutParams(paramsTxt);
    }

    public interface IOnClickItem{
        void onClickInterface(int position);
    }
}
