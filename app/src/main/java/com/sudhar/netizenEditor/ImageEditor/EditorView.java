package com.sudhar.netizenEditor.ImageEditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

public class EditorView extends RelativeLayout {
    private BrushDrawingView mBrushDrawingView;
    ImageView imageView;

    int brushSrcId =3;
    int imgSrcId = 1;
    private static final String TAG = "EditorView";

    public EditorView(Context context) {

        super(context);

        init(null);
    }

    public EditorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public EditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public EditorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }
    @SuppressLint("ResourceType")
    public void init(@Nullable AttributeSet attrs){

        imageView = new ImageView(getContext());


        mBrushDrawingView = new BrushDrawingView(getContext());

//        mBrushDrawingView = new BrushDrawingView(ctx);


    }

    public   BrushDrawingView getBrushDrawingView() {
        return mBrushDrawingView;
    }


    public void setImage(Bitmap bmp){
        imageView.setImageBitmap(bmp);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.CENTER;

        imageView.setLayoutParams(params);
        imageView.setId(imgSrcId);
        imageView.setAdjustViewBounds(true);





//        addbrushview();

        mBrushDrawingView.setVisibility(GONE);

        mBrushDrawingView.setId(brushSrcId);

        RelativeLayout.LayoutParams bparams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bparams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        bparams.addRule(RelativeLayout.ALIGN_TOP, imgSrcId);
        bparams.addRule(RelativeLayout.ALIGN_BOTTOM, imgSrcId);

        addView(imageView,params);
        addView(mBrushDrawingView,bparams);
    }



    public Bitmap getImage(){
                Bitmap image=((BitmapDrawable)imageView.getDrawable()).getBitmap();
             return image;
    }

    public ImageView getImageView(){
        return imageView;
    }


    public void rotateImageLeft(){

        imageView.setRotation(imageView.getRotation()+270);
    }

    public void rotateImageRight(){
        imageView.setRotation(imageView.getRotation()+90);


    }


    public void flipImageHorizontal(){

        imageView.setScaleX(imageView.getScaleX()*-1);
    }

    public void flipImageVertical(){

        imageView.setScaleY(imageView.getScaleY()*-1);
    }



}
