package com.pdc.metronome.ui.frags;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.pdc.metronome.R;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class BeatFrag extends Fragment {

    private static final String TAG = "BeatFrag";

    private ImageView imgBackground;
    private ImageView imgFrequencyBG;

    private TextView txtNote;
    private TextView txtHz;
    private TextView txtHzz;

    private SwitchCompat btnSwitch;
    private Thread audioThread;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_beat, container, false);

        initView();
        setImage();
        switchTurnedOff();
        onListener();

        return rootView;
    }

    private void frequencySeeking() {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
                final float pitchInHz = pitchDetectionResult.getPitch();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processPitch(pitchInHz);
                    }
                });
            }
        };

        AudioProcessor processor = new
                PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(processor);

        if (audioThread == null) {
            audioThread = new Thread(dispatcher, "Audio Thread");
            audioThread.start();
        }
    }

    private void processPitch(float pitchInHz) {

        String test = String.format("%.02f", pitchInHz);

        if (pitchInHz <= 0) {
            txtHz.setText("0");
        } else {
            txtHz.setText(test);
        }

        if (pitchInHz >= 110 && pitchInHz < 123.47) {
            //A
            txtNote.setText("A");
        } else if (pitchInHz >= 123.47 && pitchInHz < 130.81) {
            //B
            txtNote.setText("B");
        } else if (pitchInHz >= 130.81 && pitchInHz < 146.83) {
            //C
            txtNote.setText("C");
        } else if (pitchInHz >= 146.83 && pitchInHz < 164.81) {
            //D
            txtNote.setText("D");
        } else if (pitchInHz >= 164.81 && pitchInHz <= 174.61) {
            //E
            txtNote.setText("E");
        } else if (pitchInHz >= 174.61 && pitchInHz < 185) {
            //F
            txtNote.setText("F");
        } else if (pitchInHz >= 185 && pitchInHz < 196) {
            //G
            txtNote.setText("G");
        }
    }

    private void onListener() {
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switchTurnedOn();
                    toastRemind(0);
                } else {
                    switchTurnedOff();
                    toastRemind(1);
                }
            }
        });
    }

    private void toastRemind(int type) {
        if (type == 0){
            Toast.makeText(getActivity(), "Turned on frequency seeking!", Toast.LENGTH_SHORT).show();
        }
        if (type == 1){
            Toast.makeText(getActivity(), "Turned off frequency seeking!", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchTurnedOff() {
        animatorInvisibleView();
        invisibleView();
        checkThread();
    }

    private void checkThread() {
        if (audioThread != null) {
            audioThread.interrupt();
            audioThread = null;
        }
    }

    private void invisibleView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtNote.setVisibility(View.INVISIBLE);
                txtHz.setVisibility(View.INVISIBLE);
                txtHzz.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }

    private void animatorInvisibleView() {
        ViewAnimator.animate(txtNote).fadeOut().duration(500).start();
        ViewAnimator.animate(txtHz).fadeOut().duration(500).start();
        ViewAnimator.animate(txtHzz).fadeOut().duration(500).start();
    }

    private void switchTurnedOn() {
        visibleView();
        animatorVisibleView();

        txtNote.setText("-");
        frequencySeeking();
    }

    private void animatorVisibleView() {
        ViewAnimator.animate(txtNote).fadeIn().duration(500).start();
        ViewAnimator.animate(txtHz).fadeIn().duration(500).start();
        ViewAnimator.animate(txtHzz).fadeIn().duration(500).start();
    }

    private void visibleView() {
        txtNote.setVisibility(View.VISIBLE);
        txtHz.setVisibility(View.VISIBLE);
        txtHzz.setVisibility(View.VISIBLE);
    }

    private void setImage() {
        Glide.with(getContext()).load(R.drawable.bg_1).into(imgBackground);
        Glide.with(getContext()).load(R.drawable.bg_frequency).into(imgFrequencyBG);
    }

    private void initView() {
        imgBackground = rootView.findViewById(R.id.img_bg);
        imgFrequencyBG = rootView.findViewById(R.id.img_frequency_bg);

        txtNote = rootView.findViewById(R.id.txt_note);
        txtHz = rootView.findViewById(R.id.txt_hz);
        txtHzz = rootView.findViewById(R.id.txt_hzz);

        btnSwitch = rootView.findViewById(R.id.btn_switch);
    }

}
