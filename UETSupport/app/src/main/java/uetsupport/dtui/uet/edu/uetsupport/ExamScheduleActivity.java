package uetsupport.dtui.uet.edu.uetsupport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uetsupport.dtui.uet.edu.uetsupport.adapters.AlarmExamScheduleAdapter;
import uetsupport.dtui.uet.edu.uetsupport.models.Examination;
import uetsupport.dtui.uet.edu.uetsupport.models.User;
import uetsupport.dtui.uet.edu.uetsupport.session.Session;

/**
 * Created by huylv on 10/12/2015.
 */
public class ExamScheduleActivity extends AppCompatActivity {

    Toolbar toolbar;
    public TextView[] tvMaLopMonHoc, tvTenMonHoc, tvSBD, tvNgayThi, tvGioThi, tvPhongThi, tvHinhThucThi;

    int numOfExam;
    ArrayList<Examination> examinationArrayList;
    ListView lvExamScheduleAlarm;
    AlarmExamScheduleAdapter aesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Lịch thi");

        User u = Session.getCurrentUser();
        examinationArrayList = u.getExamSchedule().getExaminationArrayList();
        numOfExam = examinationArrayList.size();
        initExamSchedule();

        aesa = new AlarmExamScheduleAdapter(this,examinationArrayList);
        lvExamScheduleAlarm = (ListView)findViewById(R.id.lvAlarmExamSchedule);
        lvExamScheduleAlarm.setAdapter(aesa);
    }

    private void initExamSchedule() {

        tvTenMonHoc = new TextView[numOfExam];
        tvMaLopMonHoc = new TextView[numOfExam];
        tvSBD = new TextView[numOfExam];
        tvGioThi = new TextView[numOfExam];
        tvNgayThi = new TextView[numOfExam];
        tvPhongThi = new TextView[numOfExam];
        tvHinhThucThi = new TextView[numOfExam];

        for (int i = 0; i < numOfExam; i++) {
            int resId =0;
            resId = getResources().getIdentifier("llExam" + (i + 1), "id", getPackageName());
            ((LinearLayout)findViewById(resId)).setVisibility(View.VISIBLE);

            resId = getResources().getIdentifier("tenMonHoc" + (i + 1), "id", getPackageName());
            tvTenMonHoc[i] = (TextView) findViewById(resId);
            tvTenMonHoc[i].setText(examinationArrayList.get(i).getTenMonHoc());
            tvTenMonHoc[i].setVisibility(View.VISIBLE);

            resId = getResources().getIdentifier("maLopMonHoc" + (i + 1), "id", getPackageName());
            tvMaLopMonHoc[i] = (TextView) findViewById(resId);
            tvMaLopMonHoc[i].setText(examinationArrayList.get(i).getLopMonHoc());

            resId = getResources().getIdentifier("soBaoDanh" + (i + 1), "id", getPackageName());
            tvSBD[i] = (TextView) findViewById(resId);
            tvSBD[i].setText(examinationArrayList.get(i).getSoBaoDanh()+"");

            resId = getResources().getIdentifier("ngayThi" + (i + 1), "id", getPackageName());
            tvNgayThi[i] = (TextView) findViewById(resId);
            tvNgayThi[i].setText(examinationArrayList.get(i).getNgayThi());

            resId = getResources().getIdentifier("gioThi" + (i + 1), "id", getPackageName());
            tvGioThi[i] = (TextView) findViewById(resId);
            tvGioThi[i].setText(examinationArrayList.get(i).getGioThi());

            resId = getResources().getIdentifier("phongThi" + (i + 1), "id", getPackageName());
            tvPhongThi[i] = (TextView) findViewById(resId);
            tvPhongThi[i].setText(examinationArrayList.get(i).getPhongThi());

            resId = getResources().getIdentifier("hinhThucThi" + (i + 1), "id", getPackageName());
            tvHinhThucThi[i] = (TextView) findViewById(resId);
            tvHinhThucThi[i].setText(examinationArrayList.get(i).getHinhThucThi());

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
