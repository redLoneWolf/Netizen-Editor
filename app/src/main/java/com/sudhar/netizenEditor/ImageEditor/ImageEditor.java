package com.sudhar.netizenEditor.ImageEditor;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.sudhar.netizenEditor.MainActivity;
import com.sudhar.netizenEditor.R;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;


public class ImageEditor extends AppCompatActivity implements ToolsAdapter.OnItemSelected, ColorPickerDialogListener, StickerFragment.StickerSelectListener, BrushPreferences.BrushPrefListener ,TextFormatFragment.TextFormatListener{
    private static Bitmap bmp;
    private final String TAG = "IMAGE EDITOR";
    private final int TEXT_COLOR_DIALOG_ID = 101;
    private final int TEXT_BG_COLOR_DIALOG_ID = 102;
    private final int BRUSH_COLOR_DIALOG_ID = 103 ;
    private final int STROKE_COLOR_DIALOG_ID = 104;

    private final int DRAWING_TICK_TAG = 111;
    private final int CROPPING_TICK_TAG = 222;
    private final int ROTATE_TICK_TAG = 333;

    Bitmap bmp1;
    Uri uri;
    private ToolsAdapter mEditingToolsAdapter = new ToolsAdapter(this);
    private ImageButton brushUndoBtn, brushRedoBtn;
    private ImageButton saveBtn, formatBtn, tickBtn,xBtn;
    int CurrentTextColor, CurrentTextBgColor;
    BrushDrawingView drawingView;

    private Drawable mDrawableBackground;

    EditorView mEditorView;
    private RecyclerView mRvTools;

    private RelativeLayout rl;
    String path;
    EditTools mTools;
    String desiredImagePath;
    ActionBar actionBar;
    BrushPreferences brushPrefFragment;
    TextFormatFragment textFormatFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);
        actionBar = getSupportActionBar();

//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        rl = findViewById(R.id.relativeLayout);
//        rl.setBackgroundColor(Color.WHITE);

        CurrentTextColor = Color.BLACK;
        CurrentTextBgColor = Color.WHITE;

        mRvTools = findViewById(R.id.rvConstraintTools);
        LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);


        mEditingToolsAdapter.notifyDataSetChanged();

//        mEditingToolsAdapter.mToolList.add("Brush",R.drawable.ic_brush_icon,Type.BRUSH_DRAWING);

        Resources res = getResources();
        mDrawableBackground = res.getDrawable(R.drawable.edit_text_background);
        desiredImagePath = null;


//        txtBtn = findViewById(R.id.addTextBtn);
//        imgBtn = findViewById(R.id.addImageBtn);
        saveBtn = findViewById(R.id.save);
        formatBtn = findViewById(R.id.formatBtn);
        formatBtn.setVisibility(View.GONE);
        brushUndoBtn = findViewById(R.id.undoBtn);
        brushRedoBtn = findViewById(R.id.redoBtn);
        tickBtn = findViewById(R.id.tickBtn);
        xBtn = findViewById(R.id.xBtn);
        mEditorView = findViewById(R.id.EditorView);
        mRvTools.setEnabled(false);


//setting image resource


        saveBtn.setOnClickListener(new ButtonClickListener());

        formatBtn.setOnClickListener(new ButtonClickListener());

        mEditorView.setOnClickListener(new ButtonClickListener());

        brushUndoBtn.setOnClickListener(new ButtonClickListener());
        brushUndoBtn.setVisibility(View.GONE);
        brushRedoBtn.setOnClickListener(new ButtonClickListener());
        brushRedoBtn.setVisibility(View.GONE);

        tickBtn.setOnClickListener(new ButtonClickListener());
        tickBtn.setVisibility(View.GONE);
        tickBtn.setTag(0);

        xBtn.setOnClickListener(new ButtonClickListener());
        xBtn.setVisibility(View.GONE);
        xBtn.setTag(0);


//        Bundle extras = getIntent().getExtras();
//        byte[] b = extras.getByteArray("bmp");
//
//         bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
//        mEditorView.setImage(bmp);

//        drawingView = mEditorView.getBrushDrawingView();
//        drawingView.setDefaults();
//        mTools = new EditTools(ImageEditor.this, mEditorView, mDrawableBackground, drawingView);
//
//        Bitmap.Config bitmapConfig = bmp.getConfig();
//        if (bitmapConfig == null) {
//            bitmapConfig = Bitmap.Config.ARGB_8888;
//        }
//        bmp1 = bmp.copy(bitmapConfig, true);

        Bundle extras = getIntent().getExtras();
        String b = extras.getString("picture");
        path = b;
        File imgFile = new File(b);
        if (imgFile.exists()) {
            uri = Uri.fromFile(imgFile);
            bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());



            mEditorView.setImage(bmp);

            drawingView = mEditorView.getBrushDrawingView();
            drawingView.setDefaults();
            mTools = new EditTools(ImageEditor.this, mEditorView, mDrawableBackground, drawingView);

            Bitmap.Config bitmapConfig = bmp.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888;
            }
            bmp1 = bmp.copy(bitmapConfig, true);
        }

        else {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(ImageEditor.this, "Image Not Found", Toast.LENGTH_SHORT).show();
//
        }



//BrushPreference init ---------------------------------------------
        brushPrefFragment = new BrushPreferences();
        brushPrefFragment.setBrushPrefListener(this);
        brushPrefFragment.setBrushColorDialogId(BRUSH_COLOR_DIALOG_ID);

//        ------------------------------------------------------------

        textFormatFragment = new TextFormatFragment();
        textFormatFragment.setTextFormatListener(this);
        textFormatFragment.setSTROKE_COLOR_DIALOG_ID(STROKE_COLOR_DIALOG_ID);
        textFormatFragment.setTEXT_BG_COLOR_DIALOG_ID(TEXT_BG_COLOR_DIALOG_ID);
        textFormatFragment.setTEXT_COLOR_DIALOG_ID(TEXT_COLOR_DIALOG_ID);



    }


    void saveImage() {

        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDirectory, "crop.png");

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp1 = createBitmapFromView(mEditorView);
            bmp1.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(ImageEditor.this, "Saved! to " + sdCardDirectory.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onBrushSizeChange(int size) {
        drawingView.setBrushSize(size);
    }

    @Override
    public void onDone( int fontStyle,Typeface font, int textGravity,float strokeWidth, int textColor,int bgColor, int strokeColor) {
        mTools.setStrokeColor(strokeColor);
        mTools.setStrokeWidth(strokeWidth);
        mTools.setTextBgColor(bgColor);
        mTools.setTextColor(textColor);
        mTools.setFontStyle(font,fontStyle);
        mTools.setTextGravity(textGravity);



    }


    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save:
                    mTools.removeDrawableBackground();
                    saveImage();

                    break;

                case R.id.EditorView:
                    formatBtn.setVisibility(View.GONE);
                    mTools.removeDrawableBackground();
                    break;
                case R.id.formatBtn:
//                    TextFormatDialog();
                    textFormatFragment.setBgColor(mTools.getTextBgColor());
                    textFormatFragment.setTextColor(mTools.getTextColor());
                    textFormatFragment.setStrokeColor(mTools.getStrokeColor());
                    textFormatFragment.setFontStyle(mTools.getFontStyle());
                    textFormatFragment.setTextGravity(mTools.getTextGravity());
                    textFormatFragment.setFont(mTools.getFont());
                    textFormatFragment.show(getSupportFragmentManager(), textFormatFragment.getTag());
                    Log.d(TAG, "text format frag");
                    break;
                case R.id.undoBtn:
                    Log.d(TAG, "Undo");
                    mTools.brushUndo();
                    break;

                case R.id.redoBtn:

                    Log.d(TAG, "Redo");
                    mTools.brushRedo();

                    break;


                case R.id.tickBtn:
                    if ((int) tickBtn.getTag() == CROPPING_TICK_TAG) {

                        CropImageView cropView = mEditorView.findViewById(R.id.cropImageView);
                        Bitmap hello = cropView.getCroppedImage();
                        mEditorView.setImage(hello);
                        mEditorView.removeView(cropView);
                        tickBtn.setVisibility(View.GONE);
                        tickBtn.setTag(0);
                        xBtn.setVisibility(View.GONE);
                        xBtn.setTag(0);
                        mRvTools.setVisibility(View.VISIBLE);
                        Log.d("Cropped", "Crop Complete");


                    } else if ((int) tickBtn.getTag() == DRAWING_TICK_TAG) {
                        mTools.setDrawingMode(false);
                        tickBtn.setVisibility(View.GONE);
                        tickBtn.setTag(0);

                    } else if ((int) tickBtn.getTag() == ROTATE_TICK_TAG) {

                        tickBtn.setVisibility(View.GONE);
                        tickBtn.setTag(0);

                        mEditingToolsAdapter.changeToMainTools();
                    }

                    break;

                case R.id.xBtn:
                    if ((int) xBtn.getTag() == CROPPING_TICK_TAG) {

                        CropImageView cropView = mEditorView.findViewById(R.id.cropImageView);

                        mEditorView.removeView(cropView);
                        mEditorView.setImage(mEditorView.getImage());
                        xBtn.setVisibility(View.GONE);
                        xBtn.setTag(0);
                        tickBtn.setVisibility(View.GONE);
                        tickBtn.setTag(0);
                        mRvTools.setVisibility(View.VISIBLE);
                        Log.d("Cropped", "Crop Complete");


                    }


            }
        }
    }


    public void btn_gallery() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d(TAG, "Text Button Clicked");
            desiredImagePath = ImagePath.getPath(ImageEditor.this, data.getData());
            mTools.addImage(desiredImagePath,Type.IMAGE);
        }

    }


    public @NonNull
    static Bitmap createBitmapFromView(@NonNull View view) {

//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();

        if (background != null) {
            background.draw(canvas);
        }
        view.draw(canvas);

        return bitmap;
    }


    //    Recycler
    @Override
    public void onToolSelected(Type toolType) {
        switch (toolType) {

            case TEXT:

                mTools.removeDrawableBackground();
                mTools.setDrawingMode(false);
                mTools.addText();
                formatBtn.setVisibility(View.VISIBLE);

                Log.d(TAG, "Text Button Clicked");
                break;


            case IMAGE:
                mTools.removeDrawableBackground();
                mTools.setDrawingMode(false);
                Log.d(TAG, "Image Button Clicked");
                btn_gallery();
                break;

            case DRAWING:
                mTools.setDrawingMode(true);
                brushPrefFragment.setColor(drawingView.getBrushColor());

                brushPrefFragment.setSize(drawingView.getBrushSize());
                brushPrefFragment.show(getSupportFragmentManager(), brushPrefFragment.getTag());
                Log.d(TAG, "Sticker  Button Clicked");

                tickBtn.setVisibility(View.VISIBLE);
                tickBtn.setTag(DRAWING_TICK_TAG);
                mTools.removeDrawableBackground();

                Log.d(TAG, "Brush Draw Button Clicked");
                break;

            case CROP:
                mTools.setDrawingMode(false);
                mRvTools.setVisibility(View.GONE);

                tickBtn.setVisibility(View.VISIBLE);
                tickBtn.setTag(CROPPING_TICK_TAG);
                xBtn.setVisibility(View.VISIBLE);
                xBtn.setTag(CROPPING_TICK_TAG);
                mTools.crop(mEditorView.getImage());
                Log.d(TAG, "Crop  Button Clicked");
                break;

            case ROTATE:
                mTools.setDrawingMode(false);
                tickBtn.setVisibility(View.VISIBLE);
                tickBtn.setTag(ROTATE_TICK_TAG);
                mEditingToolsAdapter.changeToRotateTools();
                Log.d(TAG, "Rotate  Button Clicked");
                break;

            case ROTATE_LEFT:
                mEditorView.rotateImageLeft();
                Log.d(TAG, "Rotate Left Button Clicked");
                break;

            case ROTATE_RIGHT:
                mEditorView.rotateImageRight();
                Log.d(TAG, "Rotate Right Button Clicked");
                break;

            case FLIP_HORIZONTAL:
                mEditorView.flipImageHorizontal();
                Log.d(TAG, "Flip  Button Clicked");
                break;
            case FLIP_VERTICAL:
                mEditorView.flipImageVertical();
                Log.d(TAG, "Flip  Button Clicked");
                break;
            case STICKER:
                mTools.setDrawingMode(false);
//                StickerListDialogFragment st = StickerListDialogFragment.newInstance()
                StickerFragment bottomSheetFragment = new StickerFragment();
                bottomSheetFragment.setStickerListener(this);

                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                Log.d(TAG, "Sticker  Button Clicked");
                break;

            case PADDING:
                mTools.setDrawingMode(false);
                mEditorView.setBackgroundColor(Color.WHITE);
                PaddingFragment paddingFragment =new PaddingFragment();
                paddingFragment.setTopPadding(mEditorView.getImageView().getPaddingTop());
                paddingFragment.setBottomPadding(mEditorView.getImageView().getPaddingBottom());
                paddingFragment.setPaddingListener(new MyPaddingListener());
                paddingFragment.show(getSupportFragmentManager(), paddingFragment.getTag());
                Log.d(TAG, "Padding  Button Clicked");
                break;
        }
    }


    //    Action Bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Color Picker
    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case TEXT_COLOR_DIALOG_ID:
                textFormatFragment.setTextColor(color);
                Toast.makeText(ImageEditor.this, " Text Color  " + color, Toast.LENGTH_SHORT).show();
                break;

            case TEXT_BG_COLOR_DIALOG_ID:
                textFormatFragment.setBgColor(color);
                Toast.makeText(ImageEditor.this, " Bg Color  " + color, Toast.LENGTH_SHORT).show();
                break;

            case BRUSH_COLOR_DIALOG_ID:
                brushPrefFragment.setColor(color);
                drawingView.setBrushColor(color);
                Toast.makeText(ImageEditor.this, " Brush Color  " + color, Toast.LENGTH_SHORT).show();
                break;

            case STROKE_COLOR_DIALOG_ID:
                textFormatFragment.setStrokeColor(color);
                Toast.makeText(ImageEditor.this, " Stroke Color  " + color, Toast.LENGTH_SHORT).show();
                break;



        }

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }


    //Sticker Selection
    @Override
    public void onStickerSelect(String path) {
        mTools.removeDrawableBackground();
        Log.d(TAG, "Sticker Selected");
        mTools.addImage(path,Type.STICKER);
    }


//    TextFormatDialog

    void TextFormatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageEditor.this);
        builder.setTitle("Format text");

        String[] animals = {"Change Text Color", "Change background Color"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        ColorPickerDialog.newBuilder()
                                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                                .setAllowPresets(false)
                                .setColor(CurrentTextColor)
                                .setShowAlphaSlider(true)
                                .setDialogId(TEXT_COLOR_DIALOG_ID)
                                .show(ImageEditor.this);
                        break;
                    case 1:
                        ColorPickerDialog.newBuilder()
                                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                                .setAllowPresets(false)
                                .setColor(CurrentTextBgColor)
                                .setShowAlphaSlider(true)
                                .setDialogId(TEXT_BG_COLOR_DIALOG_ID)
                                .show(ImageEditor.this);
                        break;

                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }




    class MyPaddingListener implements PaddingFragment.PaddingListener{
        ImageView img = mEditorView.getImageView();
        private int tempTopPadding,tempBottomPadding;
        @Override
        public void onTopPaddingChange(int top) {

            img.setPadding(0,top,0,tempBottomPadding);
//            img.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                    RelativeLayout.LayoutParams.MATCH_PARENT));
            tempTopPadding = top;
        }

        @Override
        public void onBottomPaddingChange(int bottom) {

            img.setPadding(0,tempTopPadding,0,bottom);
//            img.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                    RelativeLayout.LayoutParams.MATCH_PARENT));
            tempBottomPadding = bottom;

        }


    }

}

