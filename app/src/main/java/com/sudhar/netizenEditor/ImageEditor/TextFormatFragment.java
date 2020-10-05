package com.sudhar.netizenEditor.ImageEditor;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.sudhar.netizenEditor.R;

import java.util.ArrayList;
import java.util.List;

public class TextFormatFragment extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {
    private final String TAG = "TextFormatTag";
    private SeekBar strokeWidthSeekbar;
    private StrokeTextView previewtext;
    private TextView  textColorBtn,bgColorBtn, strokeColorBtn;
    private Button doneBtn,cancelBtn;
    private ImageButton leftAlignBtn,rightAlignBtn,centerAlignBtn;
    private Spinner formatSpinner;
    private TextView fontPreview;



    private  int TEXT_COLOR_DIALOG_ID ;
    private  int TEXT_BG_COLOR_DIALOG_ID ;
    private  int STROKE_COLOR_DIALOG_ID ;

    List<String> format;
    ArrayAdapter<String> dataAdapter;



    private int textColor,strokeColor,bgColor;
    private int textGravity;
    private float strokeWidth,textSize;
    Typeface font;
    private int fontStyle;

    private TextFormatListener mTextFormatListener;

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if(textColorBtn!=null && previewtext!=null){
            textColorBtn.setBackgroundColor(textColor);

            if(textColor==0)textColorBtn.setText("Transparent");
            previewtext.setTextColor(textColor);
        }

    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        if(  previewtext!=null) {

            previewtext.setTextSize(textSize);
        }
//        previewtext.setTextSize(textSize);
    }


    public void setTextGravity(int textGravity) {
        this.textGravity = textGravity;
        if(  previewtext!=null) {

            previewtext.setGravity(textGravity);
        }
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        if(strokeColorBtn!=null && previewtext!=null){
            strokeColorBtn.setBackgroundColor(strokeColor);

            if(strokeColor==0)strokeColorBtn.setText("Transparent");
            previewtext.setStrokeColor(strokeColor);
        }
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        if(bgColorBtn!=null && previewtext!=null){
            bgColorBtn.setBackgroundColor(bgColor);
            if(bgColor==0)bgColorBtn.setText("Transparent");
            previewtext.setBackgroundColor(bgColor);
        }
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        if(strokeWidthSeekbar!=null && previewtext!=null){

            previewtext.setStrokeWidth(strokeWidth);
            strokeWidthSeekbar.setProgress((int) strokeWidth);
        }
    }

    public void setFont(Typeface font) {
        this.font = font;
        if(fontPreview!=null && previewtext!=null){
//            fontBtn.setBackgroundColor(textColor);
            previewtext.setTypeface(font,fontStyle);
            fontPreview.setTypeface(font);
        }
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
        if(formatSpinner!=null && previewtext!=null){
//            fontBtn.setBackgroundColor(textColor);
//            formatSpinner.setSelection(fontStyle);
            previewtext.setTypeface(font,fontStyle);
        }
    }

    public void setFontStyle(Typeface font,int fontStyle){
        this.fontStyle = fontStyle;
        this.font = font;
        if(formatSpinner!=null && previewtext!=null && fontPreview!=null){
//            fontBtn.setBackgroundColor(textColor);
            formatSpinner.setSelection(fontStyle);
            previewtext.setTypeface(font,fontStyle);

        }

    }

    public void setTEXT_COLOR_DIALOG_ID(int TEXT_COLOR_DIALOG_ID) {
        this.TEXT_COLOR_DIALOG_ID = TEXT_COLOR_DIALOG_ID;
    }

    public void setTEXT_BG_COLOR_DIALOG_ID(int TEXT_BG_COLOR_DIALOG_ID) {
        this.TEXT_BG_COLOR_DIALOG_ID = TEXT_BG_COLOR_DIALOG_ID;
    }

    public void setSTROKE_COLOR_DIALOG_ID(int STROKE_COLOR_DIALOG_ID) {
        this.STROKE_COLOR_DIALOG_ID = STROKE_COLOR_DIALOG_ID;
    }

    public void setTextFormatListener(TextFormatListener mTextFormatListener) {
        this.mTextFormatListener = mTextFormatListener;
    }


    public interface TextFormatListener {


        void onDone(int fontStyle,Typeface font,int textGravity,float strokeWidth,int textColor,int bgColor,int strokeColor);

    }

    public TextFormatFragment() {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_text_format, container, false);
        format  = new ArrayList<String>();
        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, format);
        format.add("Normal");
        format.add("Bold");
        format.add("Italic");
        format.add("Bold Italic");



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strokeWidthSeekbar = view.findViewById(R.id.StrokeWidthSeekbar);

        formatSpinner = view.findViewById(R.id.FormatSpinner);

        previewtext = (StrokeTextView) view.findViewById(R.id.TextPreview);


        leftAlignBtn = view.findViewById(R.id.LeftAlignBtn);
        rightAlignBtn = view.findViewById(R.id.RightAlignBtn);
        centerAlignBtn = view.findViewById(R.id.CenterAlignBtn);

        leftAlignBtn.setOnClickListener(new ButtonListener());
        rightAlignBtn.setOnClickListener(new ButtonListener());
        centerAlignBtn.setOnClickListener(new ButtonListener());

        fontPreview = view.findViewById(R.id.FontPreview);
        textColorBtn = view.findViewById(R.id.TextColorbtn);
        bgColorBtn = view.findViewById(R.id.BgColorbtn);
        strokeColorBtn = view.findViewById(R.id.StrokeColorbtn);

        doneBtn = view.findViewById(R.id.FormatDoneBtn);
        cancelBtn = view.findViewById(R.id.FormatCancelBtn);

        fontPreview.setOnClickListener(new ButtonListener());
        textColorBtn.setOnClickListener(new ButtonListener());
        bgColorBtn.setOnClickListener(new ButtonListener());
        strokeColorBtn.setOnClickListener(new ButtonListener());

        doneBtn.setOnClickListener(new ButtonListener());
        cancelBtn.setOnClickListener(new ButtonListener());

        textColorBtn.setBackgroundColor(textColor);
        bgColorBtn.setBackgroundColor(bgColor);
        strokeColorBtn.setBackgroundColor(strokeColor);

        strokeWidthSeekbar.setOnSeekBarChangeListener(new SeekListener());



        formatSpinner.setOnItemSelectedListener(this);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formatSpinner.setAdapter(dataAdapter);







        previewtext.setText("Text Preview");
        setTextSize(30);
//        previewtext.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        previewtext.setStrokeWidth(strokeWidth);
        previewtext.setTypeface(font,Typeface.ITALIC);
        previewtext.setTextColor(textColor);
        previewtext.setStrokeColor(strokeColor);
        previewtext.setBackgroundColor(bgColor);
//        previewtext.setGravity(textGravity);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();


                switch (position) {
                    case Typeface.NORMAL:
                        setFontStyle(Typeface.NORMAL);
                        break;
                    case Typeface.BOLD:
                        setFontStyle(Typeface.BOLD);
                        break;
                    case Typeface.ITALIC:
                        setFontStyle(Typeface.ITALIC);
                        break;
                    case Typeface.BOLD_ITALIC:
                        setFontStyle(Typeface.BOLD_ITALIC);
                        break;
                }


        }



    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }





    private class MyFontListener implements FontFragment.TypefaceSelectListener{

        @Override
        public void onTypefaceSelect(Typeface mTypeface) {
//            previewtext.setTypeface(mTypeface);
            setFont(mTypeface);
        }
    }





    private class SeekListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()){
                case R.id.StrokeWidthSeekbar:
                    previewtext.setStrokeWidth(progress);
                    setStrokeWidth(progress);
                    break;


            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }




    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.FontPreview:
                    FontFragment fontFragment = new FontFragment();
                    fontFragment.setTypefaceSelectListener(new MyFontListener());
                    fontFragment.show(getActivity().getSupportFragmentManager(),fontFragment.getTag());
                    break;
                case R.id.TextColorbtn:
                    ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                        .setAllowPresets(false)
                        .setColor(Color.BLACK)
                        .setShowAlphaSlider(true)
                        .setDialogId(TEXT_COLOR_DIALOG_ID)
                        .show(getActivity());
                    break;
                case R.id.BgColorbtn:
                    ColorPickerDialog.newBuilder()
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setColor(Color.BLACK)
                            .setShowAlphaSlider(true)
                            .setDialogId(TEXT_BG_COLOR_DIALOG_ID)
                            .show(getActivity());
                    break;
                case R.id.StrokeColorbtn:
                    ColorPickerDialog.newBuilder()
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setColor(Color.BLACK)
                            .setShowAlphaSlider(true)
                            .setDialogId(STROKE_COLOR_DIALOG_ID)
                            .show(getActivity());
                    break;

                case R.id.FormatDoneBtn:
                    mTextFormatListener.onDone(fontStyle,font,textGravity,strokeWidth,textColor,bgColor,strokeColor);
                    dismiss();
                    break;
                case R.id.FormatCancelBtn:
                    dismiss();
                    break;

                case R.id.LeftAlignBtn:
                    setTextGravity(Gravity.LEFT);
                    break;

                case R.id.CenterAlignBtn:
                    setTextGravity(Gravity.CENTER);
                    break;
                case R.id.RightAlignBtn:
                    setTextGravity(Gravity.RIGHT);
                    break;

            }
        }
    }
}
