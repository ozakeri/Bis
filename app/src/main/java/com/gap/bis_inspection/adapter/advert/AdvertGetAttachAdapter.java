package com.gap.bis_inspection.adapter.advert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gap.bis_inspection.R;
import com.gap.bis_inspection.db.objectmodel.AttachFile;

import java.util.List;

public class AdvertGetAttachAdapter extends RecyclerView.Adapter<AdvertGetAttachAdapter.CustomViewHolder> {

    private List<Bitmap> bitmapList = null;

    public AdvertGetAttachAdapter(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_show_attach_items, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        if (bitmapList != null) {
            holder.img_attach.setImageBitmap(bitmapList.get(position));
        }


    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_attach;
        private RelativeLayout relativeLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            img_attach = itemView.findViewById(R.id.img_attach);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

    private Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }
}
