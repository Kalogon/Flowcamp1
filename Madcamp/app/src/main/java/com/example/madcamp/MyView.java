package com.example.madcamp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

public class MyView extends TextureView implements View.OnTouchListener{
    public MyView(Context context){
        super(context);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        Canvas c = lockCanvas();
        c.drawColor( Color.WHITE);
        Paint p = new Paint();
        p.setStyle( Paint.Style.FILL );
        p.setColor( Color.BLUE);
        c.drawCircle( e.getX(),e.getY(),100,p);
        unlockCanvasAndPost(c);
        return false;
    }
}
