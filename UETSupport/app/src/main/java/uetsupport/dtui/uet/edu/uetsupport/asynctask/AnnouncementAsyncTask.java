package uetsupport.dtui.uet.edu.uetsupport.asynctask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.dialog.StaticDialog;
import uetsupport.dtui.uet.edu.uetsupport.fragments.AnnounceFragment;
import uetsupport.dtui.uet.edu.uetsupport.models.Announcement;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 09/12/2015.
 */
public class AnnouncementAsyncTask extends AsyncTask<Void,Void,Integer> {

    private boolean REFRESHING=false;
    ArrayList<Announcement> announcementArrayList;
    AnnounceFragment announceFragment;
    Context context;

    String firstNewsTitleCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-title > span > a > div";
    String p1NewsTitleCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-";
    String p2NewsTitleCSS = ".views-row-";
    String p3NewsTitleCSS = " > div.views-field-title > span > a > div";

    String firstNewsDescCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-teaser > div > div";
    String p1NewsDescCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-";
    String p2NewsDescCSS = ".views-row-";
    String p3NewsDescCSS = " > div.views-field-teaser > div > div";

    String firstNewsLinkCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-view-node > span > a";
    String p1NewsLinkCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-";
    String p2NewsLinkCSS = ".views-row-";
    String p3NewsLinkCSS = " > div.views-field-view-node > span > a";

    String firstNewsDateCreatedCSS = "#main > div.node > span";
    int currentPage;
    String urlAnnouncement;

    public AnnouncementAsyncTask(Context c,AnnounceFragment af,int cp){
        context=c;
        announceFragment=af;
        announcementArrayList = announceFragment.announcementArrayList;
        currentPage = cp;
        if(cp==1){
            urlAnnouncement = Util.urlAnnouncement;
        }else if(cp==-1) {
            REFRESHING=true;
        }else  {
                urlAnnouncement = Util.urlAnnouncement+"?page="+(cp-1);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("cxz","loading announcement");
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            if(REFRESHING) {
                announcementArrayList.clear();
            }
            Document doc = Jsoup.connect(Util.urlAnnouncement).timeout(10000).get();

            String firstNewsTitle = doc.select(firstNewsTitleCSS).first().text();
            String firstNewsDesc = doc.select(firstNewsDescCSS).first().text();
            String firstNewsLink = doc.select(firstNewsLinkCSS).first().attr("href");
            Announcement n = new Announcement(firstNewsTitle,firstNewsDesc,Util.domain+firstNewsLink);
            announcementArrayList.add(n);

            for(int i=2;i<11;i++){
                String newsTitle = doc.select(p1NewsTitleCSS+i+p2NewsTitleCSS+oddOrEven(i)+p3NewsTitleCSS).first().text();
                String newsDesc = doc.select(p1NewsDescCSS+i+p2NewsDescCSS+oddOrEven(i)+p3NewsDescCSS).first().text();
                String newsLink = doc.select(p1NewsLinkCSS+i+p2NewsLinkCSS+oddOrEven(i)+p3NewsLinkCSS).first().attr("href");
                n = new Announcement(newsTitle,newsDesc,Util.domain+newsLink);
                announcementArrayList.add(n);
//                Log.e("cxz","-"+n.getImageLink()+" "+n.getContentNewsLink());
            }

            saveFirstLink(announcementArrayList.get(0).getAnnouncementLink());

            return Util.LOAD_OK;

        } catch (IOException e) {
            Log.e("cxz",e.getClass()+"-"+e.getMessage());
            return Util.LOAD_ERROR;
        }
    }

    private void saveFirstLink(String firstLink) {
        SharedPreferences sp = context.getSharedPreferences(Util.SPFIRSTLINK,Context.MODE_PRIVATE);
        sp.edit().putString(Util.SPFIRSTLINKKEY,firstLink).commit();
        Log.e("cxz",firstLink);
    }

    String oddOrEven(int num){
        return num%2 == 1? "odd" : "even";
    }


    @Override
    protected void onPostExecute(Integer i) {
        super.onPostExecute(i);
        announceFragment.pbAnnouncement.setVisibility(View.GONE);
        announceFragment.srlAnnouncement.setVisibility(View.VISIBLE);
        if(i== Util.LOAD_OK){
            if(REFRESHING){
                REFRESHING=false;
                announceFragment.srlAnnouncement.setRefreshing(false);
            }
            Util.CURRENT_PAGE_ANNOUNCEMENT +=1;
            announceFragment.announcementAdapter.notifyDataSetChanged();
            announceFragment.rvAnnouncement.invalidate();


        }else{
            StaticDialog.showAlertDialog(context, "Load announcement error!");
        }

    }
}
