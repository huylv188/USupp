package com.unbelievable.uetsupport;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.unbelievable.uetsupport.common.CommonUtils;
import com.unbelievable.uetsupport.common.Constant;
import com.unbelievable.uetsupport.service.CustomAsyncHttpClient;
import com.unbelievable.uetsupport.service.Service;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DucAnhZ on 22/11/2015.
 */
public class GCMIntentService extends GCMBaseIntentService {

    public static final String PROJECT_NUMBER = "610765904940";

    public GCMIntentService() {
        super(PROJECT_NUMBER);
    }

    @Override
    protected void onError(Context arg0, String arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onMessage(Context ctx, Intent intent) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Constant.GCM_RECEIVED_ACTION);
        String message = intent.getExtras().getString("message");
        Bundle bundle = new Bundle();
        bundle.putString("msg", message);
        broadcastIntent.putExtras(bundle);
        ctx.sendBroadcast(broadcastIntent);
    }

    @Override
    protected void onRegistered(Context ctx, String regId) {
        registerToServer(ctx, regId);
        Log.d("Registered regId:", regId);
    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {
        // TODO Auto-generated method stub
        // send notification to your server to remove that regId
    }

    private void registerToServer(Context context, String regId) {
        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        CustomAsyncHttpClient client = new CustomAsyncHttpClient(context, "");
        String url = Service.ServerURL + "/data/register";
        RequestParams params = new RequestParams();
        params.put("registrationId", regId);
        client.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, String arg2) {
                if (arg0 == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(arg2);
                        String code = CommonUtils.getValidString(jsonObject.getString("code"));
                        String message = CommonUtils.getValidString(jsonObject.getString("message"));
                        if ("0".equals(code)) {
                            //TODO -> parse message from server;
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                Log.e("Send regId to server:", "Fail");
            }
        });
    }
}
