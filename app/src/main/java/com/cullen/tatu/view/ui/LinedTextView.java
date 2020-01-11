package com.cullen.tatu.view.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.cullen.tatu.R;


public class LinedTextView extends AppCompatTextView {

    private Paint mPaint;

    public LinedTextView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public LinedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineEditText);
        int color = array.getColor(R.styleable.LineEditText_lineColor,Color.BLACK);
        float lineWidth = array.getDimension(R.styleable.LineEditText_lineWidth,1);

        mPaint.setColor(color);
        mPaint.setStrokeWidth(lineWidth);
    }



    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.BLACK);

        int right = getRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int height = getHeight();
        int lineHeight = getLineHeight();
        int spacingHeight = (int) getLineSpacingExtra();
        height = height + spacingHeight;//把最后一个行间距也计算进去
        int count = (height - paddingTop - paddingBottom) / lineHeight;


        for (int i = 0; i < count; i++) {
            int baseline = lineHeight * (i + 1) + paddingTop - spacingHeight / 2;
            canvas.drawLine(0 + paddingLeft, baseline, right - paddingRight, baseline, mPaint);
        }
        super.onDraw(canvas);
    }


}
