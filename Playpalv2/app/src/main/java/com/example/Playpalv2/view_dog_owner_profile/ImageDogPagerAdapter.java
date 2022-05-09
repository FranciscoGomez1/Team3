package com.example.Playpalv2.view_dog_owner_profile;

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

public class ImageDogPagerAdapter extends PagerAdapter {

    private DogOwnerViewProfile mCtx;
    List<String> mImageUrls = new ArrayList<>();

    public ImageDogPagerAdapter(DogOwnerViewProfile mCtx, List<String> mImageUrls) {
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
