package uetsupport.dtui.uet.edu.uetsupport.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.dialog.StaticDialog;
import uetsupport.dtui.uet.edu.uetsupport.fragments.NewsFragment;
import uetsupport.dtui.uet.edu.uetsupport.models.News;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 09/12/2015.
 */
public class NewsAsyncTask extends AsyncTask<Void,Void,Integer> {

    Context context;
    String urlNews;
    int currentPage;
    String firstNewsTitleCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-title > span > a > div";
    String p1NewsTitleCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-";
    String p2NewsTitleCSS = ".views-row-";
    String p3NewsTitleCSS = " > div.views-field-title > span > a > div";

    String firstNewsDescCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-teaser > div > div";
    String p1NewsDescCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-";
    String p2NewsDescCSS = ".views-row-";
    String p3NewsDescCSS = " > div.views-field-teaser > div > div";

    String firstNewsImageCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-field-img-fid > span > a > img";
    String p1NewsImageCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-";
    String p2NewsImageCSS = ".views-row-";
    String p3NewsImageCSS = " > div.views-field-field-img-fid > span > a > img";

    String firstNewsLinkCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-view-node > span > a";
    String p1NewsLinkCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-";
    String p2NewsLinkCSS = ".views-row-";
    String p3NewsLinkCSS = " > div.views-field-view-node > span > a";

    ArrayList<News> newsArrayList;
    NewsFragment newsFragment;

    public NewsAsyncTask(Context c,NewsFragment n,int cp){
        context = c;
        newsFragment = n;
        newsArrayList = newsFragment.newsArrayList;
        currentPage = cp;
        if(cp ==1){
            urlNews = Util.urlNews;
        }else{
            urlNews = Util.urlNews+"?page="+(cp-1);
        }

//        Log.e("cxz",newsArrayList.size()+"  ---    ");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("cxz","loading news");
    }

    @Override
    protected Integer doInBackground(Void... params) {
//        newsArrayList = new ArrayList<>();
        try {

            Document doc = Jsoup.connect(urlNews).get();
//            Log.e("cxz",urlNews);

            //add an empty news
            News emptyNews = new News();
            newsArrayList.add(emptyNews);

            String firstNewsTitle = doc.select(firstNewsTitleCSS).first().text();
            String firstNewsDesc = doc.select(firstNewsDescCSS).first().text();
            String firstNewsLink = doc.select(firstNewsLinkCSS).first().attr("href");
            String firstNewsImage = null;
            if(doc.select(firstNewsImageCSS).first() != null) {
                firstNewsImage = doc.select(firstNewsImageCSS).first().attr("src");
                firstNewsImage = firstNewsImage.replaceAll(" ", "%20");
            }
            News n = new News(firstNewsTitle,firstNewsDesc,firstNewsImage,Util.domain+firstNewsLink);
            newsArrayList.add(n);
//            Log.e("cxz", "first " + firstNewsLink + " " + newsArrayList.size());

            for(int i=2;i<11;i++){
                String newsTitle = doc.select(p1NewsTitleCSS+i+p2NewsTitleCSS+oddOrEven(i)+p3NewsTitleCSS).first().text();
                String newsDesc = doc.select(p1NewsDescCSS+i+p2NewsDescCSS+oddOrEven(i)+p3NewsDescCSS).first().text();
                String newsImage=null;
                if(doc.select(p1NewsImageCSS+i+p2NewsImageCSS+oddOrEven(i)+p3NewsImageCSS).first()!=null){
                    newsImage = doc.select(p1NewsImageCSS+i+p2NewsImageCSS+oddOrEven(i)+p3NewsImageCSS).first().attr("src");
                    newsImage = newsImage.replaceAll(" ","%20");
                }
                String newsLink = doc.select(p1NewsLinkCSS+i+p2NewsLinkCSS+oddOrEven(i)+p3NewsLinkCSS).first().attr("href");

                n = new News(newsTitle,newsDesc,newsImage,Util.domain+newsLink);
                newsArrayList.add(n);
                Log.e("cxz", "-" + n.getContentNewsLink());
            }

            return Util.LOAD_OK;

        } catch (IOException e) {
            Log.e("cxz",e.getClass()+"-"+e.getMessage());
            return Util.LOAD_ERROR;
        }
    }

    private String getThumbnailByLink(String firstNewsLink) throws IOException {
        Document doc = Jsoup.connect(firstNewsLink).get();
        Element content = doc.select("#main > div.node > div.content").first();
        Elements es = content.getElementsByTag("img");
        String image = es.get(2).attr("src");
        return image;
    }

    String oddOrEven(int num){
        return num%2 == 1? "odd" : "even";
    }


    @Override
    protected void onPostExecute(Integer aInteger) {
        super.onPostExecute(aInteger);
        newsFragment.pbNews.setVisibility(View.GONE);
        newsFragment.flNews.setVisibility(View.VISIBLE);

        if(aInteger == Util.LOAD_OK){
            Util.CURRENT_PAGE_NEWS+=1;
            newsFragment.newsAdapter.notifyDataSetChanged();
            newsFragment.newsRecyclerView.invalidate();

//            if(!Util.UPDATE_SLIDER) {
                //slider layout
//                newsFragment.sliderLayout.removeAllSliders();
//                for (int i = 0; i < 4; i++) {
//                    DefaultSliderView dsv = new DefaultSliderView(context);
//                    dsv.image(newsArrayList.get(i).getImageLink()).setScaleType(BaseSliderView.ScaleType.CenterInside);
//                    newsFragment.sliderLayout.addSlider(dsv);
//                }
//                Util.UPDATE_SLIDER=true;
//            }
//        }else{
//            StaticDialog.showAlertDialog(context, "Load news error!");
        }
    }
}
