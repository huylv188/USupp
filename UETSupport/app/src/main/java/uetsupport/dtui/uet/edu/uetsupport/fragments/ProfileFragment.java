package uetsupport.dtui.uet.edu.uetsupport.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.ExamScheduleActivity;
import uetsupport.dtui.uet.edu.uetsupport.ListPointActivity;
import uetsupport.dtui.uet.edu.uetsupport.LoginActivity;
import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.ReminderActivity;
import uetsupport.dtui.uet.edu.uetsupport.ScheduleActivity;
import uetsupport.dtui.uet.edu.uetsupport.SettingActivity;
import uetsupport.dtui.uet.edu.uetsupport.models.User;
import uetsupport.dtui.uet.edu.uetsupport.session.Session;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 20/11/2015.
 */
public class ProfileFragment extends Fragment implements OnClickListener{

    CircularImageView profilePicture;
    ImageView coverPhoto;
    TextView tvName;
    TextView tvVNUMail;
    ListView profileListView;
    ArrayList<String> profileArrayList;
    ProfileAdapter profileAdapter;
    RelativeLayout rlLogout;
    ScrollView svProfile;
    Button btLogin;
    TextView tvSetting;

    public ProfileFragment(){
        profileArrayList = new ArrayList<String>();
        profileArrayList.add(getResources().getString(R.string.thoikhoabieu));
        profileArrayList.add(getResources().getString(R.string.lichthi));
        profileArrayList.add(getResources().getString(R.string.ketquahoctap));
        profileArrayList.add(getResources().getString(R.string.nhacnho));
        profileArrayList.add(getResources().getString(R.string.logout));
    }

    public ProfileFragment(Context context){
        profileArrayList = new ArrayList<String>();
        profileArrayList.add(context.getResources().getString(R.string.thoikhoabieu));
        profileArrayList.add(context.getResources().getString(R.string.lichthi));
        profileArrayList.add(context.getResources().getString(R.string.ketquahoctap));
        profileArrayList.add(context.getResources().getString(R.string.nhacnho));
        profileArrayList.add(context.getResources().getString(R.string.caidat));
        profileArrayList.add(context.getResources().getString(R.string.logout));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        profilePicture = (CircularImageView) v.findViewById(R.id.profile_picture);
        coverPhoto = (ImageView)v.findViewById(R.id.cover_photo);
        tvName = (TextView)v.findViewById(R.id.tvName);
        tvVNUMail =(TextView)v.findViewById(R.id.tvVNUMail);
        svProfile = (ScrollView)v.findViewById(R.id.svProfile);
        rlLogout = (RelativeLayout)v.findViewById(R.id.rlLogout);
        btLogin = (Button)v.findViewById(R.id.btLogin);
        tvSetting = (TextView)v.findViewById(R.id.tvSetting);

        tvSetting.setOnClickListener(this);
        btLogin.setOnClickListener(this);

        profileListView = (ListView)v.findViewById(R.id.profileListView);
        profileAdapter = new ProfileAdapter(getContext(),profileArrayList);
        profileListView.setAdapter(profileAdapter);

        profileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i1 = new Intent(getActivity(), ScheduleActivity.class);
                        startActivity(i1);
                        break;
                    case 1:
                        Intent i2 = new Intent(getActivity(), ExamScheduleActivity.class);
                        startActivity(i2);
                        break;
                    case 2:
                        Intent i3 = new Intent(getActivity(), ListPointActivity.class);
                        startActivity(i3);
                        break;
                    case 3:
                        Intent i5 = new Intent(getActivity(), ReminderActivity.class);
                        startActivity(i5);
                        break;
                    case 4:
                        Intent i4 = new Intent(getActivity(), SettingActivity.class);
                        startActivity(i4);
                        break;
                    case 5:
                        showConfirmDialog();
                        break;
                    default:
                        break;
                }

            }
        });

        Log.e("cxz", "check login "+Util.LOGGED_IN);
        if(Util.LOGGED_IN){
            rlLogout.setVisibility(View.GONE);
            svProfile.setVisibility(View.VISIBLE);
            initView();
        }else{
            rlLogout.setVisibility(View.VISIBLE);
            svProfile.setVisibility(View.GONE);
        }
        return v;
    }

    private void showConfirmDialog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage(getResources().getString(R.string.confirmlogout));
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                logOut();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void logOut() {
        Session.removeUser();
        SharedPreferences sp  = getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.clear();
        spe.commit();
        rlLogout.setVisibility(View.VISIBLE);
        svProfile.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btLogin:
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(i,1);
                break;
            case R.id.tvSetting:
                Intent i2 = new Intent(getActivity(),SettingActivity.class);
                startActivity(i2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e("cxz","result "+resultCode+" "+requestCode+" "+Activity.RESULT_OK);
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                rlLogout.setVisibility(View.GONE);
                svProfile.setVisibility(View.VISIBLE);

                initView();
            }
        }
    }

    private void initView() {
        User u  = Session.getCurrentUser();
        tvName.setText(u.getFullName());
    }

    private class ProfileAdapter extends ArrayAdapter<String>{

        ArrayList<String> list;
        public ProfileAdapter(Context context,ArrayList<String> l) {
            super(context, -1);
            list = l;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.layout_profile_item,parent,false);
            ImageView ivNews = (ImageView)rowView.findViewById(R.id.ivProfileItem);
            TextView tvNews = (TextView) rowView.findViewById(R.id.tvProFileItem);

            switch (position){
                case 0:
                    ivNews.setImageResource(R.drawable.tkb);
                    break;
                case 1:
                    ivNews.setImageResource(R.drawable.lich_thi);
                    break;
                case 2:
                    ivNews.setImageResource(R.drawable.bang_diem);
                    break;
                case 3:
                    ivNews.setImageResource(R.drawable.nhac_nho);
                    break;
                case 4:
                    ivNews.setImageResource(R.drawable.settings);
                    break;
                case 5:
                    ivNews.setImageResource(R.drawable.logout);
                    break;
            }
            tvNews.setText(list.get(position));
            return rowView;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }
}
