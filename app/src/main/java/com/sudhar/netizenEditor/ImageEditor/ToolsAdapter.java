package com.sudhar.netizenEditor.ImageEditor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudhar.netizenEditor.R;

import java.util.ArrayList;
import java.util.List;




public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {

     List<ToolModel> mToolList = new ArrayList<>();
    private OnItemSelected mOnItemSelected;

    public ToolsAdapter(OnItemSelected onItemSelected) {
        mOnItemSelected = onItemSelected;
        changeToMainTools();


    }


    public interface OnItemSelected {
        void onToolSelected(Type toolType);
    }

    class ToolModel {
        private String mToolName;
        private int mToolIcon;
        private Type mToolType;

        ToolModel(String toolName, int toolIcon, Type toolType) {
            mToolName = toolName;
            mToolIcon = toolIcon;
            mToolType = toolType;
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_editing_tools, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToolModel item = mToolList.get(position);
        holder.txtTool.setText(item.mToolName);
        holder.imgToolIcon.setImageResource(item.mToolIcon);
    }

    @Override
    public int getItemCount() {
        return mToolList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgToolIcon;
        TextView txtTool;

        ViewHolder(View itemView) {
            super(itemView);
            imgToolIcon = itemView.findViewById(R.id.imgToolIcon);
            txtTool = itemView.findViewById(R.id.txtTool);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemSelected.onToolSelected(mToolList.get(getLayoutPosition()).mToolType);
                }
            });
        }
    }

    public void clearItems(){
        mToolList.clear();
    }

    public void changeToRotateTools(){
        clearItems();
        mToolList.add(new ToolModel("Vertical", R.drawable.ic_flip_vertical, Type.FLIP_VERTICAL));
        mToolList.add(new ToolModel("Horizontal", R.drawable.ic_flip_horizontal, Type.FLIP_HORIZONTAL));
        mToolList.add(new ToolModel("Right", R.drawable.ic_rotate_right, Type.ROTATE_RIGHT));
        mToolList.add(new ToolModel("Left", R.drawable.ic_rotate_left, Type.ROTATE_LEFT));
        notifyDataSetChanged();
    }

    public void changeToMainTools(){
        clearItems();
        mToolList.add(new ToolModel("Padding", R.drawable.ic_rotate_left, Type.PADDING));
        mToolList.add(new ToolModel("Stickers", R.drawable.ic_rotate_right, Type.STICKER));
        mToolList.add(new ToolModel("Rotate", R.drawable.ic_rotate_left, Type.ROTATE));
        mToolList.add(new ToolModel("Crop", R.drawable.ic_crop, Type.CROP));
        mToolList.add(new ToolModel("Draw", R.drawable.ic_brush_icon, Type.DRAWING));
        mToolList.add(new ToolModel("Image", R.drawable.ic_image_icon, Type.IMAGE));
        mToolList.add(new ToolModel("Text", R.drawable.ic_text_icon, Type.TEXT));
        notifyDataSetChanged();
    }

    public void removeTools(){
        clearItems();
        notifyDataSetChanged();
    }

}
