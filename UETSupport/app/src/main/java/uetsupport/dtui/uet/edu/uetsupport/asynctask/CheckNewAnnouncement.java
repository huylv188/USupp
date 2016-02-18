package uetsupport.dtui.uet.edu.uetsupport.asynctask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import uetsupport.dtui.uet.edu.uetsupport.models.Announcement;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 16-Dec-15.
 */
public class CheckNewAnnouncement extends AsyncTask<Void,Void,Announcement> {

    Context context;
    String firstNewsLinkCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-view-node > span > a";
    String firstNewsTitleCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-title > span > a > div";
    String firstNewsDescCSS = "#main > div.view.view-taxonomy-term.view-id-taxonomy_term.view-display-id-page.view-dom-id-1 > div.view-content > div.views-row.views-row-1.views-row-odd.views-row-first > div.views-field-teaser > div > div";


    public CheckNewAnnouncement(Context c){
        context=c;
    }

    @Override
    protected Announcement doInBackground(Void... voids) {
        try {
            SharedPreferences sp = context.getSharedPreferences(Util.SPFIRSTLINK, Context.MODE_PRIVATE);
            String firstLinkSaved = sp.getString(Util.SPFIRSTLINKKEY, "null");

            Document doc = Jsoup.connect(Util.urlAnnouncement).get();
            String firstLinkCurrent = Util.domain + doc.select(firstNewsLinkCSS).attr("href");


            if(firstLinkCurrent.equals(firstLinkSaved)){
                return null;
            }else{

                String title = doc.select(firstNewsTitleCSS).first().text();
                String desc = doc.select(firstNewsDescCSS).first().text();
                Announcement a  = new Announcement(title,desc,firstLinkCurrent);
                return a;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Announcement aVoid) {
        super.onPostExecute(aVoid);
    }
}
