package com.pdc.metronome.ui.frags;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.orhanobut.hawk.Hawk;
import com.pdc.metronome.R;
import com.pdc.metronome.item.BpmCalculator;
import com.pdc.metronome.item.SoundManager;
import com.pdc.metronome.constant.Key;
import com.pdc.metronome.item.Indicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MetronomeFrag extends Fragment {

    private static final String TAG = "MetronomeFrag";

    private List<com.pdc.metronome.layout.ItemBeat> itemBeats;
    private List<Indicator> indicators;

    private ImageView btnPlay;
    private ImageView imgBackground;
    private ImageView imgBpmBar;
    private ImageView btnPlus;
    private ImageView btnMinus;
    private TextView txtBpm;
    private ImageView imgTapOutside;
    private ImageView imgTapInside;
    private TextView txtBeat;

    private LinearLayout lnBeat;

    private LinearLayout lnIndicator;
    private int beats = 0;
    private long timeSleep = 0;
    private SoundManager soundManager;
    private Thread thread;

    private boolean isStart = false;

    private View rootView;
    private Vibrator vibe;
    private Timer timer;

    private BpmCalculator bpmCalculator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_metronome, container, false);
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        bpmCalculator = new BpmCalculator();

        initView();
        setImage();
        initData();
        invisibileViewBeat();
        initIndicator();
        timeCalculate();
        onListener();
        return rootView;
    }

    private void setImage() {
        Glide.with(getContext()).load(R.drawable.btn_play).into(btnPlay);
        Glide.with(getContext()).load(R.drawable.bg_1).into(imgBackground);
        Glide.with(getContext()).load(R.drawable.shadow_black_bar).into(imgBpmBar);
        Glide.with(getContext()).load(R.drawable.btn_plus).into(btnPlus);
        Glide.with(getContext()).load(R.drawable.btn_minus).into(btnMinus);
        Glide.with(getContext()).load(R.drawable.black_round_tap_outside).into(imgTapOutside);
        Glide.with(getContext()).load(R.drawable.black_round_tap_inside_with_text).into(imgTapInside);
    }

    private void initData() {
        int height = (HeightScreen() - dpToPx(144)) / 11;
        itemBeats = new ArrayList<>();
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "1/4", R.drawable.img_checked, 0, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "2/4", R.drawable.img_checked, 1, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "3/4", R.drawable.img_checked, 2, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "4/4", R.drawable.img_checked, 3, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "5/4", R.drawable.img_checked, 4, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "7/4", R.drawable.img_checked, 5, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "5/8", R.drawable.img_checked, 6, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "6/8", R.drawable.img_checked, 7, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "7/8", R.drawable.img_checked, 8, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "9/8", R.drawable.img_checked, 9, height));
        itemBeats.add(new com.pdc.metronome.layout.ItemBeat(getContext(), "12/8", R.drawable.img_checked, 10, height));


        for (int i = 0; i < itemBeats.size(); i++) {
            if (itemBeats.get(i).getText().equals(Hawk.get(Key.BEAT, "4/4"))) {
                itemBeats.get(i).beatChoice(true);
            }
            final com.pdc.metronome.layout.ItemBeat itemBeat = itemBeats.get(i);
            itemBeat.setOnClickItem(new com.pdc.metronome.layout.ItemBeat.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    onSelectedBeat(position);
                    invisibileViewBeat();

                    txtBeat.setText(Hawk.get(Key.BEAT, "4/4"));
                    Hawk.put(Key.BEAT, txtBeat.getText());

                    initIndicator();
                }
            });
            lnBeat.addView(itemBeat);
        }
    }

    private void onListener() {
        onClickBtnPlay();
        onClickBtnMinus();
        onClickBtnTap();
        onClickBpmBar();
        onClickTxtBeat();
        onClickBtnPlus();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onClickBpmBar() {
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
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    Hawk.put(Key.TEMPO, txtBpm.getText().toString());
                }
                return true;
            }
        });
    }

    private void onClickTxtBeat() {
        txtBeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibleViewBeat();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onClickBtnTap() {
        imgTapInside.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ViewAnimator.animate(imgTapInside).scale(1f, 0.9f).duration(100).start();
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ViewAnimator.animate(imgTapInside).scale(0.9f, 1f).duration(100).start();
                    actionTap();
                }
                return true;
            }
        });
    }

    private void actionTap() {
        checkIsPlaying();
        vibrate();
        bpmCalculator.recordTime();
        resetTimer();
        updateBpm();

        // TODO: 29/05/2019 bpm calculate
    }

    private void checkIsPlaying() {
        if (isStart){
            isStart = !isStart;
            ViewAnimator.animate(btnPlay).scale(0.9f, 1f).duration(100).start();
            Glide.with(getContext()).load(R.drawable.btn_play).into(btnPlay);
            onPlay();
        }
    }

    private void stopThread() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    private void updateBpm() {
        String updateBpm;

        if (bpmCalculator.times.size() >= 2) {
            int bpm = bpmCalculator.getBpm();
            updateBpm = Integer.valueOf(bpm).toString();
        } else {
            updateBpm = String.valueOf(txtBpm.getText());
        }

        txtBpm.setText(updateBpm);
        Hawk.put(Key.TEMPO, updateBpm);
    }

    private void resetTimer() {
        stopResetTimer();
        startResetTimer();
    }

    private void startResetTimer() {
        timer = new Timer("reset bpm calculating", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bpmCalculator.clearTimes();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toastRemind();
                    }
                });
            }
        }, Key.RESET_DURATION);
    }

    private void toastRemind() {
        Toast.makeText(getActivity(), "Tap again!", Toast.LENGTH_SHORT).show();
    }

    private void stopResetTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void vibrate() {
        vibe.vibrate(50);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onClickBtnMinus() {
        btnMinus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ViewAnimator.animate(btnMinus).scale(1f, 0.9f).duration(100).start();
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ViewAnimator.animate(btnMinus).scale(0.9f, 1f).duration(100).start();
                    txtBpm.setText(String.valueOf(Integer.parseInt(txtBpm.getText().toString()) - 1));

                    Hawk.put(Key.TEMPO, txtBpm.getText().toString());
                }
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onClickBtnPlay() {
        btnPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ViewAnimator.animate(btnPlay).scale(1f, 0.9f).duration(100).start();
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    isStart = !isStart;
                    onPlay();
                    ViewAnimator.animate(btnPlay).scale(0.9f, 1f).duration(100).start();
                    if (isStart) {
                        Glide.with(getContext()).load(R.drawable.btn_pause).into(btnPlay);
                    } else {
                        Glide.with(getContext()).load(R.drawable.btn_play).into(btnPlay);
                    }
                }
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onClickBtnPlus() {
        btnPlus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ViewAnimator.animate(btnPlus).scale(1f, 0.9f).duration(100).start();
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    ViewAnimator.animate(btnPlus).scale(0.9f, 1f).duration(100).start();
                    txtBpm.setText(String.valueOf(Integer.parseInt(txtBpm.getText().toString()) + 1));

                    Hawk.put(Key.TEMPO, txtBpm.getText().toString());
                }
                return true;
            }
        });
    }

    private void onPlay() {
        if (isStart) {
            playThread();
        } else {
            stopThread();
        }
    }

    private void playThread() {
        if (thread == null) {
            thread = new Thread(runnable);
            thread.start();
        }
    }

    private void initView() {
        btnPlay = rootView.findViewById(R.id.btn_play);
        imgBackground = rootView.findViewById(R.id.img_bg);
        imgBpmBar = rootView.findViewById(R.id.img_bpm_bar);
        btnPlus = rootView.findViewById(R.id.btn_plus);
        btnMinus = rootView.findViewById(R.id.btn_minus);
        txtBpm = rootView.findViewById(R.id.txt_bpm);
        imgTapOutside = rootView.findViewById(R.id.img_tap_outside);
        imgTapInside = rootView.findViewById(R.id.img_tap_inside);
        txtBeat = rootView.findViewById(R.id.txt_beat);
        lnBeat = rootView.findViewById(R.id.ln_beat);

        lnIndicator = rootView.findViewById(R.id.ln_indicator);

        soundManager = new SoundManager(getContext());

        txtBpm.setText(Hawk.get(Key.TEMPO, "128"));
        txtBeat.setText(Hawk.get(Key.BEAT, "4/4"));
    }

    private void initIndicator() {
        String beat = Hawk.get(Key.BEAT, "4/4");
        beats = Integer.valueOf(beat.substring(0, beat.indexOf("/")));
        indicators = new ArrayList<>();
        lnIndicator.removeAllViews();
        for (int i = 0; i < beats; i++) {
            Indicator indicator = new Indicator(getContext());
            if (i != 0) {
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dpToPx(8), dpToPx(8));
//                layoutParams.setMargins(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
//                indicator.setLayoutParams(layoutParams);
//                indicator.setBackgroundResource(R.drawable.bg_indicator);
                indicator.setSize();
            }
            indicators.add(indicator);
            lnIndicator.addView(indicator);
        }
    }

    private Runnable runnable = new Runnable() {

        int beat = 0;

        @Override
        public void run() {
            while (isStart) {
                if (beat >= beats) {
                    beat = 0;
                }
                if (beat == 0) {
                    soundManager.play(SoundManager.TypeSound.FIRST);
                } else {
                    soundManager.play(SoundManager.TypeSound.LOOP);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        colorChanged(beat);
                    }
                });

                try {
                    Thread.sleep(timeCalculate());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                beat++;
            }
        }
    };

    private void colorChanged(int position) {
        for (int i = 0; i < indicators.size(); i++) {
            indicators.get(i).colorChanged(false);
            Log.d(TAG, "colorChanged1: " + i);
        }
        indicators.get(position).colorChanged(true);
        Log.d(TAG, "colorChanged2: " + position);

    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private int HeightScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void visibleViewBeat() {
        lnBeat.setVisibility(View.VISIBLE);
        ViewAnimator.animate(lnBeat).slideBottomIn().duration(500).start();
    }

    private void invisibileViewBeat() {
        ViewAnimator.animate(lnBeat).fadeOut().duration(500).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lnBeat.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

    private long timeCalculate() {
        String getTempo = Hawk.get(Key.TEMPO, "128");
        String beat = Hawk.get(Key.BEAT, "4/4");
        int tempo = Integer.valueOf(getTempo);
        int getBeat = Integer.valueOf(beat.substring(beat.indexOf("/") + 1));
        if (getBeat == 4) {
            timeSleep = 1000 * 60 / tempo;
        } else {
            timeSleep = (1000 * 60 / tempo) / 2;
        }
        return timeSleep;
    }

    private void onSelectedBeat(int position) {
        for (int i = 0; i < itemBeats.size(); i++) {
            itemBeats.get(i).beatChoice(false);
        }
        itemBeats.get(position).beatChoice(true);
    }
}
