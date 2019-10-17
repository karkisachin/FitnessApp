package www.sumanmyon.com.fitnessapp.RecommendationAndFeedBack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import www.sumanmyon.com.fitnessapp.R;


public class FeedBack extends AppCompatActivity {

    EditText email, feedback;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        email = findViewById(R.id.feedback_email_editText);
        feedback = findViewById(R.id.feedback_feedback_editText);
        done = findViewById(R.id.feedback_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkingFields()){
                    setMessage("Successfully");
                }
            }
        });
    }

    private void setMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    private boolean checkingFields() {
        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Please enter your email address.");
            return false;
        }else if(TextUtils.isEmpty(feedback.getText().toString())){
            feedback.setError("Please give any feedback.");
            return false;
        }else {
            return true;
        }
    }
}
