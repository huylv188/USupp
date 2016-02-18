package uetsupport.dtui.uet.edu.uetsupport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

import uetsupport.dtui.uet.edu.uetsupport.asynctask.LoadNewsAsyncTask;

/**
 * Created by huylv on 26-Dec-15.
 */
public class AnnouncementActivity extends AppCompatActivity {

    Toolbar toolbar;
    public ProgressBar pbLoading;
    public WebView wvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_announcement);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);
        wvContent = (WebView)findViewById(R.id.wvContent);

        pbLoading.setIndeterminate(true);
        Intent i = getIntent();
        setTitle(i.getStringExtra("title"));
        String url  = i.getStringExtra("url");
        LoadNewsAsyncTask lnat = new LoadNewsAsyncTask(this,url);
        lnat.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
