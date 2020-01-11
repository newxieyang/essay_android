package com.cullen.tatu.view.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.cullen.tatu.R;

public class LineEditText extends AppCompatEditText {

    private Paint mPaint;
    private Rect rect;
    private static final int MARGIN = 10;
    public LineEditText(Context context) {
        super(context);
        mPaint = new Paint();
        rect = new Rect();
    }


    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        rect = new Rect();


        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineEditText);
        int color = array.getColor(R.styleable.LineEditText_lineColor,Color.BLACK);
        float lineWidth = array.getDimension(R.styleable.LineEditText_lineWidth,1);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(lineWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Rect r = rect;
        Paint paint = mPaint;
        Layout layout = getLayout();
        if (!canvas.getClipBounds(r)) {
            return;
        }
        float startX = r.left + MARGIN, stopX = r.right - MARGIN;
        int count = layout.getLineCount();
        float size = this.getTextSize();
        int lineHeight = getLineHeight();
        int height = getHeight() - getPaddingBottom() - getPaddingTop();
        int n = height % lineHeight == 0 ? height / lineHeight : height / lineHeight + 1;
        if (count < n) {
            count = n;
        }
        //float pt = this.getLineSpacingExtra() /2;
        float pt = size / 6;
        for (int i = 1; i <= count; i++) {
            int y = (int) (lineHeight * i + pt);
            canvas.drawLine(startX, y , stopX, y, paint);
        }
    }

}
