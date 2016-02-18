package com.unbelievable.uetsupport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.objects.Teacher;

import java.util.ArrayList;

/**
 * Created by Nam on 11/21/2015.
 */
public class TeacherAdapter extends ArrayAdapter<Teacher> {
    Context context;
    ArrayList<Teacher> teacherArrayList;
    ImageView imgAvatar;
    TextView tvTeacherName;
    TextView tvTeacherPhone;

    public TeacherAdapter(Context context,ArrayList<Teacher> teachers){
        super(context,-1,teachers);
        this.context =context;
        this.teacherArrayList = teachers;
    }

    @Override
    public int getCount() {
        return teacherArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.list_teacher_item, parent, false);
        imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatar);
        tvTeacherName = (TextView) rowView.findViewById(R.id.tvTeacherName);
        tvTeacherPhone = (TextView) rowView.findViewById(R.id.tvTeacherPhone);
        tvTeacherName.setText(teacherArrayList.get(position).getFullname());
        tvTeacherPhone.setText(teacherArrayList.get(position).getPhone());
        return rowView;
    }
}
