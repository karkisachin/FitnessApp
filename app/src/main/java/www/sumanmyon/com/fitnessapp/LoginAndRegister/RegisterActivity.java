package www.sumanmyon.com.fitnessapp.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import www.sumanmyon.com.fitnessapp.DataBase.LoginAndRegisterDBHelper;
import www.sumanmyon.com.fitnessapp.R;

public class RegisterActivity extends AppCompatActivity {

    LoginAndRegisterDBHelper helper;

    EditText editTextUserName, editTextPassword, editTextRePassword;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        helper = new LoginAndRegisterDBHelper(this);

        editTextUserName = (EditText)findViewById(R.id.register_user_name);
        editTextPassword = (EditText)findViewById(R.id.register_password);
        editTextRePassword = (EditText)findViewById(R.id.register_re_password);
        buttonRegister = (Button) findViewById(R.id.register_button);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isTrue = checkingEditTextFieldsAreNotNull();
                if(isTrue == true){
                    // boolean isInserted = myDB.insert(editTextName.getText().toString(),editTextSurName.getText().toString(),editTextMarks.getText().toString());
                    boolean isInserted = helper.insert(editTextUserName.getText().toString(),editTextPassword.getText().toString());
                    if(isInserted == true){
                        showMessage("Register Successfully");
                        redirect();
                    }else {
                        showMessage("Registeration failed");
                    }
                }
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
        } else if (TextUtils.isEmpty(editTextRePassword.getText())) {
            editTextRePassword.setError("Please enter marks");
            return false;
        } else {
            return true;
        }
    }

    private void redirect(){
        finish();
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void showMessage(String message) {
        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
    }
}
