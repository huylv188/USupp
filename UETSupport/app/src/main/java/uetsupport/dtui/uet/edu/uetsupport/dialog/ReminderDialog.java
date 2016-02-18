package uetsupport.dtui.uet.edu.uetsupport.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.models.Reminder;

/**
 * Created by Nam on 11/21/2015.
 */
public class ReminderDialog extends Dialog implements View.OnClickListener{
    private Activity activity;
    private Dialog dialog;
    Reminder reminder;
    Button btnCancel;
    Button btnSave;
    TimePicker tpReminder;
//    TextView tvDialogReminderTime;
//    TextView tvDialogReminderTitle;
//    TextView tvDay;

    public ReminderDialog(Context context,Reminder reminder) {
        super(context);
        this.reminder = reminder;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_reminder);
//        tvDialogReminderTime = (TextView) findViewById(R.id.tvDialogReminderTime);
//        tvDialogReminderTitle = (TextView) findViewById(R.id.tvDialogReminderTitle);
//        tvDay = (TextView) findViewById(R.id.tvDay);
        tpReminder = (TimePicker)findViewById(R.id.timePicker);

//        tvDialogReminderTime.setText(reminder.getTime() + "");
//        tvDialogReminderTitle.setText(reminder.getTitle());
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCancel){
            dismiss();
        }
        else {
            dismiss();
        }
        dismiss();
    }
}
