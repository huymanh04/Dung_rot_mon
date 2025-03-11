package com.example.dung_rot_mon.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class CustomImageView extends AppCompatImageView {
    private Path path;
    private float radius = 50f; // Điều chỉnh độ cắt góc

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();

        RectF rect = new RectF(0, 0, w, h);

        // Cắt góc trên trái
        path.moveTo(40, 0);
        path.lineTo(0, 40);
        path.lineTo(0, h);
        path.lineTo(w, h);
        path.lineTo(w, 0);
        path.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}