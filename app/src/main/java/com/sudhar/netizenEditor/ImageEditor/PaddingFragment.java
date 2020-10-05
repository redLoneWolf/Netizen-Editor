package com.sudhar.netizenEditor.ImageEditor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sudhar.netizenEditor.R;

public class PaddingFragment extends BottomSheetDialogFragment {
    SeekBar topPaddingSeekbr,bottomPaddingSeekbr;
    int topPadding,bottomPadding,lastTop,lastBottom;


    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
        this.lastTop = topPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
        this.lastBottom = bottomPadding;
    }

    public PaddingFragment() {


    }

    public interface PaddingListener {


        void onTopPaddingChange(int top);

        void onBottomPaddingChange(int bottom);



    }
    private PaddingListener mPaddingListener;

    public void setPaddingListener(PaddingListener mPaddingListener) {
        this.mPaddingListener = mPaddingListener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_padding, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        topPaddingSeekbr = view.findViewById(R.id.TopPaddingSeekabr);

        bottomPaddingSeekbr = view.findViewById(R.id.BottomPaddingSeekbar);

        topPaddingSeekbr.setProgress(topPadding);
        bottomPaddingSeekbr.setProgress(bottomPadding);


        topPaddingSeekbr.setProgress(topPadding);
        bottomPaddingSeekbr.setProgress(bottomPadding);


        topPaddingSeekbr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPaddingListener.onTopPaddingChange(i);
                topPadding = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bottomPaddingSeekbr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPaddingListener.onBottomPaddingChange(i);
                bottomPadding= i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });









    }
}
