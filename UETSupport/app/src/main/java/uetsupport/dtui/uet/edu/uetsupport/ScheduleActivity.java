package uetsupport.dtui.uet.edu.uetsupport;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.adapters.AlarmScheduleAdapter;
import uetsupport.dtui.uet.edu.uetsupport.models.BuoiHoc;
import uetsupport.dtui.uet.edu.uetsupport.models.Course;
import uetsupport.dtui.uet.edu.uetsupport.models.Schedule;
import uetsupport.dtui.uet.edu.uetsupport.session.Session;

/**
 * Created by huylv on 09/12/2015.
 */
public class ScheduleActivity extends AppCompatActivity{

    int DAYINWEEK = 7;
    int PERIODINDAY = 10;
    TextView[][] tvTietHoc = new TextView[DAYINWEEK][PERIODINDAY];
    Schedule schedule;
    Toolbar toolbar;
    AlarmScheduleAdapter asa;
    ArrayList<Course> courseArrayList;
    ListView lvAlarmSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getResources().getString(R.string.thoikhoabieu));
        initView();
    }

    private void initView() {
        //schedule table
        for(int i=0;i<DAYINWEEK;i++){
            for(int j=0;j< PERIODINDAY;j++){
                String tvId = "t"+(i+2)+(j+1);
                int resId = getResources().getIdentifier(tvId,"id",getPackageName());
                tvTietHoc[i][j] = (TextView)findViewById(resId);
            }
        }

        schedule = Session.getCurrentUser().getSchedule();
        courseArrayList = schedule.getCourseArrayList();

        for(Course c:courseArrayList){
            ArrayList<BuoiHoc> cacBuoiHoc = c.getCacBuoiHoc();
            for(int i=0;i<cacBuoiHoc.size();i++){
                BuoiHoc bh = cacBuoiHoc.get(i);
                for(int j=0;j<bh.getSoTiet();j++){
                    tvTietHoc[bh.getThu()-2][bh.getTietDau()-1+j].setBackground(new ColorDrawable(Color.GREEN));
                }
                tvTietHoc[bh.getThu()-2][bh.getTietDau()-1].setText(c.getTenMonHoc());
            }
        }

        //alarm listview
        asa = new AlarmScheduleAdapter(this,courseArrayList);
        lvAlarmSchedule = (ListView)findViewById(R.id.lvAlarmSchedule);
        lvAlarmSchedule.setAdapter(asa);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
