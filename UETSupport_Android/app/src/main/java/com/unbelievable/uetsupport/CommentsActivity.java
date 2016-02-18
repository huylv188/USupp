package com.unbelievable.uetsupport;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.unbelievable.uetsupport.adapter.CommentListAdapter;
import com.unbelievable.uetsupport.common.CommonUtils;
import com.unbelievable.uetsupport.common.Constant;
import com.unbelievable.uetsupport.objects.Comment;
import com.unbelievable.uetsupport.service.CustomAsyncHttpClient;
import com.unbelievable.uetsupport.service.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Nam on 11/21/2015.
 */
public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ListView commentList;
    CommentListAdapter commentAdapter;
    com.unbelievable.uetsupport.objects.Thread mainThread;
    ArrayList<Comment> comments;
    ImageView avatar;
    TextView tvUserName;
    TextView tvCreateTime;
    TextView tvContent;
    LinearLayout layout;
    ImageView photo;
    ImageView imgLike;
    ImageView imgDislike;
    ImageView imgComment;
    Button btnLike;
    Button btnDisLike;
    Button btnComment;
    EditText etComment;
    ImageView btnPost;

    private DisplayImageOptions option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        option = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true).cacheOnDisk(true).build();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Comment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CustomAsyncHttpClient client = new CustomAsyncHttpClient(this, "");
        String url = Service.ServerURL + getIntent().getExtras().getString("url");
        avatar = (ImageView) findViewById(R.id.imgAvatar);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvCreateTime = (TextView) findViewById(R.id.tvUploadedTime);
        tvContent = (TextView) findViewById(R.id.tvContent);
        photo = (ImageView) findViewById(R.id.photo);
        layout = (LinearLayout) findViewById(R.id.threadLayout);
        btnLike = (Button) findViewById(R.id.btnLike);
        btnDisLike = (Button) findViewById(R.id.btnDislike);
        btnComment = (Button) findViewById(R.id.btnAnswer);
        commentList = (ListView) findViewById(R.id.listComment);

        comments = new ArrayList<>();
        imgLike = (ImageView) findViewById(R.id.imgLike);
        imgDislike = (ImageView) findViewById(R.id.imgDislike);
        imgComment = (ImageView) findViewById(R.id.imgComment);
        etComment = (EditText) findViewById(R.id.etComment);
        btnPost = (ImageView) findViewById(R.id.btnPost);
        imgLike.setImageResource(R.mipmap.up);
        imgDislike.setImageResource(R.mipmap.down);
        imgComment.setImageResource(R.mipmap.comment);
        client.get(url, new TextHttpResponseHandler() {
            private ProgressDialog progressBar;

            @Override
            public void onStart() {
                super.onStart();
                try {
                    progressBar = new ProgressDialog(getApplicationContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Loading ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    try {
                        JSONObject jObject = new JSONObject(responseString);
                        JSONObject jData = jObject.getJSONObject("data");
                        JSONArray jArray = jData.getJSONArray("comments");
                        for (int i = 0; i < jArray.length(); i++) {
                            Comment comment = Comment.getComment(jArray.getJSONObject(i));
                            comments.add(comment);
                        }
                        mainThread = com.unbelievable.uetsupport.objects.Thread.getThread(jData);
                        tvUserName.setText(mainThread.userName);
                        tvCreateTime.setText(mainThread.createdTime);
                        tvContent.setText(mainThread.content);
                        btnLike.setText(mainThread.totalLike);
                        btnDisLike.setText(mainThread.totalUnlike.equals("null") ? "0" : mainThread.totalUnlike);
                        btnComment.setText((mainThread.comment == null) ? "0" : mainThread.comment);

                        if (mainThread.photos != null && mainThread.photos.length != 0) {
                            try {
                                ImageLoader.getInstance().displayImage(mainThread.photos[0].photoUrl, photo, option);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else photo.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    commentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                commentAdapter.notifyDataSetChanged();
                progressBar.dismiss();
            }
        });
        btnLike.setOnClickListener(this);
        btnDisLike.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        commentAdapter = new CommentListAdapter(this, comments, R.layout.comment_item_list);
        commentList.setAdapter(commentAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLike) {
            btnLike.setClickable(false);
            btnDisLike.setClickable(true);
            //TODO: Handle Like count
        }
        if (v == btnDisLike) {
            btnLike.setClickable(true);
            btnDisLike.setClickable(false);
            //TODO: Handle Dislike count
        }
        if (v == btnPost) {
            postComment();
        }
    }

    void postComment() {
        CustomAsyncHttpClient client = new CustomAsyncHttpClient(this, this.getSharedPreferences(Constant.nameSharedPreferences, MODE_PRIVATE).getString(Constant.token, ""));
        String url = Service.ServerURL + "/thread/comment/create";
        RequestParams params = new RequestParams();
        params.put("threadId", 1);
        params.put("content", "abc");
        etComment.setText("");
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                CommonUtils.showOkDialog(CommentsActivity.this, getString(R.string.dialog_content_server_problem), s, null);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    try {
                        JSONObject jObject = new JSONObject(responseString);
                        String success = CommonUtils.getValidString(jObject.getString("success"));
                        if ("1".equals(success)) {
                            //TODO ->
                        } else {
                            String message = CommonUtils.getValidString(jObject.getString("message"));
                            CommonUtils.showOkDialog(getApplication(), getResources().getString(R.string.dialog_title_common), message, null);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CommonUtils.showOkDialog(getApplication(), getResources().getString(R.string.dialog_title_common), statusCode + "", null);
                }
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


}
