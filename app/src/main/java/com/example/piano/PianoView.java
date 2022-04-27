package com.example.piano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PianoView extends View {

    public static final int NUMBER_OF_KEY = 14;
    private Paint black, yellow, white, blackLine;
    private ArrayList<Key> whites, blacks;
    private int keyHeigt, keyWidth;

    private SoundManager soundManager;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        keyWidth = w/NUMBER_OF_KEY;
        keyHeigt = h;
        int count = 15;
        soundManager = SoundManager.getInstance();
        soundManager.init(getContext());

        for(int i = 0; i < NUMBER_OF_KEY; i++){
            int left = i * keyWidth;
            int right = left + keyWidth;
            RectF rectF = new RectF(left, 0, right, h);
            whites.add(new Key(i+1, rectF));


            //init black key
            if( i!= 0 && i != 3 && i != 7 && i != 10){
                rectF = new RectF((float)(i-1)*keyWidth + 0.75f*keyWidth,
                        (float) 0,
                        (float) i*keyWidth + 0.25f*keyWidth,
                        (float) 0.67*keyHeigt
                        );
                blacks.add(new Key(count, rectF));
                count++;
            }

        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Key k:whites){
            canvas.drawRect(k.rectF, k.down?yellow:white);
        }
        for (Key k:blacks){
            canvas.drawRect(k.rectF, k.down?yellow:black);
        }
        for(int i =1; i < NUMBER_OF_KEY; i++){
            canvas.drawLine(i*keyWidth, 0, i*keyWidth, keyHeigt, blackLine);
        }

    }

    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        blackLine = new Paint();
        blackLine.setColor(Color.BLACK);
        blackLine.setStrokeWidth(3);

        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);

        whites = new ArrayList<Key>();
        blacks = new ArrayList<Key>();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        boolean isdown = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;

        int index = 0;
        while(index < event.getPointerCount()){
            float x= event.getX(index);
            float y = event.getY(index);
            boolean blackdown = false;
            for(Key k:blacks){
                if(k.rectF.contains(x,y)){
                    k.down = isdown;
                    blackdown = true;
                }
                else {
                    k.down = false;
                }
            }

            //init isdown event
            for(Key k:whites){
                if(k.rectF.contains(x,y) && blackdown == false){
                    k.down = isdown;
                    switch (k.sound){
                        case 1:
                            soundManager.playSound(R.raw.a2);
                            break;
                        case 2:
                            soundManager.playSound(R.raw.b2);
                            break;
                        case 3:
                            soundManager.playSound(R.raw.c2);
                            break;
                        case 4:
                            soundManager.playSound(R.raw.d2);
                            break;
                        case 5:
                            soundManager.playSound(R.raw.f2);
                            break;
                        case 6:
                            soundManager.playSound(R.raw.g2);
                            break;
                        case 7:
                            soundManager.playSound(R.raw.a3);
                            break;
                        case 8:
                            soundManager.playSound(R.raw.b3);
                            break;
                        case 9:
                            soundManager.playSound(R.raw.c3);
                            break;
                        case 10:
                            soundManager.playSound(R.raw.d3);
                            break;
                        case 11:
                            soundManager.playSound(R.raw.f3);
                            break;
                        case 12:
                            soundManager.playSound(R.raw.g3);
                            break;
                        case 13:
                            soundManager.playSound(R.raw.g3);
                            break;
                        case 14:
                            soundManager.playSound(R.raw.db3);
                            break;
                    }
                }
                else {
                    k.down = false;
                }
            }
            index++;

//            for(Key k:whites){
//                if(k.down == true){
//
//                }
//            }

        }

        //repaint
        invalidate();
        return true;
    }
}
