package com.sudhar.netizenEditor.ImageEditor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.sudhar.netizenEditor.R;

public class BrushPreferences extends BottomSheetDialogFragment {
    private final String TAG = "BrushPrefFrag";
    private int BRUSH_COLOR_DIALOG_ID;
    private int brushColor;
    private float brushSize;

    private SeekBar BrushSizeBar;
    private  Button colorBtn;
    public BrushPreferences() {

    }

    private BrushPrefListener mBrushPrefListener;

    public void setBrushPrefListener(BrushPrefListener mbrushpreflistener) {
        mBrushPrefListener = mbrushpreflistener;
    }

    public void setBrushColorDialogId(int num) {
        BRUSH_COLOR_DIALOG_ID = num;
    }

    public interface BrushPrefListener {


        void onBrushSizeChange(int size);

    }


    public void setSize(float size) {
        this.brushSize = size;
    }

    public void setColor(int color) {
        this.brushColor = color;
        if(colorBtn!=null){
            colorBtn.setBackgroundColor(color);
        }

    }



    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


         BrushSizeBar = view.findViewById(R.id.SizeBar);
         colorBtn = view.findViewById(R.id.ColorBtn);

        BrushSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mBrushPrefListener.onBrushSizeChange(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        BrushSizeBar.setProgress((int) brushSize);
        colorBtn.setBackgroundColor( brushColor);

        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                        .setAllowPresets(false)
                        .setColor(Color.BLACK)
                        .setShowAlphaSlider(true)
                        .setDialogId(BRUSH_COLOR_DIALOG_ID)
                        .show(getActivity());
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragemnt_brush_pref, container, false);
    }





}
