package com.pdc.metronome.item;

import android.content.Context;
import android.widget.RelativeLayout;

import com.pdc.metronome.R;

public class Indicator extends RelativeLayout {

    public Indicator(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.indicator, this);
    }
}
