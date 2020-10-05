package com.sudhar.netizenEditor.ImageEditor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sudhar.netizenEditor.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StickerFragment extends BottomSheetDialogFragment {

    private final String TAG = "Sticker Frag";

    public StickerFragment() {
        // Required empty public constructor

    }

    private StickerSelectListener mStickerListener;

    public void setStickerListener(StickerSelectListener stickerListener) {
        mStickerListener = stickerListener;
    }

    public interface StickerSelectListener {
        void onStickerSelect(String path);
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


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_sticker, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();


        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        RecyclerView stickerRv = contentView.findViewById(R.id.stickerRv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        stickerRv.setLayoutManager(gridLayoutManager);
        StickerAdapter stickerAdapter = null;
        try {
            stickerAdapter = new StickerAdapter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stickerRv.setAdapter(stickerAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
        AssetManager assetManager;
        ArrayList<String> listImages;
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File imgFile = new  File(sdCardDirectory+"/stickers/");
        public StickerAdapter() throws IOException {

            listImages = getfile(imgFile);



        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticker_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


                Drawable d = Drawable.createFromPath(pathBuilder(listImages.get(position)));

                holder.imgSticker.setImageDrawable(d);

        }

        @Override
        public int getItemCount() {
            return listImages.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgSticker;

            ViewHolder(View itemView) {
                super(itemView);
                imgSticker = itemView.findViewById(R.id.stickerImg);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mStickerListener != null) {
                            mStickerListener.onStickerSelect(pathBuilder(listImages.get(getLayoutPosition())));

                        }
                        dismiss();
                    }
                });
            }
        }

        private String pathBuilder(String filename) {
            String path = imgFile + "/" + filename;
            return path;

        }
    }




    private ArrayList<String> getfile(File dir) {
        File listFile[] = dir.listFiles();
        ArrayList<String> filename = new ArrayList<>();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
//                    fileList.add(listFile[i]);
//                    getfile(listFile[i]);

                } else {
                    if (listFile[i].getName().endsWith(".png")
                            || listFile[i].getName().endsWith(".jpg")
                            || listFile[i].getName().endsWith(".jpeg")
                            || listFile[i].getName().endsWith(".gif"))

                    {
                        filename.add(listFile[i].getName());
                    }
                }

            }
        }
        return filename;
    }


}