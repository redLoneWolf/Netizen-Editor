package com.sudhar.netizenEditor.ImageEditor;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class DragTouchListener implements View.OnTouchListener{
    private float dX;
    private float dY;
    private int lastAction;
    private float xp,yp;

    GestureDetector mGestureListener;
    private Context context;

    public DragTouchListener(Context context) {
        this.context = context;

        mGestureListener = new GestureDetector(context,new GestureListener());
    }



    public boolean onTouch(View v, MotionEvent event) {
        mGestureListener.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                dX = v.getX() - event.getRawX();
                dY = v.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:


                    v.setY(event.getRawY() + dY);
                    v.setX(event.getRawX() + dX);
                    xp = event.getRawX()+ dX;
                    yp = event.getRawY()+ dY;



                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN){


                }
                break;

            default:
                return false;
        }

        return true;
    }

    interface OnGestureControl {
        void onClick();

        void onLongClick();

        void onDoubleClick();
    }

    private OnGestureControl mOnGestureControl;

    void setOnGestureControl(OnGestureControl onGestureControl) {
        mOnGestureControl = onGestureControl;
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mOnGestureControl != null) {
                mOnGestureControl.onClick();
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            if (mOnGestureControl != null) {
                mOnGestureControl.onLongClick();
            }
        }
        @Override
        public boolean onDoubleTap(MotionEvent e){
            if (mOnGestureControl != null) {
                mOnGestureControl.onDoubleClick();
            }

            return true;
        }
    }



}
