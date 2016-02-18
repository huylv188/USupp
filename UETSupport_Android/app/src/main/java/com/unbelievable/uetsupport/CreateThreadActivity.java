package com.unbelievable.uetsupport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.unbelievable.uetsupport.common.CommonUtils;
import com.unbelievable.uetsupport.common.Constant;
import com.unbelievable.uetsupport.common.UETSupportUtils;
import com.unbelievable.uetsupport.service.CustomAsyncHttpClient;
import com.unbelievable.uetsupport.service.Service;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DucAnhZ on 22/11/2015.
 */

public class CreateThreadActivity extends FragmentActivity implements View.OnClickListener {
    private EditText edtTitle;
    private EditText edtContent;
    private GridView gvImage;
    private ArrayList<Image> arrImage;
    private ArrayList<ChooseBitmap> arrBitmap;
    private File[] arrFile;
    private Image image;
    private ImageAdapter imageAdapter;
    private static final int REQ_PICK_IMAGE = 11803;
    private SharedPreferences sharedPreferences;
    private String title = "";
    private String content = "";
    private FrameLayout btnBackThread;
    private FrameLayout btnUpload;
    private RadioGroup rbtnStatus;
    private Integer status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thread);
        sharedPreferences = getSharedPreferences(Constant.nameSharedPreferences, Context.MODE_PRIVATE);
        edtContent = (EditText) findViewById(R.id.edtContent);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        rbtnStatus = (RadioGroup) findViewById(R.id.rbtnStatus);

        arrImage = new ArrayList<Image>();
        arrBitmap = new ArrayList<ChooseBitmap>();
        image = new Image(null, R.mipmap.btn_choose_image, 0, null);
        btnBackThread = (FrameLayout) findViewById(R.id.btnBackThread);
        btnUpload = (FrameLayout) findViewById(R.id.btnUpload);
        btnBackThread.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        arrImage.add(image);
        gvImage = (GridView) findViewById(R.id.gvImage);
        imageAdapter = new ImageAdapter(this, R.layout.item_gridview_image, arrImage);
        gvImage.setAdapter(imageAdapter);
        gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (arrImage.get(position).chooseImage == 1 || arrImage.get(position).chooseImage == 3) {
                    CommonUtils.showOkCancelDialog(CreateThreadActivity.this, "Xóa ảnh ?", "Xóa", "Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                if (arrImage.size() == 8 && arrImage.get(arrImage.size() - 1).chooseImage == 1 || arrImage.get(arrImage.size() - 1).chooseImage == 3) {
                                    arrImage.add(new Image(null, R.mipmap.btn_choose_image, 0, null));
                                }
                                if (arrImage.get(position).chooseImage == 1) {
                                    for (int i = 0; i < arrBitmap.size(); i++) {
                                        if (arrImage.get(position).uri == arrBitmap.get(i).uri) {
                                            try {
                                                arrBitmap.remove(i);
                                                arrImage.remove(position);
                                                imageAdapter.notifyDataSetChanged();

                                            } catch (Exception e) {
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                    });
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        rbtnStatus.setOnCheckedChangeListener(new StatusOnCheckedChange());
    }

    public void postThreads() {

        CustomAsyncHttpClient client = new CustomAsyncHttpClient(CreateThreadActivity.this, sharedPreferences.getString(Constant.token, ""));
        String url = Service.ServerURL + "/thread/create";
        RequestParams params = new RequestParams();
        params.put("title", title);
        params.put("content", content);
        params.put("status", status); // 1:show username, 2: anonymous <--- //TODO
        arrFile = new File[arrBitmap.size()];
        Collections.reverse(arrBitmap);
        for (int i = 0; i < arrBitmap.size(); i++) {
            try {
                arrFile[i] = UETSupportUtils.persistImage(CreateThreadActivity.this, arrBitmap.get(i).bitmap, new Random().nextInt(99999) + "");
            } catch (Exception e) {
            }
        }
        try {
            params.put("photos", arrFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        params.setHttpEntityIsRepeatable(true);
        params.setUseJsonStreamer(false);
        params.setForceMultipartEntityContentType(true);
        client.post(url, params, new TextHttpResponseHandler() {
            private ProgressDialog progressBar;
            @Override
            public void onStart() {
                super.onStart();
                try {
                    progressBar = new ProgressDialog(CreateThreadActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Loading ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                CommonUtils.showOkDialog(CreateThreadActivity.this, getString(R.string.dialog_content_server_problem), s, null);

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (i == 200) {
                    try {
                        JSONObject jObject = new JSONObject(s);
                        String success = CommonUtils.getValidString(jObject.getString("success"));
                        if ("1".equals(success)) {
                            Intent intent = new Intent("restartThreads");
                            CreateThreadActivity.this.sendBroadcast(intent);
                            finish();
                        } else {
                            String message = CommonUtils.getValidString(jObject.getString("message"));
                            CommonUtils.showOkDialog(CreateThreadActivity.this, getResources().getString(R.string.dialog_title_common), message, null);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CommonUtils.showOkDialog(CreateThreadActivity.this, getResources().getString(R.string.dialog_title_common), i + "", null);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnBackThread) {
            finish();;
        }
        if (v == btnUpload) {
            content = edtContent.getText().toString();
            title = edtTitle.getText().toString();
            postThreads();
        }
    }

    private class ImageAdapter extends ArrayAdapter<Image> {
        private Activity context;
        private int idLayout;
        private ArrayList<Image> arlImage;

        public ImageAdapter(Activity context, int resource, ArrayList<Image> list) {
            super(context, resource, list);
            this.context = context;
            this.idLayout = resource;
            this.arlImage = list;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = this.context.getLayoutInflater().inflate(idLayout, null);
            }
            ImageView btnDeleteImage = (ImageView) convertView.findViewById(R.id.btnDeleteImage);
            ImageView img = (ImageView) convertView.findViewById(R.id.imgThread);
            if (arlImage.get(i).chooseImage == 3 && arlImage.get(i).urlImage != null) {
                Picasso.with(CreateThreadActivity.this).load(arlImage.get(i).urlImage).placeholder(R.mipmap.photo_default).error(R.mipmap.photo_default).fit().centerInside().into(img);
                img.setClickable(false);
                img.setFocusable(false);
                img.setEnabled(false);
                btnDeleteImage.setVisibility(View.VISIBLE);
            }

            if (arlImage.get(i).uri != null && arlImage.get(i).chooseImage == 1) {
                Picasso.with(CreateThreadActivity.this).load(arlImage.get(i).uri).placeholder(R.mipmap.photo_default).error(R.mipmap.photo_default).fit().centerInside().into(img);
                img.setClickable(false);
                img.setFocusable(false);
                img.setEnabled(false);
                btnDeleteImage.setVisibility(View.VISIBLE);
            }

            if (arlImage.get(i).chooseImage != 1 && arlImage.get(i).chooseImage != 3 && arlImage.get(i).urlImage == null) {
                Picasso.with(CreateThreadActivity.this).load(arlImage.get(i).chooseImage).placeholder(R.mipmap.btn_choose_image).error(R.mipmap.btn_choose_image).fit().centerInside().into(img);
                img.setClickable(true);
                img.setFocusable(true);
                img.setEnabled(true);
                btnDeleteImage.setVisibility(View.GONE);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, REQ_PICK_IMAGE);
                    }
                });
            }


            return convertView;
        }
    }

    public class Image {
        public Uri uri;
        public int chooseImage;
        public int idImage;
        public String urlImage;

        public Image() {
        }

        public Image(Uri uri, int chooseImage, int idImage, String urlImage) {
            this.uri = uri;
            this.chooseImage = chooseImage;
            this.idImage = idImage;
            this.urlImage = urlImage;
        }
    }

    public class ChooseBitmap {
        public Uri uri;
        public Bitmap bitmap;

        public ChooseBitmap(Uri uri, Bitmap bitmap) {
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_PICK_IMAGE) {
            if (data != null) {
                try {
                    Uri selectedImageUri = data.getData();
                    Bitmap bmImg = UETSupportUtils.resizeBitmap(selectedImageUri, this);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(UETSupportUtils.getOrientation(CreateThreadActivity.this, selectedImageUri));
                    Bitmap srcBitmap = Bitmap.createBitmap(bmImg, 0, 0, bmImg.getWidth(),
                            bmImg.getHeight(), matrix, true);
                    image = new Image(selectedImageUri, 1, 0, null);
                    try {
                        arrImage.remove(arrImage.size() - 1);
                    } catch (Exception e) {
                    }
                    arrImage.add(image);
                    arrBitmap.add(new ChooseBitmap(selectedImageUri, srcBitmap));
                    arrImage.add(new Image(null, R.mipmap.btn_choose_image, 0, null));
                    if (arrImage.size() == 9) {
                        arrImage.remove(8);
                    }
                    imageAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class StatusOnCheckedChange implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rbnPublic:
                    status = 1;
                    break;
                case R.id.rbnAnonymous:
                    status = 0;
                    break;
                default:
                    status = 1;
                    break;
            }

        }
    }

}