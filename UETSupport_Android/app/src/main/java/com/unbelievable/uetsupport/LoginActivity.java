package com.unbelievable.uetsupport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.unbelievable.uetsupport.common.CommonUtils;
import com.unbelievable.uetsupport.common.Constant;
import com.unbelievable.uetsupport.common.UETSupportUtils;
import com.unbelievable.uetsupport.service.CustomAsyncHttpClient;
import com.unbelievable.uetsupport.service.Service;

import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

/**
 * Created by DucAnhZ on 20/11/2015.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener{
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        sharedpreferences = this.getSharedPreferences(
                Constant.nameSharedPreferences, Context.MODE_PRIVATE);
        btnLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        editor = sharedpreferences.edit();
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            login(this, edtUsername.getText().toString(), edtPassword.getText().toString(), Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID));
        }
        if (v == tvForgotPassword) {
            CommonUtils.showOkDialog(this, getResources().getString(R.string.dialog_title_common), "Chưa cập nhật", null);
            //forgotPassword(this, "ducanh54@gmail.com");
            //TODO -> I'm using my email for demo
        }
    }

    private void login(final Activity activity, String username, String password, String deviceId) {
        if(!UETSupportUtils.networkConnected(this)) {
            return;
        }

        CustomAsyncHttpClient client = new CustomAsyncHttpClient(activity, "");
        String url = Service.ServerURL + "/student/login";
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("deviceId", deviceId);

        client.post(url, params, new TextHttpResponseHandler() {
            private ProgressDialog progressBar;

            @Override
            public void onStart() {
                super.onStart();
                try {
                    progressBar = new ProgressDialog(activity);
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
                            JSONObject jData = new JSONObject(jObject.getString("data"));
                            editor.putString(Constant.token, CommonUtils.getValidString(jData.getString("token")));
                            JSONObject jStudent = new JSONObject(jData.getString("student"));
                            editor.putString(Constant.username, CommonUtils.getValidString(jStudent.getString("username")));
                            editor.putString(Constant.fullname, CommonUtils.getValidString(jStudent.getString("fullname")));
                            editor.putString(Constant.email, CommonUtils.getValidString(jStudent.getString("email")));
                            editor.commit();
                            Intent intent = new Intent("restart");
                            getApplication().sendBroadcast(intent);
                            finish();
                        } else {
                            String message = CommonUtils.getValidString(jObject.getString("message"));
                            CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), message, null);
                        }
                    } catch (Exception e) {
                        CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), e.getMessage(), null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), statusCode + "\n" + responseString, null);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.dismiss();
            }
        });

    }

    private void forgotPassword(final Activity activity, String email) {
        if(!UETSupportUtils.networkConnected(this)) {
            return;
        }

        CustomAsyncHttpClient client = new CustomAsyncHttpClient(this, "");
        String url = Service.ServerURL + "/student/forgot-password";
        RequestParams params = new RequestParams();
        params.put("email", email);

        client.post(url, params, new TextHttpResponseHandler() {
            private ProgressDialog progressBar;

            @Override
            public void onStart() {
                super.onStart();
                try {
                    progressBar = new ProgressDialog(activity);
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
                            //TODO -> Show dialog, write email to revice new password
                        } else {
                            String message = CommonUtils.getValidString(jObject.getString("message"));
                            CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), message, null);
                        }
                    } catch (Exception e) {
                        CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), e.getMessage(), null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), statusCode + "\n" + responseString, null);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.dismiss();
            }
        });
    }


    private void getTeacher(final Activity activity) {
        if(!UETSupportUtils.networkConnected(this)) {
            return;
        }

        CustomAsyncHttpClient client = new CustomAsyncHttpClient(this, "");
        String url = Service.ServerURL + "/student/list";

        client.get(url, new TextHttpResponseHandler() {
            private ProgressDialog progressBar;

            @Override
            public void onStart() {
                super.onStart();
                try {
                    progressBar = new ProgressDialog(activity);
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
                            //TODO -> Show dialog, write email to revice new password
                        } else {
                            String message = CommonUtils.getValidString(jObject.getString("message"));
                            CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), message, null);
                        }
                    } catch (Exception e) {
                        CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), e.getMessage(), null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                CommonUtils.showOkDialog(activity, getResources().getString(R.string.dialog_title_common), statusCode + "\n" + responseString, null);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.dismiss();
            }
        });
    }

}
