package com.pdc.metronome.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.pdc.metronome.R;
import com.pdc.metronome.constant.Key;

public class ItemNote extends LinearLayout {

    private TextView txtNote;
    private int position;
    private IOnClickItem onClickItem;
    private LayoutParams paramsTxt;

    public void setOnClickItem(IOnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public ItemNote(Context context, String text, int width, int position) {
        super(context);
        this.position = position;
        initView(text, width);
    }

    private void initView(String text, int width) {
        LayoutParams layoutParams = new LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        txtNote = new TextView(getContext());
        paramsTxt = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtNote.setLayoutParams(paramsTxt);
        txtNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        setContainer(text);

        addView(txtNote);

        txtNote.setTextColor(getResources().getColor(R.color.tempo_txt_tap));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickInterface(position);
            }
        });
    }

    private void setContainer(String text) {
        txtNote.setText(text);
        txtNote.setLayoutParams(paramsTxt);
    }


    public interface IOnClickItem {
        void onClickInterface(int position);
    }

    public void noteChoice(boolean isChoosen) {
        if (isChoosen) {
            txtNote.setTypeface(Typeface.DEFAULT_BOLD);
            txtNote.setTextColor(getResources().getColor(R.color.stroke_beat));
            Hawk.put(Key.NOTE, txtNote.getText().toString());
        } else {
            txtNote.setTypeface(Typeface.DEFAULT);
            txtNote.setTextColor(getResources().getColor(R.color.tempo_txt_tap));
        }
    }
}
