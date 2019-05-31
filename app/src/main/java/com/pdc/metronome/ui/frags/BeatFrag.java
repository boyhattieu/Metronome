package com.pdc.metronome.ui.frags;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.orhanobut.hawk.Hawk;
import com.pdc.metronome.R;
import com.pdc.metronome.constant.Key;
import com.pdc.metronome.item.NotesSound;
import com.pdc.metronome.layout.ItemNote;

import java.util.ArrayList;
import java.util.List;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class BeatFrag extends Fragment {

    private static final String TAG = "BeatFrag";

    private List<ItemNote> itemNotes;
    private LinearLayout lnNote;

    private ImageView imgBackground;
    private ImageView imgGuitar;
    private ImageView imgGuitar2;

    private TextView txtNote;
    private TextView txtHz;
    private TextView txtLastNote;
    private TextView txtTooHigh;
    private TextView txtTooLow;
    private TextView txtIncomplete;
    private TextView txtExcessive;

    private SwitchCompat btnSwitch;
    private Thread audioThread;

    private NotesSound notesSound;
    private CountDownTimer count;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_beat, container, false);

        initView();
        setImage();
        initData();
        setTextBackground("OFF");
        switchTurnedOff();
        onListener();

        return rootView;
    }

    private void initData() {
        int width = WidthScreen() / 6;
        itemNotes = new ArrayList<>();
        itemNotes.add(new ItemNote(getContext(), "E2", width, 0));
        itemNotes.add(new ItemNote(getContext(), "A2", width, 1));
        itemNotes.add(new ItemNote(getContext(), "D3", width, 2));
        itemNotes.add(new ItemNote(getContext(), "G3", width, 3));
        itemNotes.add(new ItemNote(getContext(), "B3", width, 4));
        itemNotes.add(new ItemNote(getContext(), "E4", width, 5));


        for (int i = 0; i < itemNotes.size(); i++) {
            final ItemNote itemNote = itemNotes.get(i);
            itemNote.setOnClickItem(new ItemNote.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    onSelectedNote(position);
                    checkNoteChosen(position);
                    invisibleNote();
                    txtNote.setText(Hawk.get(Key.NOTE, "Tap here to choose note"));
                }
            });
            lnNote.addView(itemNote);
        }
    }

    private void checkNoteChosen(int position) {
        switch (position) {
            case 0:
                onClickE2();
                break;

            case 1:
                onClickA2();
                break;

            case 2:
                onClickD3();
                break;

            case 3:
                onClickG3();
                break;

            case 4:
                onClickB3();
                break;

            case 5:
                onClickE4();
                break;

            default:
                break;
        }
    }

    private void invisibleNote() {
        ViewAnimator.animate(lnNote).fadeOut().duration(500).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lnNote.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

    private void setTextBackground(String TYPE) {
        if (TYPE.equals("OFF")) {
            txtNote.setBackgroundResource(R.drawable.bg_beat);
            txtNote.setPadding(dpToPx(10), dpToPx(2), dpToPx(10), dpToPx(2));
        }
        if (TYPE.equals("ON")) {
            txtNote.setBackgroundResource(android.R.color.transparent);
        }
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
        audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }

    private void processPitch(float pitchInHz) {
        if (btnSwitch.isChecked()) {
            whenSwitchIsOn(pitchInHz);
        }
        if (!btnSwitch.isChecked()) {
            whenSwitchIsOff(pitchInHz);
        }
    }

    private void whenSwitchIsOff(float pitchInHz) {

        setTextHz(pitchInHz);

        if (Hawk.get(Key.NOTE).equals("E2")) {
            handleE2(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("A2")) {
            handleA2(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("D3")) {
            handleD3(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("G3")) {
            handleG3(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("B3")) {
            handleB3(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("E4")) {
            handleE4(pitchInHz);
        }
    }

    private void handleE4(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 329.55 && pitchInHz <= 329.71) {
                invisibleText();
                checkTime();
            }
            if (pitchInHz < 329.55) {
                setIncompleteText(pitchInHz, 329);
            }
            if (pitchInHz > 329.71) {
                setExcessiveText(pitchInHz, 329);
            }
            if (pitchInHz <= 313.55) {
                setVisibility("low");
            }
            if (pitchInHz >= 345.71) {
                setVisibility("high");
            }
        } else {
            setVisibility("hideAll");
        }
    }

    private void invisibleText() {
        txtIncomplete.setVisibility(View.INVISIBLE);
        txtExcessive.setVisibility(View.INVISIBLE);
    }

    private void setExcessiveText(float pitchInHz, int number) {
        int pitch = Math.round(pitchInHz);
        txtExcessive.setVisibility(View.VISIBLE);
        txtExcessive.setText("+" + String.valueOf(pitch + number));
    }

    private void setIncompleteText(float pitchInHz, int number) {
        int pitch = Math.round(pitchInHz);
        txtIncomplete.setVisibility(View.VISIBLE);
        txtIncomplete.setText(String.valueOf(pitch - number));
    }

    private void handleB3(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 246.86 && pitchInHz <= 247.02) {
                checkTime();
            }
            if (pitchInHz <= 230.86) {
                setVisibility("low");
            }
            if (pitchInHz >= 263.02) {
                setVisibility("high");
            }
        } else {
            setVisibility("hideAll");
        }
    }

    private void handleG3(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 195.92 && pitchInHz <= 196.08) {
                checkTime();
            }
            if (pitchInHz <= 179.92) {
                setVisibility("low");
            }
            if (pitchInHz >= 212.08) {
                setVisibility("high");
            }
        } else {
            setVisibility("hideAll");
        }
    }

    private void handleD3(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 146.75 && pitchInHz <= 146.91) {
                checkTime();
            }
            if (pitchInHz <= 130.75) {
                setVisibility("low");
            }
            if (pitchInHz >= 162.91) {
                setVisibility("high");
            }
        } else {
            setVisibility("hideAll");
        }
    }

    private void handleA2(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 109.92 && pitchInHz <= 110.08) {
                checkTime();
            }
            if (pitchInHz <= 93.92) {
                setVisibility("low");
            }
            if (pitchInHz >= 126.08) {
                setVisibility("high");
            }
        } else {
            setVisibility("hideAll");
        }
    }

    private void handleE2(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 82.33 && pitchInHz <= 82.49) {
                checkTime();
            }
            if (pitchInHz <= 66.33) {
                setVisibility("low");
                checkThread();
            }
            if (pitchInHz >= 98.49) {
                setVisibility("high");
                checkThread();
            }
        } else {
            setVisibility("hideAll");
            checkThread();
        }
    }

    private void setVisibility(String typeVisibility) {
        if (typeVisibility.equals("high")) {
            txtTooHigh.setVisibility(View.VISIBLE);
            txtTooLow.setVisibility(View.INVISIBLE);
        }
        if (typeVisibility.equals("low")) {
            txtTooLow.setVisibility(View.VISIBLE);
            txtTooHigh.setVisibility(View.INVISIBLE);
        }
        if (typeVisibility.equals("hideAll")) {
            txtTooHigh.setVisibility(View.INVISIBLE);
            txtTooLow.setVisibility(View.INVISIBLE);
        }
    }

    private void checkTime() {
        stopCheckingTime();
        startCheckingTime();
    }

    private void startCheckingTime() {
        count = new CountDownTimer(1000, 1000) {

            int count = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                count++;
            }

            @Override
            public void onFinish() {
                if (count == 1) {
                    notesSound.play(NotesSound.TypeSound.CORRECT);
                }
            }
        };
        count.start();
    }

    private void stopCheckingTime() {
        if (count != null) {
            count.cancel();
        }
    }

    private void setTextHz(float pitchInHz) {
        String pitch = String.format("%.1f", pitchInHz);
        if (pitchInHz <= 0) {
            txtHz.setText("0");
        } else if (pitchInHz >= 350) {
            txtHz.setText("349,6");
        } else {
            txtHz.setText(pitch);
        }
    }

    private void whenSwitchIsOn(float pitchInHz) {
        setTextHz(pitchInHz);

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
        onClickBtnSwitch();
        onClickNoteText();
    }

    private void onClickNoteText() {
        txtNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnNote.setVisibility(View.VISIBLE);
                ViewAnimator.animate(lnNote).fadeIn().duration(500).start();
            }
        });
    }

    private void onClickG3() {
        notesSound.play(NotesSound.TypeSound.G3);
        setNoteImage("G3");
    }

    private void onClickD3() {
        notesSound.play(NotesSound.TypeSound.D3);
        setNoteImage("D3");
    }

    private void onClickB3() {
        notesSound.play(NotesSound.TypeSound.B3);
        setNoteImage("B3");
    }

    private void onClickA2() {
        notesSound.play(NotesSound.TypeSound.A2);
        setNoteImage("A2");
    }

    private void onClickE4() {
        notesSound.play(NotesSound.TypeSound.E4);
        setNoteImage("E4");
    }

    private void onClickE2() {
        notesSound.play(NotesSound.TypeSound.E2);
        setNoteImage("E2");
    }

    private void onClickBtnSwitch() {
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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
        if (type == 0) {
            Toast.makeText(getActivity(), "Auto is turned on!", Toast.LENGTH_SHORT).show();
        }
        if (type == 1) {
            Toast.makeText(getActivity(), "Auto is turned off!", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchTurnedOn() {
        setTextBackground("ON");
        invisibleView();
        animatorInvisibleView();
        txtLastNote.setText("Last note received");
        txtNote.setText("-");
        checkThread();
        frequencySeeking();
    }

    private void switchTurnedOff() {
        setTextBackground("OFF");
        setImgGuitar2();
        visibleView();
        animatorVisibleView();
        txtLastNote.setText("Note choosen");
        txtNote.setText("Tap to choose note");
        checkThread();
        frequencySeeking();
    }

    private void setNoteImage(String TYPE) {
        if (TYPE.equals("E2")) {
            Glide.with(getContext()).load(R.drawable.guitar_e2).into(imgGuitar2);
        }
        if (TYPE.equals("E4")) {
            Glide.with(getContext()).load(R.drawable.guitar_e4).into(imgGuitar2);
        }
        if (TYPE.equals("A2")) {
            Glide.with(getContext()).load(R.drawable.guitar_a2).into(imgGuitar2);
        }
        if (TYPE.equals("B3")) {
            Glide.with(getContext()).load(R.drawable.guitar_b3).into(imgGuitar2);
        }
        if (TYPE.equals("D3")) {
            Glide.with(getContext()).load(R.drawable.guitar_d3).into(imgGuitar2);
        }
        if (TYPE.equals("G3")) {
            Glide.with(getContext()).load(R.drawable.guitar_g3).into(imgGuitar2);
        }
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
                imgGuitar2.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

    private void animatorInvisibleView() {
        ViewAnimator.animate(imgGuitar2).fadeOut().duration(500).start();
    }

    private void animatorVisibleView() {
        ViewAnimator.animate(imgGuitar2).fadeIn().duration(500).start();
    }

    private void visibleView() {
        imgGuitar2.setVisibility(View.VISIBLE);
    }

    private void setImgGuitar2() {
        Glide.with(getContext()).load(R.drawable.guitar_change).into(imgGuitar2);
    }

    private void setImage() {
        Glide.with(getContext()).load(R.drawable.bg_1).into(imgBackground);
        Glide.with(getContext()).load(R.drawable.guitar_auto_l).into(imgGuitar);
        Glide.with(getContext()).load(R.drawable.guitar_change).into(imgGuitar2);
    }

    private void initView() {
        imgBackground = rootView.findViewById(R.id.img_bg);
        imgGuitar = rootView.findViewById(R.id.img_guitar);
        imgGuitar2 = rootView.findViewById(R.id.img_guitar_2);

        txtNote = rootView.findViewById(R.id.txt_note);
        txtHz = rootView.findViewById(R.id.txt_hz);
        txtLastNote = rootView.findViewById(R.id.txt_last_note);
        txtTooHigh = rootView.findViewById(R.id.txt_too_high);
        txtTooLow = rootView.findViewById(R.id.txt_too_low);
        txtIncomplete = rootView.findViewById(R.id.txt_incomplete);
        txtExcessive = rootView.findViewById(R.id.txt_excessive);

        btnSwitch = rootView.findViewById(R.id.btn_switch);

        lnNote = rootView.findViewById(R.id.ln_note);

        notesSound = new NotesSound(getContext());
    }

    private int WidthScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private void onSelectedNote(int position) {
        for (int i = 0; i < itemNotes.size(); i++) {
            itemNotes.get(i).noteChoice(false);
        }
        itemNotes.get(position).noteChoice(true);
    }
}
