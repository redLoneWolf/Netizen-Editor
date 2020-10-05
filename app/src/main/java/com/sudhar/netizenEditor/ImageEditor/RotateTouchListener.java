package com.sudhar.netizenEditor.ImageEditor;


import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class RotateTouchListener implements View.OnTouchListener{
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout layBg;
    private int pivx=0;
    private int pivy=0;
    private int pos=0;
    private float startDegree=0;
    private int basex = 0;
    private int basey = 0;
    private View rootview,childview;

    public RotateTouchListener(View rootview,View childview) {
//        this.ctx = ctx;
//        this.mDrawableBackground = mDrawableBackground;
//        mGestureListener = new GestureDetector(ctx,new DragTouchListener.GestureListener());
        this.rootview = rootview;
        this.childview = childview;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        layoutParams = (RelativeLayout.LayoutParams) rootview.getLayoutParams();
        layBg = ((RelativeLayout) rootview.getParent());
        int[] arrayOfInt = new int[2];
        layBg.getLocationOnScreen(arrayOfInt);
        int i = (int) event.getRawX() - arrayOfInt[0];
        int j = (int) event.getRawY() - arrayOfInt[1];
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                startDegree = rootview.getRotation();
                pivx = (layoutParams.leftMargin + rootview.getWidth() / 2);
                pivy = (layoutParams.topMargin + rootview.getHeight() / 2);
                basex = (i - pivx);
                basey = (pivy - j);
                break;

            case MotionEvent.ACTION_MOVE:
                int k = pivx;
                int m = pivy;
                j = (int) (Math.toDegrees(Math.atan2(basey, basex))
                        - Math.toDegrees(Math.atan2(m - j, i - k)));
                i = j;
                if (j < 0) {
                    i = j + 360;
                }
                rootview.setRotation((startDegree + i) % 360.0F);
                break;
        }

        return true;
    }
}
