package com.polarb.android.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.polarb.android.R;

public class Triangle extends View {
    private String position = null;
    private int color;

    public Triangle(Context context) {
        super(context);
    }

    public Triangle(Context context, AttributeSet attrs) {
        super(context, attrs);

        setStyledAttributes(context, attrs);
    }

    public Triangle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setStyledAttributes(context, attrs);
    }

    private void setStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Triangle);

        if (a.getString(R.styleable.Triangle_position) != null && a.getString(R.styleable.Triangle_position).equals("right")){
            this.position = a.getString(R.styleable.Triangle_position);
        }

        this.color = a.getColor(R.styleable.Triangle_color, Color.BLACK);

        a.recycle();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        getTrianglePath();

        canvas.drawPath(getTrianglePath(), getTrianglePaint());
    }

    private Paint getTrianglePaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        paint.setColor(color);

        return paint;
    }
    private Path getTrianglePath() {
        Path path = new Path();

        if (position != null && position.equals("right")){
            path.lineTo(getWidth(), 0);
            path.lineTo(0, getHeight());
        } else {
            path.lineTo(getWidth(), 0);
            path.lineTo(getWidth(), getHeight());
        }

        return path;
    }
}
