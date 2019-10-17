package www.sumanmyon.com.fitnessapp.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import www.sumanmyon.com.fitnessapp.BMIcalculator;
import www.sumanmyon.com.fitnessapp.DataBase.LoginAndRegisterDBHelper;
import www.sumanmyon.com.fitnessapp.DataBase.SharePreferenceForUserCredential;
import www.sumanmyon.com.fitnessapp.MainActivity;
import www.sumanmyon.com.fitnessapp.R;
import www.sumanmyon.com.fitnessapp.WaterNotification.AlarmBrodcastReceiver;

public class LoginActivity extends AppCompatActivity {

    LoginAndRegisterDBHelper helper;

    EditText editTextUserName, editTextPassword;
    Button buttonLogin;
    TextView textViewRegisterAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new LoginAndRegisterDBHelper(this);

        editTextUserName = (EditText)findViewById(R.id.login_user_name);
        editTextPassword = (EditText)findViewById(R.id.login_password);
        buttonLogin = (Button) findViewById(R.id.login_button);
        textViewRegisterAcc = (TextView) findViewById(R.id.textView_register_acc);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isTrue = checkingEditTextFieldsAreNotNull();
                if(isTrue == true){
                    boolean isHaved = helper.check(editTextUserName.getText().toString(),editTextPassword.getText().toString());
                    if(isHaved == true){
                        showMessage("Login Successfully");

                        //put data in share preference
                        new SharePreferenceForUserCredential(LoginActivity.this)
                                .setCredential(editTextUserName.getText().toString(),editTextPassword.getText().toString(),helper.getID());

                        //set broadcast reciver

                        //redirect
                        redirect();
                    }else {
                        showMessage("Login failed. Please create account to login");
                    }
                }
            }
        });

        textViewRegisterAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private boolean checkingEditTextFieldsAreNotNull() {
        if (TextUtils.isEmpty(editTextUserName.getText())) {
            editTextUserName.setError("Please enter name");
            return false;
        } else if (TextUtils.isEmpty(editTextPassword.getText())) {
            editTextPassword.setError("Please enter surname");
            return false;
        } else {
            return true;
        }
    }

    private void showMessage(String message) {
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
    }

    private void createAccount(){
        finish();
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    private void redirect(){
        finish();
        Intent i = new Intent(LoginActivity.this, BMIcalculator.class);
        i.putExtra("uid",helper.getID());
        startActivity(i);
    }
}
