package uetsupport.dtui.uet.edu.uetsupport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import uetsupport.dtui.uet.edu.uetsupport.receiver.NotificationEventReceiver;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 15-Dec-15.
 */
public class SettingActivity extends AppCompatActivity {

    Switch notiSwitch;
    Toolbar toolbar;
    Boolean enableNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Setting");

        notiSwitch = (Switch)findViewById(R.id.swEnableNoti);
        SharedPreferences sp = getSharedPreferences(Util.SPSETTING,MODE_PRIVATE);
        enableNoti = sp.getBoolean(Util.SPSETTING_NOTI,true);
        notiSwitch.setChecked(enableNoti);
        notiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                enableNoti=b;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                saveNotiSetting(enableNoti);
                if(enableNoti){
                    NotificationEventReceiver.setupAlarm(getApplicationContext());
                }
                else{
                    NotificationEventReceiver.cancelAlarm(getApplicationContext());
                }
                finish();
                break;
        }
        return true;
    }

    private void saveNotiSetting(boolean b) {
        SharedPreferences sp = getSharedPreferences(Util.SPSETTING,MODE_PRIVATE);
        sp.edit().putBoolean(Util.SPSETTING_NOTI,b).commit();
    }
}
