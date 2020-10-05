package com.sudhar.netizenEditor.ImageEditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.sudhar.netizenEditor.R;


public class StrokeTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final int DEFAULT_STROKE_WIDTH = 0;

    private int _strokeColor;
    private float _strokeWidth;
    private boolean isDrawing;

    public StrokeTextView(Context context) {
        super(context);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if(attrs != null) {
            @SuppressLint("CustomViewStyleable")
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextAttrs);
            _strokeColor = a.getColor(R.styleable.StrokeTextAttrs_StrokeColor,
                    getCurrentTextColor());
            _strokeWidth = a.getFloat(R.styleable.StrokeTextAttrs_StrokeWidth,
                    DEFAULT_STROKE_WIDTH);

            a.recycle();
        } else {
            _strokeColor = getCurrentTextColor();
            _strokeWidth = DEFAULT_STROKE_WIDTH;
        }
        setStrokeWidth(_strokeWidth);
    }

    public void setStrokeWidth(float width) {
        setText(getText());

        setTextColor(getTextColors());

        _strokeWidth = spToPx(getContext(), width);
    }
    public void setStrokeWidth(int unit, float width) {
        _strokeWidth = TypedValue.applyDimension(
                unit, width, getContext().getResources().getDisplayMetrics());
    }

    public int getStrokeColor() {
        return _strokeColor;
    }

    public float getStrokeWidth() {
        return _strokeWidth;
    }

    @Override
    public void invalidate() {

        if(isDrawing) return;
        super.invalidate();
    }
    public void setStrokeColor(int color) {
        setText(getText());
        setTextColor(getTextColors());

        _strokeColor = color;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(_strokeWidth > 0) {
            isDrawing = true;
            //set paint to fill mode
            Paint p = getPaint();
            p.setStyle(Paint.Style.FILL);
            //draw the fill part of text
            super.onDraw(canvas);
            //save the text color
            int currentTextColor = getCurrentTextColor();
            //set paint to stroke mode and specify
            //stroke color and width
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(_strokeWidth);
            setTextColor(_strokeColor);
            //draw text stroke
            super.onDraw(canvas);
            //revert the color back to the one
            //initially specified
            setTextColor(currentTextColor);
            isDrawing = false;

//            int textColor = getTextColors().getDefaultColor();
//            setTextColor(Color.BLACK);
//            getPaint().setStrokeWidth(10);
//            getPaint().setStyle(Paint.Style.STROKE);
//            super.onDraw(canvas);
//            setTextColor(textColor);
//            getPaint().setStrokeWidth(0);
//            getPaint().setStyle(Paint.Style.FILL);
//            super.onDraw(canvas);


        } else {
            super.onDraw(canvas);
        }
    }
    private static int spToPx(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }


}
