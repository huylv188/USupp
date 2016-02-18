package com.unbelievable.uetsupport.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.TextHttpResponseHandler;
import com.unbelievable.uetsupport.CommentsActivity;
import com.unbelievable.uetsupport.CreateThreadActivity;
import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.adapter.ThreadAdapter;
import com.unbelievable.uetsupport.common.CommonUtils;
import com.unbelievable.uetsupport.common.UETSupportUtils;
import com.unbelievable.uetsupport.objects.Thread;
import com.unbelievable.uetsupport.service.CustomAsyncHttpClient;
import com.unbelievable.uetsupport.service.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Nam on 11/20/2015.
 */
public class SocialFragment extends Fragment implements View.OnClickListener {
    ListView socialListItem;
    ArrayList<com.unbelievable.uetsupport.objects.Thread> threadArrayList;
    ThreadAdapter itemAdapter;
    private FloatingActionButton btnCreateThread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_social, container, false);
        socialListItem = (ListView) v.findViewById(R.id.list_item);
        btnCreateThread = (FloatingActionButton) v.findViewById(R.id.btnCreateThread);
        btnCreateThread.setOnClickListener(this);
        threadArrayList = new ArrayList<>();
        parseThreadFromServer();
        itemAdapter = new ThreadAdapter(this.getActivity(), R.layout.list_thread_item, threadArrayList);
        socialListItem.setAdapter(itemAdapter);
        socialListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommentsActivity.class);
                intent.putExtra("url","/thread/list/" + threadArrayList.get(position).threadId);
                startActivity(intent);
            }
        });
        IntentFilter filter = new IntentFilter("restartThreads");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                parseThreadFromServer();
            }
        };
        getActivity().registerReceiver(broadcastReceiver, filter);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreateThread) {
            Intent intent = new Intent(getActivity(), CreateThreadActivity.class);
            startActivity(intent);
        }
    }

    public void parseThreadFromServer() {

        CustomAsyncHttpClient client = new CustomAsyncHttpClient(getActivity(), "");
        String url = Service.ServerURL + "/thread/list";
        client.get(url, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                CommonUtils.showOkDialog(getActivity(), getResources().getString(R.string.dialog_title_common), statusCode + "\n" + responseString, null);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    try {
                        JSONObject jObject = new JSONObject(responseString);
                        String success = CommonUtils.getValidString(jObject.getString("success"));
                        if (success.equals("1")) {
                            JSONArray jArray = jObject.getJSONArray("data");
                            threadArrayList = new ArrayList<>();
                            for (int index = 0; index < jArray.length(); index++) {
                                threadArrayList.add(Thread.getThread(jArray.getJSONObject(index)));
                            }
                            itemAdapter = new ThreadAdapter(getActivity(), R.layout.list_thread_item, threadArrayList);
                            socialListItem.setAdapter(itemAdapter);
                        } else {
                            String message = CommonUtils.getValidString(jObject.getString("message"));
                            CommonUtils.showOkDialog(getActivity(), getResources().getString(R.string.dialog_title_common), message, null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });

    }


}
