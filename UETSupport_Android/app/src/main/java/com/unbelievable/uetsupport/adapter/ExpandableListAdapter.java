package com.unbelievable.uetsupport.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.unbelievable.uetsupport.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Nam on 11/21/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    private List<String> listQuestion;
    private List<String> listAnswer;

    public ExpandableListAdapter(Activity activity, List<String> listQuestion,  List<String> listAnswer) {
        this.activity = activity;
        this.listQuestion = listQuestion;
        this.listAnswer = listAnswer;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listAnswer.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_qa_item,parent,false);
        }
        TextView tvAnswer = (TextView) convertView.findViewById(R.id.listAnswer);
        tvAnswer.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listQuestion.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listQuestion.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_qa_group, parent,false);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.listQuestion);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
