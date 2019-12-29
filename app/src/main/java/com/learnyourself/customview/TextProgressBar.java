package com.learnyourself.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.learnyourself.grewords.R;

/**
 * Created by Nha on 12/13/2015.
 */
public class TextProgressBar extends ProgressBar{
    private String text;
    private Paint textPaint;
    Rect bound;

    public TextProgressBar(Context context) {
        super(context);
        text = "0/100";
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        bound = new Rect();
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        text = "0/100";
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        bound = new Rect();
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        text = "0/100";
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        bound = new Rect();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textPaint.getTextBounds(text, 0, text.length(), bound);
        textPaint.setTextSize((float)(getHeight()*0.65));
        textPaint.setStrokeWidth(1.5f);
        int x = getWidth() / 2 - bound.centerX();
        int y = getHeight() / 2 - bound.centerY();
        canvas.drawText(text, x, y, textPaint);
    }

    public synchronized void setText(String text) {
        this.text = text;
        drawableStateChanged();
    }

    public void setTextColor(int color) {
        textPaint.setColor(color);
        drawableStateChanged();
    }
}
