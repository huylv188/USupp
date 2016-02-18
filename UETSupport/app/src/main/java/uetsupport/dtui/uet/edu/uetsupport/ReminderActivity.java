package uetsupport.dtui.uet.edu.uetsupport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.adapters.ReminderAdapter;
import uetsupport.dtui.uet.edu.uetsupport.dialog.ReminderDialog;
import uetsupport.dtui.uet.edu.uetsupport.models.Reminder;

/**
 * Created by huylv on 30-Dec-15.
 */
public class ReminderActivity extends AppCompatActivity{

    ListView lvReminder;
    ReminderAdapter reminderAdapter;
    ArrayList<Reminder> reminderArrayList;
    Toolbar toolbar;
    FloatingActionButton fabAddReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Nhắc nhở");

        fabAddReminder = (FloatingActionButton)findViewById(R.id.fabAddReminder);
        reminderArrayList = new ArrayList<>();
        reminderArrayList.add(new Reminder("10:10\n31/12/2015", "Ôn thi điện quang", true));
        reminderArrayList.add(new Reminder("10:20\n11/1/2016","Học nhóm Tin 4 trên thư viện tầng 4 với Tuyết, Đức, Lương",true));
        reminderArrayList.add(new Reminder("10:20\n11/1/2016","Thi giữa kỳ Tin 1",false));
        reminderArrayList.add(new Reminder("10:20\n11/1/2016","Thi cuối kỳ tin 2",true));

        lvReminder = (ListView)findViewById(R.id.lvReminder);
        reminderAdapter = new ReminderAdapter(this,reminderArrayList);
        lvReminder.setAdapter(reminderAdapter);


        fabAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderDialog rd = new ReminderDialog(ReminderActivity.this,new Reminder("","",false));
                rd.show();
            }
        });
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
