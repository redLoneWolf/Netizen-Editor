package com.sudhar.netizenEditor.ImageEditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;

public class BackgroundEraseView extends androidx.appcompat.widget.AppCompatImageView {

        private Bitmap mSourceBitmap;
        private  Bitmap rawBitmap;
        private Canvas mSourceCanvas = new Canvas();
        private Paint mDestPaint = new Paint();
        private Path mDestPath = new Path();

        public BackgroundEraseView(Context context)
        {
            super(context);

            //convert drawable file into bitmap
            setScaleType(ScaleType.FIT_CENTER);

            //convert bitmap into mutable bitmap

        }

    public void setRawBitmap(Bitmap rawBitmap) {
        this.rawBitmap = rawBitmap;
//        mSourceBitmap = Bitmap.createBitmap(rawBitmap.getWidth(), rawBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        setImageBitmap(rawBitmap);



//        Rect dstRect = new Rect();
//        mSourceCanvas.getClipBounds(dstRect); // get canvas size base screen size
//
//
//
//        mSourceCanvas.setBitmap(mSourceBitmap);
//        mSourceCanvas.drawBitmap(rawBitmap, null, dstRect, null);
//        mSourceCanvas.drawBitmap(rawBitmap, 100, 50, null);

        mDestPaint.setAlpha(0);
        mDestPaint.setAntiAlias(true);
        mDestPaint.setStyle(Paint.Style.STROKE);
        mDestPaint.setStrokeJoin(Paint.Join.ROUND);
        mDestPaint.setStrokeCap(Paint.Cap.ROUND);
        mDestPaint.setStrokeWidth(50);
        mDestPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
        protected void onDraw(Canvas canvas)
        {
            //Draw path
//            canvas.drawPath(mDestPath, mDestPaint);
            mSourceCanvas = canvas;
            //Draw bitmap
//            canvas.drawBitmap(mSourceBitmap, 0, 0, null);

            super.onDraw(canvas);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float xPos = event.getX();
            float yPos = event.getY();

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    mDestPath.moveTo(xPos, yPos);
                    break;

                case MotionEvent.ACTION_MOVE:
                    mDestPath.lineTo(xPos, yPos);
                    break;
                case MotionEvent.ACTION_UP:
                    mSourceCanvas.drawPath(mDestPath, mDestPaint);
                    mDestPath.reset();
                    break;

                default:
                    return false;
            }

            invalidate();
            return true;
        }

}
