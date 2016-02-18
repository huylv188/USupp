package com.unbelievable.uetsupport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.objects.News;
import com.unbelievable.uetsupport.objects.Recruitment;

import java.util.ArrayList;

/**
 * Created by huylv on 20/11/2015.
 */
public class RecruitmentAdapter extends ArrayAdapter<Recruitment> {

    Context context;
    ArrayList<Recruitment> recruitmentArrayList;

    public RecruitmentAdapter(Context context, ArrayList<Recruitment> recruitments) {
        super(context, -1, recruitments);
        this.context = context;
        this.recruitmentArrayList = recruitments;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_recruiment_item, parent, false);
        ImageView imCompanyLogo = (ImageView) rowView.findViewById(R.id.imCompanyLogo);
        TextView tvRecruitmentTitle = (TextView)rowView.findViewById(R.id.tvRecruitmentTitle);
        TextView tvRecruitmentJobType = (TextView)rowView.findViewById(R.id.tvRecruitmentJobType);
        TextView tvRecruitmentQuantity = (TextView) rowView.findViewById(R.id.tvRecruitmentQuantity);
        TextView tvRecruitmentSalary = (TextView) rowView.findViewById(R.id.tvRecruitmentSalary);

        tvRecruitmentTitle.setText(recruitmentArrayList.get(position).getTitle());
        tvRecruitmentJobType.setText("Vị trí: "+recruitmentArrayList.get(position).getJobType());
        tvRecruitmentQuantity.setText("Số lượng: "+recruitmentArrayList.get(position).getQuantity()+"");
        tvRecruitmentSalary.setText("Mức lương: "+recruitmentArrayList.get(position).getSalary());


        switch (position){
            case 0:
                imCompanyLogo.setImageResource(R.mipmap.topica);
                break;
            case 1:
                imCompanyLogo.setImageResource(R.mipmap.dtt);
                break;

            case 2:
                imCompanyLogo.setImageResource(R.mipmap.vtc);
                break;

            case 3:
                imCompanyLogo.setImageResource(R.mipmap.egame);
                break;
            case 4:
                imCompanyLogo.setImageResource(R.mipmap.misa);
                break;
        }
        return rowView;
    }

    @Override
    public int getCount() {
        return recruitmentArrayList.size();
    }
}
