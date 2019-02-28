package com.pdc.metronome.adapter.entity;

public class MetronomeItems {
    private int btnSetting;
    private int btnPlay;
    private int btnPause;
    private int btnRotate;

    public MetronomeItems(int btnSetting, int btnPlay, int btnPause, int btnRotate) {
        this.btnSetting = btnSetting;
        this.btnPlay = btnPlay;
        this.btnPause = btnPause;
        this.btnRotate = btnRotate;
    }

    public int getBtnSetting() {
        return btnSetting;
    }

    public int getBtnPlay() {
        return btnPlay;
    }

    public int getBtnPause() {
        return btnPause;
    }

    public int getBtnRotate() {
        return btnRotate;
    }
}
