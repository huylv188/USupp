package uetsupport.dtui.uet.edu.uetsupport.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import uetsupport.dtui.uet.edu.uetsupport.adapters.NewsAdapter;
import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.asynctask.NewsAsyncTask;
import uetsupport.dtui.uet.edu.uetsupport.models.News;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 20/11/2015.
 */
@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment{

    public  SliderLayout sliderLayout;
    public RecyclerView newsRecyclerView;
    public ArrayList<News> newsArrayList;
    public NewsAdapter newsAdapter;
    Context context;
    public ProgressBar pbNews;
    public FrameLayout flNews;
    LinearLayout llHeader;
    GridLayoutManager glm;
    public HashMap<String,Integer> file_maps;

    /////////////////
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    ////////////////////////

    public NewsFragment(){
        newsArrayList = new ArrayList<>();
    }

    @SuppressLint("ValidFragment")
    public NewsFragment(Context c) {
        context = c;
        newsArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news,container,false);

        newsRecyclerView = (RecyclerView)v.findViewById(R.id.rvNews);
        newsAdapter = new NewsAdapter(getContext(),newsArrayList);
        pbNews = (ProgressBar)v.findViewById(R.id.pbNews);
        flNews = (FrameLayout)v.findViewById(R.id.flNews);

        glm = new GridLayoutManager(getActivity(),2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                }
                return 1;
            }
        });
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(glm);
        newsRecyclerView.setAdapter(newsAdapter);

        if(newsArrayList.size()>0){
            flNews.setVisibility(View.VISIBLE);
            pbNews.setVisibility(View.GONE);
        }else{
            flNews.setVisibility(View.GONE);
            pbNews.setVisibility(View.VISIBLE);
        }

//        addListener();
        return v;
    }



    private void addListener() {
        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = newsRecyclerView.getChildCount();
                totalItemCount = glm.getItemCount();
                firstVisibleItem = glm.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    Log.e("cxz", "end called");

                    // Do something
                    NewsAsyncTask nat = new NewsAsyncTask(getActivity(),NewsFragment.this, Util.CURRENT_PAGE_NEWS);
                    nat.execute();
                    loading = true;
                }
            }
        });
    }



}
