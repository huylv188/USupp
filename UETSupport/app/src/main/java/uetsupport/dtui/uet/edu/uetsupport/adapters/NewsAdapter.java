package uetsupport.dtui.uet.edu.uetsupport.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import uetsupport.dtui.uet.edu.uetsupport.MainActivity;
import uetsupport.dtui.uet.edu.uetsupport.NewsActivity;
import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.models.News;

/**
 * Created by huylv on 20/11/2015.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<News> newsArrayList;
    private SliderLayout sliderLayoutBanner;

    public NewsAdapter(Context context, ArrayList<News> newsArrayList) {
        this.context=context;
        this.newsArrayList= newsArrayList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        NewsViewHolder nvh = new NewsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
//        Log.e("czxz")
        if(position==0){
            holder.llHeader.setVisibility(View.VISIBLE);
            holder.cvContent.setVisibility(View.GONE);

            sliderLayoutBanner = new SliderLayout(context);
            holder.llHeader.addView(sliderLayoutBanner);
            setSliderLayoutBanner();
        }else {
            Log.e("cxz"," "+position);
            holder.tvNewsTitle.setText(context.getResources().getString(newsArrayList.get(position).tn));
            holder.ivNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, NewsActivity.class);
//                    i.putExtra("url", newsArrayList.get(position).getContentNewsLink());
//                    i.putExtra("title", newsArrayList.get(position).getTitleNews());
                        i.putExtra("position",position);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.tvNewsTitle.setTransitionName("newsTitleTrans");
                        holder.ivNews.setTransitionName("newsImageTrans");

                        Pair<View, String> pair1 = Pair.create((View)holder.ivNews, holder.ivNews.getTransitionName());
                        Pair<View, String> pair2 = Pair.create((View)holder.tvNewsTitle, holder.tvNewsTitle.getTransitionName());
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((MainActivity) context, pair1,pair2);
                        context.startActivity(i, options.toBundle());
                    }else{
                        context.startActivity(i);
                    }
                }
            });
            //set image for ivNews
//            if (newsArrayList.get(position).getImageLink() != null) {
//                Picasso.with(context)
//                        .load(newsArrayList.get(position).getImageLink())
//                        .into(holder.ivNews);
//            }

            holder.ivNews.setImageResource(newsArrayList.get(position).resThumb);
        }
    }

    private void setSliderLayoutBanner() {
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.mipmap.thumbnail1);
        file_maps.put("2", R.mipmap.thumbnail2);
        file_maps.put("3", R.mipmap.thumbnail3);
        file_maps.put("4", R.mipmap.thumbnail4);

        for (String name : file_maps.keySet()) {
            DefaultSliderView sliderView = new DefaultSliderView(context);
            sliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayoutBanner.addSlider(sliderView);
        }

        sliderLayoutBanner.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayoutBanner.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayoutBanner.setCustomAnimation(new DescriptionAnimation());
        sliderLayoutBanner.setDuration(4000);
        sliderLayoutBanner.startAutoCycle();
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView tvNewsTitle;
        TextView tvNewsDescription;
        ImageView ivNews;
        LinearLayout llHeader;
        CardView cvContent;

        public NewsViewHolder(View itemView) {
            super(itemView);
            cvContent = (CardView)itemView.findViewById(R.id.cvContent);
            llHeader = (LinearLayout)itemView.findViewById(R.id.llHeader);
            tvNewsTitle = (TextView)itemView.findViewById(R.id.tvNewsTitle);
            ivNews = (ImageView)itemView.findViewById(R.id.ivThumbnailNews);

        }

    }
}
