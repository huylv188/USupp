package com.unbelievable.uetsupport.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.objects.News;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huylv on 20/11/2015.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    private SliderLayout sliderLayoutBanner;
    Context context;
    ArrayList<News> newsArrayList;
    private DisplayImageOptions option;
    private CardView card_view;
    private TextView tvContent;

    public NewsAdapter(Context context, ArrayList<News> newsArrayList) {
        super(context, -1, newsArrayList);
        this.context=context;
        this.newsArrayList= newsArrayList;
        option = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true).cacheOnDisk(true).build();

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_news_item,parent,false);
        ImageView ivNews = (ImageView)rowView.findViewById(R.id.imNews);
        TextView tvNews = (TextView) rowView.findViewById(R.id.tvNews);
        LinearLayout pageView = (LinearLayout)rowView.findViewById(R.id.pageView);
        card_view = (CardView) rowView.findViewById(R.id.card_view);
        tvContent = (TextView) rowView.findViewById(R.id.tvContent);

        if (position == 0) {
            card_view.setVisibility(View.GONE);
            sliderLayoutBanner = new SliderLayout(getContext());
            pageView.setVisibility(View.VISIBLE);
            pageView.addView(sliderLayoutBanner);
            ivNews.setVisibility(View.GONE);
            tvNews.setVisibility(View.GONE);
            tvContent.setVisibility(View.GONE);
            setSliderLayoutBanner();
        } else {
            tvNews.setText(newsArrayList.get(position).title);
            tvContent.setText(newsArrayList.get(position).content);
            try {
                ImageLoader.getInstance().displayImage(newsArrayList.get(position).photo, ivNews, option);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return rowView;
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    private void setSliderLayoutBanner() {
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.mipmap.banner0);
        file_maps.put("2", R.mipmap.banner02);
        file_maps.put("3", R.mipmap.banner03);
        file_maps.put("4", R.mipmap.banner04);

        for (String name : file_maps.keySet()) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            sliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderLayoutBanner.addSlider(sliderView);
        }

        sliderLayoutBanner.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayoutBanner.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayoutBanner.setCustomAnimation(new DescriptionAnimation());
        sliderLayoutBanner.setDuration(4000);
        sliderLayoutBanner.startAutoCycle();
    }
}
