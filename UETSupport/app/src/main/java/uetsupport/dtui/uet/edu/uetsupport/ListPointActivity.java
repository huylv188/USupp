package uetsupport.dtui.uet.edu.uetsupport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by huylv on 19-Dec-15.
 */
public class ListPointActivity extends AppCompatActivity {
    Toolbar toolbar;
    WebView wvListPoint ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_point);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Kết quả học tập");


        wvListPoint = (WebView)findViewById(R.id.wvListPoint);
        String result="";
        StringBuilder buf=new StringBuilder();
        InputStream html= null;
        try {
            html = getAssets().open("listPoint.html");
            BufferedReader in= new BufferedReader(new InputStreamReader(html, "UTF-8"));
            String str;

            while ((str=in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
            result = buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mime = "text/html";
        String encoding = "UTF-8";
        wvListPoint.loadDataWithBaseURL(null, result, mime, encoding, null);
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
