package com.sudhar.netizenEditor.ImageEditor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sudhar.netizenEditor.R;

import java.io.File;
import java.util.ArrayList;

public class FontFragment extends BottomSheetDialogFragment {

    private final String TAG = "FontFrag";
    private TypefaceSelectListener mTypefaceSelectListener;

    public FontFragment() {

    }

    public interface TypefaceSelectListener {
        void onTypefaceSelect(Typeface mTypeface);
    }

    public void setTypefaceSelectListener(TypefaceSelectListener mTypefaceSelectListener) {
       this.mTypefaceSelectListener = mTypefaceSelectListener;
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
        View contentView = View.inflate(getContext(), R.layout.fragment_font, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();


//        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        RecyclerView appFontRv = contentView.findViewById(R.id.AppFontRv);
        RecyclerView localFontRv = contentView.findViewById(R.id.LocalFontRv);

        GridLayoutManager appFontGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        GridLayoutManager localFontGridLayoutManager = new GridLayoutManager(getActivity(), 3);

        appFontRv.setLayoutManager(appFontGridLayoutManager);
        localFontRv.setLayoutManager(localFontGridLayoutManager);

        AppFontAdapter appFontAdapter = null;
        appFontAdapter = new AppFontAdapter();
        appFontRv.setAdapter(appFontAdapter);

        LocalFontAdapter localFontAdapter = null;
        localFontAdapter = new LocalFontAdapter();
        localFontRv.setAdapter(localFontAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public class AppFontAdapter extends RecyclerView.Adapter<AppFontAdapter.ViewHolder> {

        ArrayList<Typeface> font = new ArrayList<>();
        String[] list = getResources().getStringArray(R.array.fonts);

        public  AppFontAdapter()  {



            for (String fontName : list) {

                int id =  getResources().getIdentifier(fontName,"font",getContext().getPackageName());
                font.add(ResourcesCompat.getFont(getContext(), id));
            }






        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_list, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {



            holder.fontBox.setTypeface(font.get(position));



        }

        @Override
        public int getItemCount() {
            return font.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView fontBox;

            ViewHolder(View itemView) {
                super(itemView);
                fontBox = itemView.findViewById(R.id.fontBox);



                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTypefaceSelectListener != null) {
                            mTypefaceSelectListener.onTypefaceSelect(font.get(getLayoutPosition()));

                        }
                        dismiss();
                    }
                });
            }
        }


    }

    public class LocalFontAdapter extends RecyclerView.Adapter<LocalFontAdapter.ViewHolder> {

        ArrayList<File> font ;//= new ArrayList<>();

        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File imgFile = new  File(sdCardDirectory,"font");

        public  LocalFontAdapter()  {




            font = getfile(imgFile);


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_list, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {



            holder.fontBox.setTypeface(Typeface.createFromFile(font.get(position)));




        }

        @Override
        public int getItemCount() {
            return font.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView fontBox;

            ViewHolder(View itemView) {
                super(itemView);
                fontBox = itemView.findViewById(R.id.fontBox);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTypefaceSelectListener != null) {
                            mTypefaceSelectListener.onTypefaceSelect(Typeface.createFromFile(font.get(getLayoutPosition())));

                        }
                        dismiss();
                    }
                });
            }
        }

        private ArrayList<File> getfile(File dir) {
            File listFile[] = dir.listFiles();
            ArrayList<File> file = new ArrayList<>();
            if (listFile != null && listFile.length > 0) {
                for (int i = 0; i < listFile.length; i++) {

                    if (listFile[i].isDirectory()) {
//                    fileList.add(listFile[i]);
//                    getfile(listFile[i]);

                    } else {
                        if (listFile[i].getName().endsWith(".ttf"))

                        {
                            file.add(listFile[i]);
                        }
                    }

                }
            }
            return file;
        }


    }
}
