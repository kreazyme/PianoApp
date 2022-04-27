package com.example.piano;

import android.graphics.RectF;

public class Key {
    public int sound;

    public Key(int sound, RectF rectF) {
        this.sound = sound;
        this.rectF = rectF;
    }

    public RectF rectF;
    public boolean down;
}
