package com.pdc.metronome.item;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.pdc.metronome.R;

public class NotesSound {

    private SoundPool soundPool;
    private Context context;

    private int idE1;
    private int idE2;
    private int idE4;
    private int idA1;
    private int idA2;
    private int idB0;
    private int idB3;
    private int idC3;
    private int idD2;
    private int idD3;
    private int idG2;
    private int idG3;
    private int idCorrect;

    public enum TypeSound {
        E1, E2, E4, A1, A2, B0, B3, C3, D2, D3, G2, G3, CORRECT
    }

    public NotesSound(Context context) {
        this.context = context;
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        loadId();
    }

    private void loadId() {
        idE1 = soundPool.load(context, R.raw.bass_e1, 1);
        idE2 = soundPool.load(context, R.raw.e2, 1);
        idE4 = soundPool.load(context, R.raw.e4, 1);
        idA1 = soundPool.load(context, R.raw.bass_a1, 1);
        idA2 = soundPool.load(context, R.raw.a2, 1);
        idB0 = soundPool.load(context, R.raw.bass_b0, 1);
        idB3 = soundPool.load(context, R.raw.b3, 1);
        idC3 = soundPool.load(context, R.raw.bass_c3, 1);
        idD2 = soundPool.load(context, R.raw.bass_d2, 1);
        idD3 = soundPool.load(context, R.raw.d3, 1);
        idG2 = soundPool.load(context, R.raw.bass_g2, 1);
        idG3 = soundPool.load(context, R.raw.g3, 1);
        idCorrect = soundPool.load(context, R.raw.correct_sound, 1);
    }

    public void play(TypeSound type) {
        if (type == TypeSound.E1) {
            soundPool.play(idE1, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.E2) {
            soundPool.play(idE2, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.E4) {
            soundPool.play(idE4, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.A1) {
            soundPool.play(idA1, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.A2) {
            soundPool.play(idA2, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.B0) {
            soundPool.play(idB0, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.B3) {
            soundPool.play(idB3, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.C3) {
            soundPool.play(idC3, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.D2) {
            soundPool.play(idD2, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.D3) {
            soundPool.play(idD3, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.G2) {
            soundPool.play(idG2, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.G3) {
            soundPool.play(idG3, 1, 1, 1, 0, 1F);
        }
        if (type == TypeSound.CORRECT) {
            soundPool.play(idCorrect, 0.2f, 0.2f, 1, 0, 1F);
        }
    }
}
