package com.sudhar.netizenEditor.ImageEditor;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class ResizeTouchListener implements View.OnTouchListener {
    private RelativeLayout.LayoutParams layoutParams;
    private int basex = 0;
    private int basey = 0;
    private int basew = 0;
    private int baseh = 0;
    private int margl;
    private int margt;
    private View rootview;


    public ResizeTouchListener(View rootview) {

        this.rootview = rootview;
//        this.type = type;
//        if (type == Type.TEXT){
//            this.textView = (TextView) childview;
//        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

//            layoutParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        layoutParams = (RelativeLayout.LayoutParams) rootview.getLayoutParams();

        int j = (int) event.getRawX();
        int i = (int) event.getRawY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                basex = j;
                basey = i;
                basew = rootview.getWidth();
                baseh = rootview.getHeight();
                int[] loaction = new int[2];
                rootview.getLocationOnScreen(loaction);
                margl = layoutParams.leftMargin;
                margt = layoutParams.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:

                float f2 = (float) Math.toDegrees(Math.atan2(i - basey, j - basex));
                float f1 = f2;
                if (f2 < 0.0F) {
                    f1 = f2 + 360.0F;
                }
                j -= basex;
                int k = i - basey;
                i = (int) (Math.sqrt(j * j + k * k)
                        * Math.cos(Math.toRadians(f1 - rootview.getRotation())));
                j = (int) (Math.sqrt(i * i + k * k)
                        * Math.sin(Math.toRadians(f1 - rootview.getRotation())));
                k = i * 2 + basew;
                int m = j * 2 + baseh;
                if (k > 150) {
                    layoutParams.width = k;
                    layoutParams.leftMargin = (margl - i);
                }
                if (m > 150) {
                    layoutParams.height = m;
                    layoutParams.topMargin = (margt - j);
                }
//                            textInputTv.setLayoutParams(layoutParams);



//                if (type == Type.TEXT){
//                    textView.setHeight(layoutParams.height );
//                    textView.setWidth(layoutParams.width );
//                    textView.setLeft(layoutParams.leftMargin );
//                    textView.setTop(layoutParams.topMargin );
//                }
//                else if(type == Type.IMAGE){
//                    imgView.setS
                    rootview.setLayoutParams(layoutParams);
//                }

                break;
        }
        return true;
    }
}
