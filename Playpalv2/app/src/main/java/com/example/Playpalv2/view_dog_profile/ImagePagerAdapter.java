package com.example.Playpalv2.view_dog_profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.R;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private CardProfileFragment mCtx;
    List<String> mImageUrls = new ArrayList<>();
    /*private String[] mImageUrls = {
            "https://firebasestorage.googleapis.com/v0/b/playpalv2-9e341.appspot.com/o/images%2F3a49d6eb-d895-4d25-99b3-bfda9b3bb794?alt=media&token=e4ca74e9-b75a-46ed-8fd5-1a1cac0a6c24",
            "https://firebasestorage.googleapis.com/v0/b/playpalv2-9e341.appspot.com/o/images%2Fa7f9208e-daca-4ab0-968e-cc73a7de21c1?alt=media&token=f030e4f9-442c-4d50-9de2-ecc0f1c52066",
            "https://firebasestorage.googleapis.com/v0/b/playpalv2-9e341.appspot.com/o/images%2F3b7d79b4-c2c3-4ca5-bd79-4ffd415c7737?alt=media&token=34a0a595-cdc5-4e8e-81f6-4db14643ee54",
            "https://firebasestorage.googleapis.com/v0/b/playpalv2-9e341.appspot.com/o/images%2F4ad6fd4e-be54-4bb3-a434-6e7cf487f69d?alt=media&token=5e3d0ea0-8499-43ba-8f11-ee65a1820a28"
    };*/

    public ImagePagerAdapter(CardProfileFragment mCtx, List<String> mImageUrls) {
        this.mCtx = mCtx;
        this.mImageUrls = mImageUrls;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       View view = LayoutInflater.from(mCtx.getContext()).inflate(R.layout.pager_layout,container, false);
       ImageView img = view.findViewById(R.id.iv_image);

       Glide.with(mCtx).load(mImageUrls.get(position)).into(img);

       container.addView(view);
       return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
