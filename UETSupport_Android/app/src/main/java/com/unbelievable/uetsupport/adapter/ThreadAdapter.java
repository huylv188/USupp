package com.unbelievable.uetsupport.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.unbelievable.uetsupport.R;

import java.util.ArrayList;

/**
 * Created by Nam on 11/21/2015.
 */
public class ThreadAdapter extends BaseAdapter {
    private Activity activity;
    private int layoutId;
    private ArrayList<com.unbelievable.uetsupport.objects.Thread> threads;

    public ThreadAdapter(Activity activity, int layoutId, ArrayList<com.unbelievable.uetsupport.objects.Thread> threads) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.threads = threads;
        option = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true).cacheOnDisk(true).build();

    }
    private DisplayImageOptions option;

    ImageView avatar;
    TextView tvUserName;
    TextView tvUploadedTime;
    TextView tvContent;
    ImageView photo;
    Button btnLike;
    Button btnDisLike;
    Button btnComment;

    @Override
    public int getCount() {
        return threads.size();
    }

    @Override
    public Object getItem(int i) {
        return threads.get(i);
    }

    @Override
    public long getItemId(int i) {
        return threads.get(i).threadId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(layoutId, viewGroup, false);
        }
        initView(view);

        com.unbelievable.uetsupport.objects.Thread thread = threads.get(i);
//        if (thread.avatar != null) avatar.setImageResource(thread.avatar);
        tvUserName.setText(thread.userName);

        tvUploadedTime.setText(thread.createdTime);
        tvContent.setText(thread.content);
        if (thread.photos!= null && thread.photos.length > 0) {
            try {
                ImageLoader.getInstance().displayImage(threads.get(i).photos[0].photoUrl, photo, option);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        btnLike.setText(thread.totalLike + "");
        btnDisLike.setText(thread.totalUnlike + "");
        btnComment.setText((thread.comment == null)? "0":thread.comment);
        return view;
    }

    public void initView(View view) {
        avatar = (ImageView) view.findViewById(R.id.imgAvatar);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvUploadedTime = (TextView) view.findViewById(R.id.tvUploadedTime);
        tvContent = (TextView) view.findViewById(R.id.tvContent);
        photo = (ImageView) view.findViewById(R.id.photo);
        btnLike = (Button) view.findViewById(R.id.btnLike);
        btnDisLike = (Button) view.findViewById(R.id.btnDislike);
        btnComment = (Button) view.findViewById(R.id.btnAnswer);
    }
}
