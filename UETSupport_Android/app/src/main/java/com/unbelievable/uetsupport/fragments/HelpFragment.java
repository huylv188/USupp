package com.unbelievable.uetsupport.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.loopj.android.http.TextHttpResponseHandler;
import com.unbelievable.uetsupport.MainActivity;
import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.adapter.DepartmentAdapter;
import com.unbelievable.uetsupport.adapter.TeacherAdapter;
import com.unbelievable.uetsupport.common.CommonUtils;
import com.unbelievable.uetsupport.common.UETSupportUtils;
import com.unbelievable.uetsupport.objects.Department;
import com.unbelievable.uetsupport.objects.QuestionAnswer;
import com.unbelievable.uetsupport.objects.Teacher;
import com.unbelievable.uetsupport.service.CustomAsyncHttpClient;
import com.unbelievable.uetsupport.service.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Nam on 11/20/2015.
 */
public class HelpFragment extends Fragment implements View.OnClickListener{
    RadioButton btnTeacher;
    RadioButton btnOffice;
    RadioButton btnQA;
    ListView listTeacher;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    TeacherAdapter teacherAdapter;
    DepartmentAdapter departmentAdapter;

    MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        mainActivity = (MainActivity) getActivity();

        if (mainActivity.listDataChild.size() == 0) {
            parseQuestionAnswerFromServer();
        }
        if (mainActivity.teacherArrayList.size() == 0) {
            parseTeacherFromServer();
        }
        if (mainActivity.departmentArrayList.size() == 0) {
            parseDepartmentFromServer();
        }
        initView(v);
        return v;
    }

    public void initView(View v){
        btnTeacher = (RadioButton) v.findViewById(R.id.btnTeacher);
        btnOffice = (RadioButton) v.findViewById(R.id.btnOffice);
        btnQA = (RadioButton) v.findViewById(R.id.btnQA);
        listDataHeader = new ArrayList<>();
        parseTeacherFromServer();
        parseDepartmentFromServer();
        parseQuestionAnswerFromServer();
        listTeacher = (ListView) v.findViewById(R.id.listTeacher);
        expListView = (ExpandableListView) v.findViewById(R.id.listQA);
        teacherAdapter = new TeacherAdapter(getContext(), mainActivity.teacherArrayList);
        listAdapter = new com.unbelievable.uetsupport.adapter.ExpandableListAdapter(getActivity(),listDataHeader, mainActivity.listDataChild);
        departmentAdapter = new DepartmentAdapter(getContext(), mainActivity.departmentArrayList);
        btnTeacher.setOnClickListener(this);
        btnOffice.setOnClickListener(this);
        btnQA.setOnClickListener(this);

        listTeacher.setAdapter(teacherAdapter);
        expListView.setAdapter(listAdapter);
    }
    @Override
    public void onClick(View v) {
        if (v == btnTeacher){
            listTeacher.setVisibility(View.VISIBLE);
            expListView.setVisibility(View.GONE);
            listTeacher.setAdapter(teacherAdapter);
        }if (v == btnOffice){
            listTeacher.setVisibility(View.VISIBLE);
            expListView.setVisibility(View.GONE);
            listTeacher.setAdapter(departmentAdapter);
        }if (v == btnQA){
            listTeacher.setVisibility(View.GONE);
            expListView.setVisibility(View.VISIBLE);
        }
    }
    private void parseTeacherFromServer(){
        if (!UETSupportUtils.networkConnected(getActivity())){
            return;
        }
        CustomAsyncHttpClient client = new CustomAsyncHttpClient(getActivity(), "");
        String url = Service.ServerURL + "/data/teachers";
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                CommonUtils.showOkDialog(getActivity(), getResources().getString(R.string.dialog_title_common), statusCode + "\n" + responseString, null);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    mainActivity.teacherArrayList.clear();
                    try {
                        JSONObject jObject = new JSONObject(responseString);
                        String success = CommonUtils.getValidString(jObject.getString("success"));
                        if (success.equals("1")){
                            JSONArray jArray = jObject.getJSONArray("data");
                            for (int index = 0;index < jArray.length();index++){
                                Teacher teacher = Teacher.getTeacher(jArray.getJSONObject(index));
                                mainActivity.teacherArrayList.add(teacher);
                            }
                        }else {
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
                teacherAdapter.notifyDataSetChanged();
            }
        });
    }
    private void parseDepartmentFromServer(){
        if (!UETSupportUtils.networkConnected(getActivity())){
            return;
        }
        CustomAsyncHttpClient client = new CustomAsyncHttpClient(getActivity(), "");
        String url = Service.ServerURL + "/data/departments";
        client.get(url, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                CommonUtils.showOkDialog(getActivity(), getResources().getString(R.string.dialog_title_common), statusCode + "\n" + responseString, null);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    mainActivity.departmentArrayList.clear();
                    try {
                        JSONObject jObject = new JSONObject(responseString);
                        String success = CommonUtils.getValidString(jObject.getString("success"));
                        if (success.equals("1")){
                            JSONArray jArray = jObject.getJSONArray("data");
                            for (int index = 0;index < jArray.length();index++){
                                Department department = Department.getDepartment(jArray.getJSONObject(index));
                                mainActivity.departmentArrayList.add(department);
                            }
                        }else {
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
                teacherAdapter.notifyDataSetChanged();
            }
        });
    }

    private void parseQuestionAnswerFromServer() {
        if(!UETSupportUtils.networkConnected(getActivity())) {
            return;
        }
        CustomAsyncHttpClient client = new CustomAsyncHttpClient(getActivity(), "");
        String url = Service.ServerURL + "/data/question-answers";
        client.get(url, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    try {
                        JSONObject jObject = new JSONObject(responseString);
                        String success = CommonUtils.getValidString(jObject.getString("success"));
                        if ("1".equals(success)) {
                            JSONArray jarrData = new JSONArray(jObject.getString("data"));
                            for (int i = 0; i < jarrData.length(); i++) {
                                QuestionAnswer questionAnswer = QuestionAnswer.getQuestionAnswer(jarrData.getJSONObject(i));
                                listDataHeader.add(questionAnswer.question);
                                mainActivity.listDataChild.add(questionAnswer.answer);
                            }

                        } else {
                            String message = CommonUtils.getValidString(jObject.getString("message"));
                            CommonUtils.showOkDialog(getActivity(), getResources().getString(R.string.dialog_title_common), message, null);
                        }
                    } catch (Exception e) {
                        CommonUtils.showOkDialog(getActivity(), getResources().getString(R.string.dialog_title_common), e.getMessage(), null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                CommonUtils.showOkDialog(getActivity(), getResources().getString(R.string.dialog_title_common), statusCode + "\n" + responseString, null);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });

    }
}
