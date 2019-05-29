package com.pdc.metronome.item;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.pdc.metronome.R;
import com.pdc.metronome.item.Indicator;

public class SoundManager {
    private SoundPool soundPool;
    private int idFirst;
    private Context context;
    private int idLoop;

    public enum TypeSound {
        FIRST, LOOP
    }

    public SoundManager(Context context) {
        this.context = context;
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        loadId();
    }

    private void loadId() {
        idFirst = soundPool.load(context, R.raw.metronome_first, 1);
        idLoop = soundPool.load(context, R.raw.metronome_loop, 1);
    }

    public void play(TypeSound type) {
        if (type == TypeSound.FIRST) {
            soundPool.play(idFirst, 1, 1, 1, 0, 1F);
        } else {
            soundPool.play(idLoop, 1, 1, 1, 0, 1F);
        }
    }
}
