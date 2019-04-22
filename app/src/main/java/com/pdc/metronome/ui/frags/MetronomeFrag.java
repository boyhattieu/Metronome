package com.pdc.metronome.ui.frags;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.orhanobut.hawk.Hawk;
import com.pdc.metronome.R;
import com.pdc.metronome.SoundManager;
import com.pdc.metronome.adapter.viewpager.ViewPager;
import com.pdc.metronome.constant.Constant;
import com.pdc.metronome.constant.Key;
import com.pdc.metronome.item.Indicator;

public class MetronomeFrag extends Fragment {

    private static final String TAG = "MetronomeFrag";

    private ImageView btnPlay;
    private ImageView btnSetting;
    private ImageView imgBackground;
    private ImageView imgBpmBar;
    private ImageView btnPlus;
    private ImageView btnMinus;
    private TextView txtBpm;
    private ImageView imgTapOutside;
    private ImageView imgTapInside;
    private TextView txtTap;
    private TextView txtBeat;

    private LinearLayout lnIndicator;
    private int beats = 0;
    private long timeSleep = 1000L;
    private SoundManager soundManager;
    private boolean isPlay = false;
    private Thread thread;

    private boolean isStart = false;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_metronome, container, false);
        initView();
        onListener();
        return rootView;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onListener() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = !isStart;
                onPlay();
                ViewAnimator.animate(btnPlay).scale(1f, 0.9f, 1f).duration(100).start();
                if (isStart) {
                    Glide.with(getContext()).load(R.drawable.btn_pause).into(btnPlay);
                } else {
                    Glide.with(getContext()).load(R.drawable.btn_play).into(btnPlay);
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAnimator.animate(btnPlus).scale(1f, 0.9f, 1f).duration(100).start();
                txtBpm.setText(String.valueOf(Integer.parseInt(txtBpm.getText().toString()) + 1));
                Log.d(TAG, "onClickClick+: " + txtBpm.getText());
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAnimator.animate(btnMinus).scale(1f, 0.9f, 1f).duration(100).start();
                txtBpm.setText(String.valueOf(Integer.parseInt(txtBpm.getText().toString()) - 1));
                Log.d(TAG, "onClickClick-: " + txtBpm.getText());
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAnimator.animate(btnSetting).scale(1f, 0.9f, 1f).duration(100).start();
            }
        });


        imgTapInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAnimator.animate(imgTapInside).scale(1f, 0.8f, 1f).duration(100).start();
                ViewAnimator.animate(txtTap).scale(1f, 0.8f, 1f).duration(100).start();
            }
        });

        imgBpmBar.setOnTouchListener(new View.OnTouchListener() {
            float x = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = event.getX();
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    txtBpm.setText(String.valueOf(Integer.valueOf(Hawk.get(Key.TEMPO, "128")) + Math.round(event.getX() - x)));
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    Hawk.put(Key.TEMPO, txtBpm.getText());
                }
                return true;
            }
        });
    }

    private void onPlay() {
        isPlay = !isPlay;
        if (isPlay) {
            thread = new Thread(runnable);
            thread.start();
        } else {
            thread.interrupt();
        }
    }

    private void initView() {
        btnPlay = rootView.findViewById(R.id.btn_play);
        btnSetting = rootView.findViewById(R.id.btn_setting);
        imgBackground = rootView.findViewById(R.id.img_bg);
        imgBpmBar = rootView.findViewById(R.id.img_bpm_bar);
        btnPlus = rootView.findViewById(R.id.btn_plus);
        btnMinus = rootView.findViewById(R.id.btn_minus);
        txtBpm = rootView.findViewById(R.id.txt_bpm);
        imgTapOutside = rootView.findViewById(R.id.img_tap_outside);
        imgTapInside = rootView.findViewById(R.id.img_tap_inside);
        txtTap = rootView.findViewById(R.id.txt_tap);
        txtBeat = rootView.findViewById(R.id.txt_beat);

        lnIndicator = rootView.findViewById(R.id.ln_indicator);

        soundManager = new SoundManager(getContext());

        txtBpm.setText(Hawk.get(Key.TEMPO, "128"));
        txtBeat.setText(Hawk.get(Key.BEAT, Constant.BEAT[3]));

        Glide.with(getContext()).load(R.drawable.btn_play).into(btnPlay);
        Glide.with(getContext()).load(R.drawable.bg_1).into(imgBackground);
        Glide.with(getContext()).load(R.drawable.shadow_black_bar).into(imgBpmBar);
        Glide.with(getContext()).load(R.drawable.btn_plus).into(btnPlus);
        Glide.with(getContext()).load(R.drawable.btn_minus).into(btnMinus);
        Glide.with(getContext()).load(R.drawable.btn_setting_grey).into(btnSetting);
        Glide.with(getContext()).load(R.drawable.black_round_tap_outside).into(imgTapOutside);
        Glide.with(getContext()).load(R.drawable.black_round_tap_inside).into(imgTapInside);

        initIndicator();
    }

    private void initIndicator() {
        String beat = Hawk.get(Key.BEAT, Constant.BEAT[3]);
        beats = Integer.valueOf(beat.substring(0, beat.indexOf("/")));
        lnIndicator.removeAllViews();
        for (int i = 0; i < beats; i++) {
            Indicator indicator = new Indicator(getContext());
            lnIndicator.addView(indicator);
        }
    }

    private Runnable runnable = new Runnable() {
        int beat = 0;

        @Override
        public void run() {
            while (isPlay) {
                if (beat == 0) {
                    soundManager.play(SoundManager.TypeSound.FIRST);
                } else {
                    soundManager.play(SoundManager.TypeSound.LOOP);
                }
                beat++;
                if (beat > (beats - 1)) {
                    beat = 0;
                }
                try {
                    Thread.sleep(timeSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
