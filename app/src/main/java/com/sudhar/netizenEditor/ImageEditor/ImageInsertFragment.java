package com.sudhar.netizenEditor.ImageEditor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sudhar.netizenEditor.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class ImageInsertFragment extends BottomSheetDialogFragment {

    private Button doneBtn,cancelBtn,cropBtn;
    private ImageView imageView;
    private Bitmap image;
    private Uri resultUri;
    private ImageInsertListener imageInsertListener;

    public void setImageInsertListener(ImageInsertListener imageInsertListener) {
        this.imageInsertListener = imageInsertListener;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        if(imageView!=null){
            imageView.setImageBitmap(image);
        }
    }

    public interface ImageInsertListener{

        void onDone(Uri resultUri);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_image_insert, container, false);




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doneBtn = view.findViewById(R.id.ImageDoneBtn);
        cropBtn = view.findViewById(R.id.CropBtn);

        cancelBtn = view.findViewById(R.id.ImageCancelBtn);

        imageView= view.findViewById(R.id.imagePreview);
        imageView.setImageBitmap(image);

        cropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity(getImageUri(getContext(),image))
                        .start(getContext(), ImageInsertFragment.this);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageInsertListener.onDone(resultUri);
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
