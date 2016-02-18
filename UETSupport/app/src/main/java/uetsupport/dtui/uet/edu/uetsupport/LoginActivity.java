package uetsupport.dtui.uet.edu.uetsupport;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uetsupport.dtui.uet.edu.uetsupport.asynctask.LoginAsyncTask;
import uetsupport.dtui.uet.edu.uetsupport.dialog.StaticDialog;

/**
 * Created by huylv on 08/12/2015.
 */
public class LoginActivity extends AppCompatActivity{

    Button btLogin;
    EditText etLoginStudentID;
    EditText etLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = (Button)findViewById(R.id.btLogin);
        etLoginStudentID = (EditText)findViewById(R.id.etLoginID);
        etLoginPassword = (EditText)findViewById(R.id.etLoginPassword);

//        etLoginPassword.setText("1");
//        etLoginStudentID.setText("1");

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentid = etLoginStudentID.getText().toString();
                String password = etLoginPassword.getText().toString();

                if (studentid.equals("") || password.equals("")) {
                    StaticDialog.showAlertDialog(LoginActivity.this, "Vui lòng nhập đầy đủ mã sinh viên và mật khẩu!");
                } else {
                    login(studentid, password);
                }
            }
        });
    }

    private void login(String studentid,String password) {
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this,studentid,password);
        loginAsyncTask.execute();

    }

}
