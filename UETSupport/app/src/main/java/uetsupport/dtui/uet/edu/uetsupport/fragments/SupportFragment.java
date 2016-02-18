package uetsupport.dtui.uet.edu.uetsupport.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.MainActivity;
import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.adapters.QuestionAdapter;
import uetsupport.dtui.uet.edu.uetsupport.models.Question;

/**
 * Created by huylv on 08/12/2015.
 */
public class SupportFragment  extends Fragment{

    RecyclerView rvQuestion;
    QuestionAdapter questionAdapter;
    ArrayList<Question> questionArrayList;
    SharedPreferences sp;
    EditText etInputSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_support,container,false);

        etInputSearch = (EditText)v.findViewById(R.id.etInputSearch);


        questionArrayList = new ArrayList<>();
        sp = getActivity().getSharedPreferences("FAQ", Context.MODE_PRIVATE);
        int size = 15;
        for(int i=0;i<size;i++){
            String q = sp.getString("q"+i,"null");
            String a = sp.getString("a"+i,"null");
            questionArrayList.add(new Question(q,a));
        }



        rvQuestion = (RecyclerView)v.findViewById(R.id.rvQuestion);
        questionAdapter = new QuestionAdapter(getActivity(),questionArrayList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvQuestion.setLayoutManager(llm);
        rvQuestion.setAdapter(questionAdapter);


        etInputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SupportFragment.this.questionAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        return v;
    }




}
