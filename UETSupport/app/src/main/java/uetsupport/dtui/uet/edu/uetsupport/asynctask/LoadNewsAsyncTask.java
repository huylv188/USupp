package uetsupport.dtui.uet.edu.uetsupport.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.AnnouncementActivity;
import uetsupport.dtui.uet.edu.uetsupport.NewsActivity;
import uetsupport.dtui.uet.edu.uetsupport.models.Announcement;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 09/12/2015.
 */
public class LoadNewsAsyncTask extends AsyncTask<Void,Void,String> {

    ProgressDialog pd;
    Context context;
    String url;
    AnnouncementActivity newsActivity;

    public LoadNewsAsyncTask(Context c, String url){
        this.url=url;
        context=c;
        newsActivity = (AnnouncementActivity)c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            Document doc  = Jsoup.connect(url).get();

            String html = doc.select("#main > div.node > div.content").remove(0).html();
            return html;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        newsActivity.pbLoading.setVisibility(View.GONE);
        newsActivity.wvContent.setVisibility(View.VISIBLE);

        String mime = "text/html";
        String encoding = "UTF-8";
        newsActivity.wvContent.loadDataWithBaseURL(Util.domain, s, mime, encoding, null);
    }
}
