package com.pdc.metronome.item;

public class BeatItems {
    private String txtBeat;
    private int imgChecked;

    public BeatItems(String txtBeat, int imgChecked) {
        this.txtBeat = txtBeat;
        this.imgChecked = imgChecked;
    }

    public String getTxtBeat() {
        return txtBeat;
    }

    public int getImgChecked() {
        return imgChecked;
    }

    public void setImgChecked(int imgChecked) {
        this.imgChecked = imgChecked;
    }
}
