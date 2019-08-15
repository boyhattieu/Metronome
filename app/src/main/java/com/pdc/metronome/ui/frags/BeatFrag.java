package com.pdc.metronome.ui.frags;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
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
import com.pdc.metronome.layout.ItemGuitar;
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

    private List<ItemNote> itemNotes;
    private List<ItemGuitar> itemGuitars;
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
    private TextView txtGuitar;

    private SwitchCompat btnSwitch;
    private Thread audioThread;

    private NotesSound notesSound;
    private CountDownTimer count;

    private LinearLayout lnGuitar;

    protected FragmentActivity mActivity;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_beat, container, false);

        setNone();
        initView();
        setImage();
        initData();
        checkGuitarType();
        setTextBackground("OFF");
        switchTurnedOff();
        onListener();

        return rootView;
    }

    private void checkGuitarType() {
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Classic")) {
            selectedGuitarClassic();
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 4-String")) {
            selectedGuitarBass4String();
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 5-String")) {
            selectedGuitarBass5String();
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 6-String")) {
            selectedGuitarBass6String();
        }
    }

    private void clearList() {
        if (!itemNotes.isEmpty()) {
            itemNotes.clear();
        }
    }

    @SuppressLint("SetTextI18n")
    private void selectedGuitarClassic() {
        setGuitarImages();
        int width = WidthScreen() / 6;
        itemNotes = new ArrayList<>();
        clearList();
        itemNotes.add(new ItemNote(getContext(), "E2", width, 0));
        itemNotes.add(new ItemNote(getContext(), "A2", width, 1));
        itemNotes.add(new ItemNote(getContext(), "D3", width, 2));
        itemNotes.add(new ItemNote(getContext(), "G3", width, 3));
        itemNotes.add(new ItemNote(getContext(), "B3", width, 4));
        itemNotes.add(new ItemNote(getContext(), "E4", width, 5));
        lnNote.removeAllViews();

        if (!btnSwitch.isChecked()) {
            txtNote.setText("Tap here to choose note");
        }
        for (int i = 0; i < itemNotes.size(); i++) {
            final ItemNote itemNote = itemNotes.get(i);
            itemNote.setOnClickItem(new ItemNote.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    onSelectedClassicNote(position);
                    checkClassicNoteChosen(position);
                    invisibleNote();
                    txtNote.setText(Hawk.get(Key.NOTE, "Tap here to choose note"));
                }
            });
            lnNote.addView(itemNote);
        }
    }

    @SuppressLint("SetTextI18n")
    private void selectedGuitarBass4String() {
        setGuitarImages();
        int width = WidthScreen() / 4;
        itemNotes = new ArrayList<>();
        clearList();
        itemNotes.add(new ItemNote(getContext(), "E1", width, 0));
        itemNotes.add(new ItemNote(getContext(), "A1", width, 1));
        itemNotes.add(new ItemNote(getContext(), "D2", width, 2));
        itemNotes.add(new ItemNote(getContext(), "G2", width, 3));
        lnNote.removeAllViews();

        if (!btnSwitch.isChecked()) {
            txtNote.setText("Tap here to choose note");
        }
        for (int i = 0; i < itemNotes.size(); i++) {
            final ItemNote itemNote = itemNotes.get(i);
            itemNote.setOnClickItem(new ItemNote.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    onSelectedClassicNote(position);
                    checkBass4NoteChosen(position);
                    invisibleNote();
                    txtNote.setText(Hawk.get(Key.NOTE, "Tap here to choose note"));
                }
            });
            lnNote.addView(itemNote);
        }
    }

    @SuppressLint("SetTextI18n")
    private void selectedGuitarBass5String() {
        setGuitarImages();
        int width = WidthScreen() / 5;
        itemNotes = new ArrayList<>();
        clearList();
        itemNotes.add(new ItemNote(getContext(), "B0", width, 0));
        itemNotes.add(new ItemNote(getContext(), "E1", width, 1));
        itemNotes.add(new ItemNote(getContext(), "A1", width, 2));
        itemNotes.add(new ItemNote(getContext(), "D2", width, 3));
        itemNotes.add(new ItemNote(getContext(), "G2", width, 4));
        lnNote.removeAllViews();

        if (!btnSwitch.isChecked()) {
            txtNote.setText("Tap here to choose note");
        }
        for (int i = 0; i < itemNotes.size(); i++) {
            final ItemNote itemNote = itemNotes.get(i);
            itemNote.setOnClickItem(new ItemNote.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    onSelectedClassicNote(position);
                    checkBass5NoteChosen(position);
                    invisibleNote();
                    txtNote.setText(Hawk.get(Key.NOTE, "Tap here to choose note"));
                }
            });
            lnNote.addView(itemNote);
        }
    }

    @SuppressLint("SetTextI18n")
    private void selectedGuitarBass6String() {
        setGuitarImages();
        int width = WidthScreen() / 6;
        itemNotes = new ArrayList<>();
        clearList();
        itemNotes.add(new ItemNote(getContext(), "B0", width, 0));
        itemNotes.add(new ItemNote(getContext(), "E1", width, 1));
        itemNotes.add(new ItemNote(getContext(), "A1", width, 2));
        itemNotes.add(new ItemNote(getContext(), "D2", width, 3));
        itemNotes.add(new ItemNote(getContext(), "G2", width, 4));
        itemNotes.add(new ItemNote(getContext(), "C3", width, 5));
        lnNote.removeAllViews();

        if (!btnSwitch.isChecked()) {
            txtNote.setText("Tap here to choose note");
        }
        for (int i = 0; i < itemNotes.size(); i++) {
            final ItemNote itemNote = itemNotes.get(i);
            itemNote.setOnClickItem(new ItemNote.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    onSelectedClassicNote(position);
                    checkBass6NoteChosen(position);
                    invisibleNote();
                    txtNote.setText(Hawk.get(Key.NOTE, "Tap here to choose note"));
                }
            });
            lnNote.addView(itemNote);
        }

    }

    private void setNone() {
        Hawk.put(Key.NOTE, "none");
    }

    private void initData() {
        int height = (HeightScreen() - dpToPx(144)) / 4;
        itemGuitars = new ArrayList<>();
        itemGuitars.add(new ItemGuitar(getContext(), R.drawable.classic, "Guitar Classic", R.drawable.img_checked, 0, height));
        itemGuitars.add(new ItemGuitar(getContext(), R.drawable.bass_4, "Guitar Bass 4-String", R.drawable.img_checked, 1, height));
        itemGuitars.add(new ItemGuitar(getContext(), R.drawable.bass_5, "Guitar Bass 5-String", R.drawable.img_checked, 2, height));
        itemGuitars.add(new ItemGuitar(getContext(), R.drawable.bass_6, "Guitar Bass 6-String", R.drawable.img_checked, 3, height));

        txtGuitar.setText(Hawk.get(Key.GUITAR_CHOOSEN, "Classic/Acoustic"));
        for (int i = 0; i < itemGuitars.size(); i++) {
            if (itemGuitars.get(i).getText().equals(Hawk.get(Key.GUITAR, "Guitar Classic"))) {
                itemGuitars.get(i).guitarChoice(true);
            }
            final ItemGuitar itemGuitar = itemGuitars.get(i);
            itemGuitar.setOnClickItem(new ItemGuitar.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    onSelectedGuitar(position);
                    invisibleGuitarList();
                    checkGuitarChoosen(position);

                    Hawk.put(Key.GUITAR, itemGuitars.get(position).getText());
                    txtGuitar.setText(setGuitarText());
                }
            });
            lnGuitar.addView(itemGuitar);
        }
    }

    private void checkGuitarChoosen(int position) {
        if (position == 0) {
            selectedGuitarClassic();
        }
        if (position == 1) {
            selectedGuitarBass4String();
        }
        if (position == 2) {
            selectedGuitarBass5String();
        }
        if (position == 3) {
            selectedGuitarBass6String();
        }
    }

    @SuppressLint("SetTextI18n")
    private String setGuitarText() {
        if (Hawk.get(Key.GUITAR).equals("Guitar Classic")) {
            Hawk.put(Key.GUITAR_CHOOSEN, "Classic/Acoustic");
        }
        if (Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Hawk.put(Key.GUITAR_CHOOSEN, "Bass 4-String");
        }
        if (Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Hawk.put(Key.GUITAR_CHOOSEN, "Bass 5-String");
        }
        if (Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Hawk.put(Key.GUITAR_CHOOSEN, "Bass 6-String");
        }

        return Hawk.get(Key.GUITAR_CHOOSEN, "Classic/Acoustic");
    }

    private void invisibleGuitarList() {
        ViewAnimator.animate(lnGuitar).fadeOut().duration(500).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lnGuitar.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

    private void checkClassicNoteChosen(int position) {
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

    private void checkBass4NoteChosen(int position) {
        switch (position) {
            case 0:
                onClickE1();
                break;

            case 1:
                onClickA1();
                break;

            case 2:
                onClickD2();
                break;

            case 3:
                onClickG2();
                break;

            default:
                break;
        }
    }

    private void checkBass5NoteChosen(int position) {
        switch (position) {
            case 0:
                onClickB0();
                break;

            case 1:
                onClickE1();
                break;

            case 2:
                onClickA1();
                break;

            case 3:
                onClickD2();
                break;

            case 4:
                onClickG2();
                break;

            default:
                break;
        }
    }

    private void checkBass6NoteChosen(int position) {
        switch (position) {
            case 0:
                onClickB0();
                break;

            case 1:
                onClickE1();
                break;

            case 2:
                onClickA1();
                break;

            case 3:
                onClickD2();
                break;

            case 4:
                onClickG2();
                break;

            case 5:
                onClickC3();
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
        final AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
                final float pitchInHz = pitchDetectionResult.getPitch();

                mActivity.runOnUiThread(new Runnable() {
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

        checkThread();
        if (audioThread == null) {
            audioThread = new Thread(dispatcher, "Audio Thread");
            audioThread.start();
        }
    }

    private void processPitch(float pitchInHz) {
        if (btnSwitch.isChecked()) {
            whenSwitchIsOn(pitchInHz);
        }
        if (!btnSwitch.isChecked()) {
            whenSwitchIsOff(pitchInHz);
        }
    }

    private void invisibleText() {
        txtIncomplete.setVisibility(View.INVISIBLE);
        txtExcessive.setVisibility(View.INVISIBLE);
    }

    private void setExcessiveText(float pitchInHz, int number) {
        int pitch = Math.round(pitchInHz);
        if (pitchInHz >= 360) {
            txtExcessive.setVisibility(View.INVISIBLE);
        } else {
            txtExcessive.setVisibility(View.VISIBLE);
            txtIncomplete.setVisibility(View.INVISIBLE);
            txtExcessive.setText("+" + String.valueOf(pitch - number));
        }
    }

    private void setIncompleteText(float pitchInHz, int number) {
        int pitch = Math.round(pitchInHz);
        txtIncomplete.setVisibility(View.VISIBLE);
        txtExcessive.setVisibility(View.INVISIBLE);
        txtIncomplete.setText(String.valueOf(pitch - number));
    }

    private void whenSwitchIsOff(float pitchInHz) {

        setTextHz(pitchInHz);

        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Classic")) {
            handleGuitarClassicManual(pitchInHz);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 4-String")) {
            handleGuitarBass4Manual(pitchInHz);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 5-String")) {
            handleGuitarBass5Manual(pitchInHz);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 6-String")) {
            handleGuitarBass6Manual(pitchInHz);
        }

    }

    private void whenSwitchIsOn(float pitchInHz) {
        setTextHz(pitchInHz);

        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Classic")) {
            handleGuitarClassicAuto(pitchInHz);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 4-String")) {
            handleGuitarBass4Auto(pitchInHz);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 5-String")) {
            handleGuitarBass5Auto(pitchInHz);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 6-String")) {
            handleGuitarBass6Auto(pitchInHz);
        }

    }

    private void handleGuitarBass4Auto(float pitchInHz) {
        if (pitchInHz >= 41.13 && pitchInHz < 41.29) {
            handleOn("E1");
        } else if (pitchInHz >= 54.92 && pitchInHz < 55.08) {
            handleOn("A1");
        } else if (pitchInHz >= 73.34 && pitchInHz < 73.50) {
            handleOn("D2");
        } else if (pitchInHz >= 97.92 && pitchInHz <= 98.08) {
            handleOn("G2");
        }
    }

    private void handleGuitarBass5Auto(float pitchInHz) {
        if (pitchInHz >= 30.79 && pitchInHz < 30.95) {
            handleOn("B0");
        } else if (pitchInHz >= 41.13 && pitchInHz < 41.29) {
            handleOn("E1");
        } else if (pitchInHz >= 54.92 && pitchInHz < 55.08) {
            handleOn("A1");
        } else if (pitchInHz >= 73.34 && pitchInHz < 73.50) {
            handleOn("D2");
        } else if (pitchInHz >= 97.92 && pitchInHz <= 98.08) {
            handleOn("G2");
        }
    }

    private void handleGuitarBass6Auto(float pitchInHz) {
        if (pitchInHz >= 30.79 && pitchInHz < 30.95) {
            handleOn("B0");
        } else if (pitchInHz >= 41.13 && pitchInHz < 41.29) {
            handleOn("E1");
        } else if (pitchInHz >= 54.92 && pitchInHz < 55.08) {
            handleOn("A1");
        } else if (pitchInHz >= 73.34 && pitchInHz < 73.50) {
            handleOn("D2");
        } else if (pitchInHz >= 97.92 && pitchInHz <= 98.08) {
            handleOn("G2");
        } else if (pitchInHz >= 130.73 && pitchInHz <= 130.89) {
            handleOn("C3");
        }
    }

    private void handleGuitarClassicAuto(float pitchInHz) {
        if (pitchInHz >= 82.33 && pitchInHz < 82.49) {
            handleOn("E2");
        } else if (pitchInHz >= 109.92 && pitchInHz < 110.08) {
            handleOn("A2");
        } else if (pitchInHz >= 146.75 && pitchInHz < 146.91) {
            handleOn("D3");
        } else if (pitchInHz >= 195.92 && pitchInHz <= 196.08) {
            handleOn("G3");
        } else if (pitchInHz >= 246.86 && pitchInHz < 247.02) {
            handleOn("B3");
        } else if (pitchInHz >= 329.55 && pitchInHz <= 329.71) {
            handleOn("E4");
        }
    }

    private void handleGuitarBass4Manual(float pitchInHz) {
        if (Hawk.get(Key.NOTE).equals("E1")) {
            handleE1(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("A1")) {
            handleA1(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("D2")) {
            handleD2(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("G2")) {
            handleG2(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("none")) {
            checkThread();
        }
    }

    private void handleGuitarBass5Manual(float pitchInHz) {
        if (Hawk.get(Key.NOTE).equals("B0")) {
            handleB0(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("E1")) {
            handleE1(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("A1")) {
            handleA1(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("D2")) {
            handleD2(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("G2")) {
            handleG2(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("none")) {
            checkThread();
        }
    }

    private void handleGuitarBass6Manual(float pitchInHz) {
        if (Hawk.get(Key.NOTE).equals("B0")) {
            handleB0(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("E1")) {
            handleE1(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("A1")) {
            handleA1(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("D2")) {
            handleD2(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("G2")) {
            handleG2(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("C3")) {
            handleC3(pitchInHz);
        }
        if (Hawk.get(Key.NOTE).equals("none")) {
            checkThread();
        }
    }

    private void handleGuitarClassicManual(float pitchInHz) {
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
        if (Hawk.get(Key.NOTE).equals("none")) {
            checkThread();
        }
    }

    private void handleOn(String noteName) {
        countingWork();
        txtNote.setText(noteName);
        ViewAnimator.animate(imgGuitar2).fadeIn().duration(500).start();

        if (noteName.equals("E1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_e1_auto).into(imgGuitar2);
        }
        if (noteName.equals("E1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_e1_auto).into(imgGuitar2);
        }
        if (noteName.equals("E1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_e1_auto).into(imgGuitar2);
        }
        if (noteName.equals("E2")) {
            Glide.with(getContext()).load(R.drawable.guitar_auto_e2).into(imgGuitar2);
        }
        if (noteName.equals("A1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_a1_auto).into(imgGuitar2);
        }
        if (noteName.equals("A1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_a1_auto).into(imgGuitar2);
        }
        if (noteName.equals("A1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_a1_auto).into(imgGuitar2);
        }
        if (noteName.equals("A2")) {
            Glide.with(getContext()).load(R.drawable.guitar_auto_a2).into(imgGuitar2);
        }
        if (noteName.equals("D2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_d2_auto).into(imgGuitar2);
        }
        if (noteName.equals("D2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_d2_auto).into(imgGuitar2);
        }
        if (noteName.equals("D2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_d2_auto).into(imgGuitar2);
        }
        if (noteName.equals("D3")) {
            Glide.with(getContext()).load(R.drawable.guitar_auto_d3).into(imgGuitar2);
        }
        if (noteName.equals("G2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_g2_auto).into(imgGuitar2);
        }
        if (noteName.equals("G2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_g2_auto).into(imgGuitar2);
        }
        if (noteName.equals("G2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_g2_auto).into(imgGuitar2);
        }
        if (noteName.equals("G3")) {
            Glide.with(getContext()).load(R.drawable.guitar_auto_g3).into(imgGuitar2);
        }
        if (noteName.equals("B0") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_b0_auto).into(imgGuitar2);
        }
        if (noteName.equals("B0") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_b0_auto).into(imgGuitar2);
        }
        if (noteName.equals("B3")) {
            Glide.with(getContext()).load(R.drawable.guitar_auto_b3).into(imgGuitar2);
        }
        if (noteName.equals("C3")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_c3_auto).into(imgGuitar2);
        }
        if (noteName.equals("E4")) {
            Glide.with(getContext()).load(R.drawable.guitar_auto_e4).into(imgGuitar2);
        }
    }

    private void handleE4(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 329.55 && pitchInHz <= 329.71) {
                countingWork();
            }
            if (pitchInHz < 329.55) {
                setIncompleteText(pitchInHz, 329);
            }
            if (pitchInHz > 329.71) {
                setExcessiveText(pitchInHz, 329);
            }
            if (pitchInHz <= 313.55) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 345.71) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleB3(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 246.86 && pitchInHz <= 247.02) {
                countingWork();
            }
            if (pitchInHz < 246.86) {
                setIncompleteText(pitchInHz, 247);
            }
            if (pitchInHz > 247.02) {
                setExcessiveText(pitchInHz, 247);
            }
            if (pitchInHz <= 230.86) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 263.02) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleG3(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 195.92 && pitchInHz <= 196.08) {
                countingWork();
            }
            if (pitchInHz < 195.92) {
                setIncompleteText(pitchInHz, 195);
            }
            if (pitchInHz > 196.08) {
                setExcessiveText(pitchInHz, 196);
            }
            if (pitchInHz <= 179.92) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 212.08) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleD3(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 146.75 && pitchInHz <= 146.91) {
                countingWork();
            }
            if (pitchInHz < 146.75) {
                setIncompleteText(pitchInHz, 146);
            }
            if (pitchInHz > 146.91) {
                setExcessiveText(pitchInHz, 147);
            }
            if (pitchInHz <= 130.75) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 162.91) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleA2(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 109.92 && pitchInHz <= 110.08) {
                countingWork();
            }
            if (pitchInHz < 109.92) {
                setIncompleteText(pitchInHz, 109);
            }
            if (pitchInHz > 110.08) {
                setExcessiveText(pitchInHz, 110);
            }
            if (pitchInHz <= 93.92) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 126.08) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleE2(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 82.33 && pitchInHz <= 82.49) {
                countingWork();
            }
            if (pitchInHz < 82.33) {
                setIncompleteText(pitchInHz, 82);
            }
            if (pitchInHz > 82.49) {
                setExcessiveText(pitchInHz, 83);
            }
            if (pitchInHz <= 66.33) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 98.49) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleE1(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 41.13 && pitchInHz <= 41.29) {
                countingWork();
            }
            if (pitchInHz < 41.13) {
                setIncompleteText(pitchInHz, 41);
            }
            if (pitchInHz > 41.29) {
                setExcessiveText(pitchInHz, 41);
            }
            if (pitchInHz <= 25.13) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 57.29) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleA1(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 54.92 && pitchInHz <= 55.08) {
                countingWork();
            }
            if (pitchInHz < 54.92) {
                setIncompleteText(pitchInHz, 54);
            }
            if (pitchInHz > 55.08) {
                setExcessiveText(pitchInHz, 55);
            }
            if (pitchInHz <= 38.92) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 71.08) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleD2(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 73.34 && pitchInHz <= 73.50) {
                countingWork();
            }
            if (pitchInHz < 73.34) {
                setIncompleteText(pitchInHz, 73);
            }
            if (pitchInHz > 73.50) {
                setExcessiveText(pitchInHz, 73);
            }
            if (pitchInHz <= 57.34) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 89.50) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleG2(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 97.92 && pitchInHz <= 98.08) {
                countingWork();
            }
            if (pitchInHz < 97.92) {
                setIncompleteText(pitchInHz, 97);
            }
            if (pitchInHz > 98.08) {
                setExcessiveText(pitchInHz, 98);
            }
            if (pitchInHz <= 81.92) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 114.08) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleB0(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 30.79 && pitchInHz <= 30.95) {
                countingWork();
            }
            if (pitchInHz < 30.79) {
                setIncompleteText(pitchInHz, 30);
            }
            if (pitchInHz > 30.95) {
                setExcessiveText(pitchInHz, 31);
            }
            if (pitchInHz <= 14.79) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 46.95) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void handleC3(float pitchInHz) {
        if (pitchInHz > 0) {
            if (pitchInHz >= 130.73 && pitchInHz <= 130.89) {
                countingWork();
            }
            if (pitchInHz < 130.73) {
                setIncompleteText(pitchInHz, 130);
            }
            if (pitchInHz > 130.89) {
                setExcessiveText(pitchInHz, 131);
            }
            if (pitchInHz <= 114.73) {
                setVisibility("low");
                invisibleText();
            }
            if (pitchInHz >= 146.89) {
                setVisibility("high");
                invisibleText();
            }
        } else {
            setVisibility("hideAll");
            invisibleText();
        }
    }

    private void countingWork() {
        invisibleText();
        checkTime();
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

    private void onListener() {
        onClickBtnSwitch();
        onClickNoteText();
        onClickGuitarText();
    }

    private void onClickGuitarText() {
        txtGuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lnGuitar.setVisibility(View.VISIBLE);
                        ViewAnimator.animate(lnGuitar).slideBottomIn().duration(500).start();
                    }
                }, 500);
            }
        });
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
        setImage("G3");
    }

    private void onClickD3() {
        notesSound.play(NotesSound.TypeSound.D3);
        setImage("D3");
    }

    private void onClickB3() {
        notesSound.play(NotesSound.TypeSound.B3);
        setImage("B3");
    }

    private void onClickA2() {
        notesSound.play(NotesSound.TypeSound.A2);
        setImage("A2");
    }

    private void onClickE4() {
        notesSound.play(NotesSound.TypeSound.E4);
        setImage("E4");
    }

    private void onClickE2() {
        notesSound.play(NotesSound.TypeSound.E2);
        setImage("E2");
    }

    private void onClickB0() {
        notesSound.play(NotesSound.TypeSound.B0);
        setImage("B0");
    }

    private void onClickE1() {
        notesSound.play(NotesSound.TypeSound.E1);
        setImage("E1");
    }

    private void onClickA1() {
        notesSound.play(NotesSound.TypeSound.A1);
        setImage("A1");
    }

    private void onClickD2() {
        notesSound.play(NotesSound.TypeSound.D2);
        setImage("D2");
    }

    private void onClickG2() {
        notesSound.play(NotesSound.TypeSound.G2);
        setImage("G2");
    }

    private void onClickC3() {
        notesSound.play(NotesSound.TypeSound.C3);
        setImage("C3");
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
            Toast.makeText(mActivity, "Auto is turned on!", Toast.LENGTH_SHORT).show();
        }
        if (type == 1) {
            Toast.makeText(mActivity, "Auto is turned off!", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchTurnedOn() {
        setTextBackground("ON");
        setImage(0);
        animatorInvisibleView();
        txtLastNote.setText("Last note received");
        txtNote.setText("-");
        checkThread();
        frequencySeeking();
    }

    private void switchTurnedOff() {
        setNone();
        setTextBackground("OFF");
        setImage(1);
        visibleView();
        animatorVisibleView();
        txtLastNote.setText("Note choosen");
        txtNote.setText("Tap to choose note");
        checkThread();
        frequencySeeking();
    }

    private void checkThread() {
        if (audioThread != null) {
            audioThread.interrupt();
            audioThread = null;
        }
    }

    private void animatorInvisibleView() {
        ViewAnimator.animate(imgGuitar2).fadeOut().duration(500).start();
        checkGuitarTypeForSwitch();
    }

    private void checkGuitarTypeForSwitch() {
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Classic")) {
            Glide.with(getContext()).load(R.drawable.guitar_change_classic).into(imgGuitar2);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_change_bass_4).into(imgGuitar2);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_change_bass_5).into(imgGuitar2);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_change_bass_6).into(imgGuitar2);
        }
    }

    private void animatorVisibleView() {
        ViewAnimator.animate(imgGuitar2).fadeIn().duration(500).start();
    }

    private void visibleView() {
        imgGuitar2.setVisibility(View.VISIBLE);
    }

    private void setImage(int type) {
        if (type == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getContext()).load(0).into(imgGuitar2);
                }
            }, 500);
        } else {
            checkGuitarTypeForSwitch();
        }
    }

    private void setImage(String TYPE) {
        if (TYPE.equals("E1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_e1_manual).into(imgGuitar2);
        }
        if (TYPE.equals("E1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_e1_manual).into(imgGuitar2);
        }
        if (TYPE.equals("E1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_e1_manual).into(imgGuitar2);
        }
        if (TYPE.equals("E2")) {
            Glide.with(getContext()).load(R.drawable.guitar_e2).into(imgGuitar2);
        }
        if (TYPE.equals("E4")) {
            Glide.with(getContext()).load(R.drawable.guitar_e4).into(imgGuitar2);
        }
        if (TYPE.equals("A1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_a1_manual).into(imgGuitar2);
        }
        if (TYPE.equals("A1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_a1_manual).into(imgGuitar2);
        }
        if (TYPE.equals("A1") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_a1_manual).into(imgGuitar2);
        }
        if (TYPE.equals("A2")) {
            Glide.with(getContext()).load(R.drawable.guitar_a2).into(imgGuitar2);
        }
        if (TYPE.equals("B0") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_b0_manual).into(imgGuitar2);
        }
        if (TYPE.equals("B0") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_b0_manual).into(imgGuitar2);
        }
        if (TYPE.equals("B3")) {
            Glide.with(getContext()).load(R.drawable.guitar_b3).into(imgGuitar2);
        }
        if (TYPE.equals("C3")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_c3_manual).into(imgGuitar2);
        }
        if (TYPE.equals("D2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_d2_manual).into(imgGuitar2);
        }
        if (TYPE.equals("D2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_d2_manual).into(imgGuitar2);
        }
        if (TYPE.equals("D2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_d2_manual).into(imgGuitar2);
        }
        if (TYPE.equals("D3")) {
            Glide.with(getContext()).load(R.drawable.guitar_d3).into(imgGuitar2);
        }
        if (TYPE.equals("G2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_g2_manual).into(imgGuitar2);
        }
        if (TYPE.equals("G2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_g2_manual).into(imgGuitar2);
        }
        if (TYPE.equals("G2") && Hawk.get(Key.GUITAR).equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_g2_manual).into(imgGuitar2);
        }
        if (TYPE.equals("G3")) {
            Glide.with(getContext()).load(R.drawable.guitar_g3).into(imgGuitar2);
        }
    }

    private void setImage() {
        Glide.with(getContext()).load(R.drawable.bg_1).into(imgBackground);
        setGuitarImages();
    }

    private void setGuitarImages() {
        Glide.with(getContext()).load(0).into(imgGuitar);
        Glide.with(getContext()).load(0).into(imgGuitar2);
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Classic")) {
            Glide.with(getContext()).load(R.drawable.guitar_auto_l).into(imgGuitar);
            Glide.with(getContext()).load(R.drawable.guitar_change_classic).into(imgGuitar2);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 4-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_4_auto).into(imgGuitar);
            Glide.with(getContext()).load(R.drawable.guitar_change_bass_4).into(imgGuitar2);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 5-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_5_auto).into(imgGuitar);
            Glide.with(getContext()).load(R.drawable.guitar_change_bass_5).into(imgGuitar2);
        }
        if (Hawk.get(Key.GUITAR, "Guitar Classic").equals("Guitar Bass 6-String")) {
            Glide.with(getContext()).load(R.drawable.guitar_bass_6_auto).into(imgGuitar);
            Glide.with(getContext()).load(R.drawable.guitar_change_bass_6).into(imgGuitar2);
        }
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
        txtGuitar = rootView.findViewById(R.id.txt_guitar_choosen);

        btnSwitch = rootView.findViewById(R.id.btn_switch);

        lnNote = rootView.findViewById(R.id.ln_note);
        lnGuitar = rootView.findViewById(R.id.ln_guitar);

        notesSound = new NotesSound(getContext());
    }

    private int WidthScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int HeightScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private void onSelectedClassicNote(int position) {
        for (int i = 0; i < itemNotes.size(); i++) {
            itemNotes.get(i).noteChoice(false);
        }
        itemNotes.get(position).noteChoice(true);
    }

    private void onSelectedGuitar(int position) {
        for (int i = 0; i < itemGuitars.size(); i++) {
            itemGuitars.get(i).guitarChoice(false);
        }
        itemGuitars.get(position).guitarChoice(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    public void onPause() {
        checkThread();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        frequencySeeking();
    }
}
