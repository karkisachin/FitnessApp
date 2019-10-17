package www.sumanmyon.com.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import www.sumanmyon.com.fitnessapp.DataBase.BMIdbHelper;
import www.sumanmyon.com.fitnessapp.LoginAndRegister.LoginActivity;

public class BMIcalculator extends AppCompatActivity {

    EditText weightEditText, heightEditText;
    Button calculateBMIbutton, map;
    TextView resultBMItextView;

    String uid;
    BMIdbHelper bmIdbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        //casting
        castingViews();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null || !bundle.equals("")) {
            uid = bundle.getString("uid");
        }
        bmIdbHelper = new BMIdbHelper(this);

        //BMI
        calculateBMI();
    }


    private void castingViews() {

        weightEditText = (EditText)findViewById(R.id.weight);
        heightEditText = (EditText)findViewById(R.id.height);
        calculateBMIbutton = (Button)findViewById(R.id.calculate_BMI_Button);
        map = (Button)findViewById(R.id.map);
        resultBMItextView = (TextView)findViewById(R.id.textView_BMI_Result);
    }

    private void calculateBMI() {

        calculateBMIbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calculate BMI
                double weight, height;
                if(TextUtils.isEmpty(weightEditText.getText()) || TextUtils.isEmpty(heightEditText.getText())){
                    Toast.makeText(getApplicationContext(),"Input field should not be null",Toast.LENGTH_LONG).show();
                }else {
                    weight = Double.parseDouble(weightEditText.getText().toString());
                    height = Double.parseDouble(heightEditText.getText().toString());

                    double calculateBMI = weight/(height * height);

                    resultBMItextView.setText("Your BMI is : "+String.valueOf(calculateBMI));

                    boolean isInserted = bmIdbHelper.insert(
                            weightEditText.getText().toString(),
                            heightEditText.getText().toString(),
                            String.valueOf(calculateBMI),
                            uid);
                    if(isInserted == true){
                        showMessage("Stored Successfully");
                    }else {
                        showMessage("Failed to store");
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    redirect();
                }
            }
        });

    }

    private void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void redirect(){
        finish();
        Intent i = new Intent(BMIcalculator.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
