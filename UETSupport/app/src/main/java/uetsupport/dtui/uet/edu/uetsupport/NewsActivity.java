package uetsupport.dtui.uet.edu.uetsupport;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

import uetsupport.dtui.uet.edu.uetsupport.asynctask.LoadNewsAsyncTask;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 09/12/2015.
 */
public class NewsActivity extends BaseActivity implements ObservableScrollViewCallbacks,View.OnClickListener {

    public WebView wvContent;
    Toolbar toolbar;
    public ProgressBar pbLoading;
    CardView cvContent;
    private ImageView mImageView;
    private ObservableScrollView mScrollView;
    private ImageView ibBack;
    private TextView tvNewsTitle;
    private LinearLayout llNewsContent;
    public static String EXTRA = "extratrans";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        ibBack = (ImageView)findViewById(R.id.ibBack);
        ibBack.setOnClickListener(this);
        mImageView = (ImageView)findViewById(R.id.image);
        tvNewsTitle = (TextView)findViewById(R.id.tvNewsTitle);
        llNewsContent = (LinearLayout)findViewById(R.id.llNewsContent);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tvNewsTitle.setTransitionName("newsTitleTrans");
            mImageView.setTransitionName("newsImageTrans");
        }

        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary_500)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        //loadcontent
//        wvContent = (WebView)findViewById(R.id.wvContent);
//        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);
//
//        pbLoading.setIndeterminate(true);
//        Intent i = getIntent();
//        setTitle(i.getStringExtra("title"));
//        String url  = i.getStringExtra("url");
//        LoadNewsAsyncTask lnat = new LoadNewsAsyncTask(this,url);
//        lnat.execute();


        //load local
        int position= getIntent().getIntExtra("position",0);
        mImageView.setImageResource(getResources().getIdentifier("thumbnail" + position, "mipmap", getPackageName()));
        tvNewsTitle.setText(getStringByName("nt" + position));
        for(int i=1;i<4;i++){
            TextView tvContent = new TextView(this);
            String s =getStringByName("p"+i+"nc"+position);
            if(s!=null) {
                tvContent.setText(s);
                llNewsContent.addView(tvContent);
            }

            ImageView ivContent = new ImageView(this);
            int r = getResources().getIdentifier("pic"+i+"n"+position,"mipmap",getPackageName());
            if(r!=0) {
                ivContent.setImageResource(r);
                llNewsContent.addView(ivContent);
            }
        }
    }


    String getStringByName(String name){
        int  r = getResources().getIdentifier(name, "string", getPackageName());
        if(r!=0) return getString(r);
        else return null;
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void resizeImageInHtml(String html){
        Document doc = new Document(html);
        Elements es = doc.getElementsByTag("img");
        for(Element e : es){
            resizeImage(e);
        }
    }

    private void resizeImage(Element img){
        int width = Integer.valueOf(img.attr("width"));
        int height = Integer.valueOf(img.attr("height"));

        float ratio = width/height;

        width = 400;
        height = (int) (width/ratio);

        img.attr("width", String.valueOf(width));
        img.attr("height", String.valueOf(height));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary_500);
        float alpha = 0;//Math.min(1, (float) scrollY / mParallaxImageHeight);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibBack:
                supportFinishAfterTransition();
                break;

        }
    }
}
