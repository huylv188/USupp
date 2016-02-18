package com.unbelievable.uetsupport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.objects.Department;

import java.util.ArrayList;

/**
 * Created by Nam on 11/22/2015.
 */
public class DepartmentAdapter extends ArrayAdapter<Department> {
    Context context;
    ArrayList<Department> departmentArrayList;

    ImageView imgAvatar;
    TextView tvTeacherName;
    TextView tvTeacherPhone;

    public DepartmentAdapter(Context context, ArrayList<Department> departments) {
        super(context, -1, departments);
        this.context = context;
        departmentArrayList = departments;
    }

    @Override
    public int getCount() {
        return departmentArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.list_teacher_item, parent, false);
        imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatar);
        imgAvatar.setVisibility(View.GONE);
        tvTeacherName = (TextView) rowView.findViewById(R.id.tvTeacherName);
        tvTeacherPhone = (TextView) rowView.findViewById(R.id.tvTeacherPhone);
        tvTeacherName.setText(departmentArrayList.get(position).name);
        tvTeacherPhone.setText(departmentArrayList.get(position).description);
        return rowView;
    }
}
