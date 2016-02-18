package com.unbelievable.uetsupport;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.unbelievable.uetsupport.common.CommonUtils;
import com.unbelievable.uetsupport.common.UETSupportUtils;
import com.unbelievable.uetsupport.objects.News;
import com.unbelievable.uetsupport.service.CustomAsyncHttpClient;
import com.unbelievable.uetsupport.service.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by huylv on 21/11/2015.
 */
public class SearchNewsActivity extends AppCompatActivity{
    Toolbar toolbar;
    EditText etSearchNews;
    ImageButton btSearchNews;
    ArrayList<News> resultArrayList;
    ResultAdapter resultAdapter;
    ListView resultListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etSearchNews = (EditText)findViewById(R.id.etSearchNews);
        btSearchNews = (ImageButton)findViewById(R.id.btSearchNews);
        btSearchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = etSearchNews.getText().toString();
                resultArrayList.clear();
                parseNewsFromServer(key);
            }
        });

        resultListView = (ListView)findViewById(R.id.searchResultListView);
        resultArrayList = new ArrayList<>();
        resultAdapter= new ResultAdapter(this);
        resultListView.setAdapter(resultAdapter);

    }



    //TODO
    private void parseNewsFromServer(String ks) {
        if (!UETSupportUtils.networkConnected(this)) {
            return;
        }

        CustomAsyncHttpClient client = new CustomAsyncHttpClient(this, "");
        String url = Service.ServerURL + "/data/informations";
        RequestParams params = new RequestParams();
        params.put("keySearch", ks);
        client.get(url, params, new TextHttpResponseHandler() {
            private ProgressDialog progressBar;

            @Override
            public void onStart() {
                super.onStart();
                try {
                    progressBar = new ProgressDialog(SearchNewsActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Loading ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    try {
                        JSONObject jObject = new JSONObject(responseString);
                        String success = CommonUtils.getValidString(jObject.getString("success"));
                        if ("1".equals(success)) {
                            JSONArray jarrData = new JSONArray(jObject.getString("data"));
                            for (int i = 0; i < jarrData.length(); i++) {
                                News news = News.getNews(jarrData.getJSONObject(i));
                                resultArrayList.add(news);
                            }

                        } else {
                            String message = CommonUtils.getValidString(jObject.getString("message"));
                            CommonUtils.showOkDialog(SearchNewsActivity.this, getResources().getString(R.string.dialog_title_common), message, null);
                        }
                    } catch (Exception e) {
                        CommonUtils.showOkDialog(SearchNewsActivity.this, getResources().getString(R.string.dialog_title_common), e.getMessage(), null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                CommonUtils.showOkDialog(SearchNewsActivity.this, getResources().getString(R.string.dialog_title_common), statusCode + "\n" + responseString, null);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                resultAdapter.notifyDataSetChanged();
                progressBar.dismiss();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ResultAdapter extends ArrayAdapter<News>{

        public ResultAdapter(Context context) {
            super(context, -1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            TextView tv = (TextView) rowView.findViewById(android.R.id.text1);
            if(tv == null) Log.e("cxz", "d");
            tv.setText(resultArrayList.get(position).title);

            return rowView;
        }

        @Override
        public int getCount() {
            return resultArrayList.size();
        }
    }

}
