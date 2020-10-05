package com.sudhar.netizenEditor.ImageEditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.sudhar.netizenEditor.R;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditTools {
    private List<View> addedViews;
    private List<View> removedViews;
    LayoutInflater mInflater;
    EditorView mEditorView;
    Context ctx;
    Drawable mDrawableBackground;
    BrushDrawingView drawingView;
    int TextColor,TextBgColor;
    int StrokeColor;
    int TextGravity;
    float StrokeWidth,Textsize;
    private StrokeTextView currentTextView;
    final String TAG = "EditTools";
    private View formatBtn;
    private View rootView;
    private Typeface font;
    private int fontStyle;
    public EditTools(Context ctx, EditorView mEditorView,Drawable mDrawableBackground,BrushDrawingView drawingView){
        this.mEditorView = mEditorView;
        this.ctx = ctx;
        this.mInflater = LayoutInflater.from(ctx);
        this.mDrawableBackground = mDrawableBackground;

        this.drawingView = drawingView;
        addedViews = new ArrayList<>();
        removedViews = new ArrayList<>();

        rootView = ((Activity)ctx).getWindow().getDecorView().findViewById(android.R.id.content);

//        View v = rootView.findViewById(R.id.your_view_id);
         formatBtn =(View) rootView.findViewById(R.id.formatBtn);
        setDefaults();
    }

    public Typeface getFont() {
        return font;
    }

    public int getTextGravity() {
        return TextGravity;
    }

    public void setTextGravity(int textGravity) {
        TextGravity = textGravity;
        if(currentTextView!=null){

            currentTextView.setGravity(textGravity);
        }
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(Typeface font,int fontStyle) {
        this.fontStyle = fontStyle;
        if(currentTextView!=null){

            currentTextView.setTypeface(font,fontStyle);
        }
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public void setFont(Typeface font) {
        this.font = font;
    }

    public float getTextsize() {
        return Textsize;
    }

    public void setTextSize(float textSize) {
        Textsize = textSize;
        if(currentTextView!=null){

            currentTextView.setTextSize(textSize);
        }
    }

    public int getTextColor() {
        return TextColor;
    }
    public void setTextColor(int textColor) {
        TextColor = textColor;
        if(currentTextView!=null){

            currentTextView.setTextColor(textColor);
        }

    }

    public int getTextBgColor() {
        return TextBgColor;
    }

    public void setTextBgColor(int textBgColor) {
        TextBgColor = textBgColor;
        if(currentTextView!=null){

            currentTextView.setBackgroundColor(textBgColor);
        }

    }

    public int getStrokeColor() {
        return StrokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        StrokeColor = strokeColor;
        if(currentTextView!=null){
            currentTextView.setStrokeColor(strokeColor);
        }
    }

    public float getStrokeWidth() {
        return StrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        StrokeWidth = strokeWidth;
        if(currentTextView!=null){
            currentTextView.setStrokeWidth(strokeWidth);
        }
    }



    void setDefaults(){
        TextColor = Color.BLACK;
        TextGravity = Gravity.CENTER;
        TextBgColor = 0;
        StrokeColor = 0;
        StrokeWidth = 0;
    }

    private void addViewToParent(View rootView, Type type) {


        if (type== Type.CROP){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mEditorView.removeAllViews();
            mEditorView.addView(rootView,params);
        }else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mEditorView.addView(rootView,params);
            addedViews.add(rootView);
        }


    }


    private void setCurrentTextView(StrokeTextView tv){
        int color=0;
        if(tv != null){
            currentTextView = tv;
            if(((ColorDrawable)tv.getBackground())!=null){

                color = ((ColorDrawable) tv.getBackground()).getColor();
            }

            setProps(tv.getTypeface(),tv.getGravity(),tv.getCurrentTextColor(),color
                    ,tv.getStrokeColor(),tv.getStrokeWidth());
        }


    }
    private TextView getCurrentTextView(){
        return currentTextView ;
    }

    private View getLayout(Type type) {
        View rootView = null;
        switch (type) {
            case TEXT: {
                rootView = mInflater.inflate(R.layout.text_res, null);
                TextView textInputTv = rootView.findViewById(R.id.plain_text_input);
                textInputTv.setGravity(Gravity.CENTER);
                Log.d(TAG, "Text Layout");
                break;
            }
            case IMAGE: {
                rootView = mInflater.inflate(R.layout.img_res, null);

                Log.d(TAG, "Image Layout");
                break;
            }
            case CROP: {
                rootView = mInflater.inflate(R.layout.crop_res, null);

                Log.d(TAG, "Crop Layout");
                break;
            }

        }
        return rootView;
    }


private void setProps(Typeface font,  int TextGravity,int TextColor,int TextBgColor, int StrokeColor, float StrokeWidth ){
        this.font = font;
        this.fontStyle = fontStyle;
        this.TextColor =TextColor;
        this.TextBgColor =TextBgColor;
        this.StrokeColor = StrokeColor;
        this.StrokeWidth = StrokeWidth;
}

    public void addText() {
        setDrawingMode(false);
        final View textRootView = getLayout(Type.TEXT);
        final StrokeTextView textInputTv = textRootView.findViewById(R.id.plain_text_input);
        final ImageView imgClose = textRootView.findViewById(R.id.imgPhotoEditorClose);
        final ImageView resize = textRootView.findViewById(R.id.resizeBtn);
        final ImageView rotate = textRootView.findViewById(R.id.rotateBtn);
        final FrameLayout frmBorder = textRootView.findViewById(R.id.frmBorder);




        textInputTv.setHint("Your Text");




        textInputTv.setTextColor(TextColor);
        textInputTv.setGravity(TextGravity);
        textInputTv.setTypeface(font,fontStyle);

        textInputTv.setStrokeColor(StrokeColor);
        textInputTv.setStrokeWidth(StrokeWidth);



        if(TextBgColor!=0){

            textInputTv.setBackgroundColor(TextBgColor);
        }
        setCurrentTextView(textInputTv);
        showEditDialog(textInputTv);

        textInputTv.setTextSize(25);
        textInputTv.setAutoSizeTextTypeUniformWithConfiguration(
                1, 200, 1, TypedValue.COMPLEX_UNIT_DIP);
        Log.d(TAG,"Adding Text");


        rotate.setOnTouchListener(new RotateTouchListener(textRootView,textInputTv));

        resize.setOnTouchListener(new ResizeTouchListener(textRootView));




        imgClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ctx, "Remove", Toast.LENGTH_SHORT).show();

                mEditorView.removeView(textRootView);
                addedViews.remove(textRootView);
                removedViews.add(textRootView);
            }
        });

        DragTouchListener tl = new DragTouchListener(ctx);

        tl.setOnGestureControl(new DragTouchListener.OnGestureControl() {
            @Override
            public void onClick() {
                setDrawingMode(false);
                removeDrawableBackground();
                final boolean isBackgroundVisible = frmBorder.getTag() != null && (boolean) frmBorder.getTag();
                frmBorder.setBackgroundResource(isBackgroundVisible ? 0 : R.drawable.edit_text_background);
                imgClose.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                resize.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                rotate.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                formatBtn.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);

                setCurrentTextView(isBackgroundVisible ? null  : textInputTv);


                frmBorder.setTag(!isBackgroundVisible);



            }

            @Override
            public void onLongClick() { }

            @Override
            public void onDoubleClick() {
                showEditDialog(textInputTv);
            }
        });


        textRootView.setOnTouchListener(tl);



        addViewToParent(textRootView,Type.TEXT);

    }

    public void crop(Bitmap bmp) {

        drawingView.clearAll();

        final View cropRootView = getLayout(Type.CROP);

        CropImageView cropView = cropRootView.findViewById(R.id.cropImageView);

        cropView.setImageBitmap(bmp);


        addViewToParent(cropView,Type.CROP);
    }


    void setDrawingMode(boolean hi){
    //    drawingView.setupBrushDrawing();
        drawingView.setBrushDrawingMode(hi);
        ImageButton undoBtn = rootView.findViewById(R.id.undoBtn);
        ImageButton redoBtn = rootView.findViewById(R.id.redoBtn);
        ImageButton tickBtn = rootView.findViewById(R.id.tickBtn);
        tickBtn.setVisibility(hi?View.VISIBLE:View.GONE);
        undoBtn.setVisibility(hi?View.VISIBLE:View.GONE);
        redoBtn.setVisibility(hi?View.VISIBLE:View.GONE);

    }

    void brushUndo(){
            drawingView.undo();
    }

    void brushRedo(){
        drawingView.redo();
    }


    @SuppressLint("WrongConstant")
    public void editText(TextView tv, String str){
        setDrawingMode(false);
        tv.setText(str);
//        tv.setTextSize(45);
//        tv.setTextColor(Color.RED);
//        Typeface typeface = ResourcesCompat.getFont(ctx, R.font.pangolin_regular);
//        tv.setTypeface(typeface);


    }

    public void addImage(String desiredImage,Type type) {
        setDrawingMode(false);
        Bitmap bmp,bmp1=null;

        File imgFile = new  File(desiredImage);

        if(imgFile.exists()){

            bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            android.graphics.Bitmap.Config bitmapConfig = bmp.getConfig();
            if(bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            bmp1 = bmp.copy(bitmapConfig, true);

        }else {

            Toast.makeText(ctx, " Image Not Found ", Toast.LENGTH_SHORT).show();
            return;

        }


        final View imageRootView = getLayout(Type.IMAGE);
        final ImageView imageView = imageRootView.findViewById(R.id.imgEditorImage);
        final FrameLayout frmBorder = imageRootView.findViewById(R.id.frmBorder);
        final ImageView imgClose = imageRootView.findViewById(R.id.imgPhotoEditorClose);
        final ImageView resize = imageRootView.findViewById(R.id.resizeBtn);
        final ImageView rotate = imageRootView.findViewById(R.id.rotateBtn);

        imageView.setImageBitmap(bmp1);

        if(type !=Type.STICKER){

            ImageInsertFragment imageInsertFragment = new ImageInsertFragment();
            imageInsertFragment.setImageInsertListener(new ImageInsertFragment.ImageInsertListener() {
                @Override
                public void onDone(Uri uri) {
                    imageView.setImageURI(uri);
                }
            });
            imageInsertFragment.setImage(bmp);
            imageInsertFragment.show( ((FragmentActivity) ctx).getSupportFragmentManager(),imageInsertFragment.getTag());
        }




        rotate.setOnTouchListener(new RotateTouchListener(imageRootView,imageView));

        resize.setOnTouchListener(new ResizeTouchListener(imageRootView));

        imgClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ctx, "Remove", Toast.LENGTH_SHORT).show();

                mEditorView.removeView(imageRootView);
                addedViews.remove(imageRootView);
                removedViews.add(imageRootView);
            }
        });

        DragTouchListener tl = new DragTouchListener(ctx);

        tl.setOnGestureControl(new DragTouchListener.OnGestureControl() {
            @Override
            public void onClick() {

                setDrawingMode(false);
                removeDrawableBackground();
                boolean isBackgroundVisible = frmBorder.getTag() != null && (boolean) frmBorder.getTag();
                frmBorder.setBackgroundResource(isBackgroundVisible ? 0 : R.drawable.edit_text_background);
                imgClose.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                resize.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                rotate.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                frmBorder.setTag(!isBackgroundVisible);

            }

            @Override
            public void onLongClick() { }

            @Override
            public void onDoubleClick() {

            }
        });
        imageRootView.setOnTouchListener(tl);
        addViewToParent(imageRootView,Type.IMAGE);

    }

    public void viewUndo() {

        if (addedViews.size() > 0) {
            View removedView = addedViews.get(addedViews.size() - 1);
            Log.d(TAG,"Undo Func");
            if (addedViews.contains(removedView)) {
                mEditorView.removeView(removedView);
                addedViews.remove(removedView);
                removedViews.add(removedView);

            }

        }
    }
    public void viewRedo() {

        if (removedViews.size() > 0) {
            View removedView = removedViews.get(removedViews.size() - 1);
            Log.d(TAG,"Undo Func");
            if (removedViews.contains(removedView)) {
                mEditorView.addView(removedView);
                removedViews.remove(removedView);
                addedViews.add(removedView);

            }
        }
    }
    void removeDrawableBackground(){



        for (View v : addedViews) {


                v.findViewById(R.id.frmBorder).setBackground(null);
                v.findViewById(R.id.frmBorder).setTag(null);
                v.findViewById(R.id.imgPhotoEditorClose).setVisibility(View.INVISIBLE);
                v.findViewById(R.id.resizeBtn).setVisibility(View.INVISIBLE);


            v.findViewById(R.id.rotateBtn).setVisibility(View.INVISIBLE);
        }

    }

    private void showEditDialog(TextView tv) {
        FragmentManager fm = ((FragmentActivity) ctx).getSupportFragmentManager();

        EditTextDialogFragment editTextDialogFragment = (EditTextDialogFragment) EditTextDialogFragment.newInstance("hello",tv);
        editTextDialogFragment.show(fm, "fragment_edit_text_dialog");


    }




}


