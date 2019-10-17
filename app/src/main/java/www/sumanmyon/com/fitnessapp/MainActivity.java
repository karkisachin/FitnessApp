package www.sumanmyon.com.fitnessapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import fr.ganfra.materialspinner.MaterialSpinner;
import www.sumanmyon.com.fitnessapp.Chart.Chart;
import www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingDBHelper;
import www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB.Fruit;
import www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB.Juice;
import www.sumanmyon.com.fitnessapp.DataBase.LoginAndRegisterDBHelper;
import www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB.Meat;
import www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB.Others;
import www.sumanmyon.com.fitnessapp.DataBase.SharePreferenceForUserCredential;
import www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB.TeaCoffeeDrinks;
import www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB.Vegetables;
import www.sumanmyon.com.fitnessapp.LoginAndRegister.LoginActivity;
import www.sumanmyon.com.fitnessapp.Map.Map2;
import www.sumanmyon.com.fitnessapp.RecommendationAndFeedBack.FeedBack;
import www.sumanmyon.com.fitnessapp.RecommendationAndFeedBack.ShareSocialMedia;
import www.sumanmyon.com.fitnessapp.WaterNotification.AlarmBrodcastReceiver;
import www.sumanmyon.com.fitnessapp.WaterNotification.LoadNotification;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;

    ImageView imageView;
    EditText water_editText, fruit_editText, vegetable_editText, meat_editText, tea_editText, juice_editText, other_editText;
    //, launch_editText, snack_editText, dinner_editText;
    Button done;
    SliderLayout sliderLayout;

    MaterialSpinner fruiteSpinner, vegetableSpinner, meatSpinner, teaCoffeeSpinner, juiceSpinner, otherSpinner;
    TextView fruitTextView, vegetableTextView, meatTextView, teaCoffeeTextView, juiceTextView, otherTextView, total_textView;

    ArrayAdapter<String> fruiteAdapter, vegetableAdapter, meatAdapter, teaCoffeeAdapter, juiceAdapter, otherAdapter;
    Fruit fruit = new Fruit();
    Vegetables vegetables = new Vegetables();
    Meat meat = new Meat();
    TeaCoffeeDrinks teaCoffeeDrinks = new TeaCoffeeDrinks();
    Juice juice = new Juice();
    Others others = new Others();

    String water;
    int  f, v, m, t, j, o;
    int total = 0;


    LoginAndRegisterDBHelper helper;
    FoodTrackingDBHelper helper2;

    String username;
    String uid;

    LoadNotification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new LoginAndRegisterDBHelper(this);
        helper2 = new FoodTrackingDBHelper(this);

        //casting
        casting();

        //check login or not
        checklogin();

        //spinner for fruit, vegetable, meat
        fruite();
        vegitables();
        meat();
        teaCoffee();
        juice();
        others();


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isTrue = checkingEditTextFieldsAreNotNull2();
                if(isTrue == true && total >= 0){
                    boolean isInserted = helper2.insert(
                            water,
                            total_textView.getText().toString(),
                            uid);
                    if(isInserted == true){
                        total = 0;
                         showMessage("Inserted Successfully");
                    }else {
                        showMessage("Insertion failed");
                    }
                }else {
                    showMessage("Please enter form to store.");
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showMessage("Loading");
            }
        });

        //set username in naviagtion
        TextView view = nv.getHeaderView(0).findViewById(R.id.nav_user_name);
        view.setText(username);
    }

    private void fruite() {
        fruiteSpinner = findViewById(R.id.main_activity_material_spinner);
        fruiteAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,fruit.getFruit());
        fruiteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fruiteSpinner.setAdapter(fruiteAdapter);

        fruiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == -1){
                    fruitTextView.setText("fruit : calorie");
                }else {
                    fruitTextView.setText(fruit.getFruitServing()[position] + " : " + fruit.getFruitCalorie()[position]+" cal");
                    f = fruit.getFruitCalorie()[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void vegitables() {
        vegetableSpinner = findViewById(R.id.main_activity_material_spinner2);
        vegetableAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,vegetables.getVegetable());
        vegetableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vegetableSpinner.setAdapter(vegetableAdapter);

        vegetableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == -1){
                    vegetableTextView.setText("vegetable : calorie");
                }else {
                    vegetableTextView.setText(vegetables.getVegetableServing()[position] + " : " + vegetables.getVegetableCalorie()[position]+" cal");
                    v = vegetables.getVegetableCalorie()[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void meat(){
        meatSpinner = findViewById(R.id.main_activity_material_spinner3);
        meatAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,meat.getMeat());
        meatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meatSpinner.setAdapter(meatAdapter);

        meatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == -1){
                    meatTextView.setText("meat : calorie");
                }else {
                    meatTextView.setText(meat.getMeatServing()[position] + " : " + meat.getMeatCalorie()[position]+" cal");
                    m = meat.getMeatCalorie()[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void teaCoffee(){
        teaCoffeeSpinner = findViewById(R.id.main_activity_material_spinner4);
        teaCoffeeAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,teaCoffeeDrinks.getTeaCoffeeDrink());
        teaCoffeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teaCoffeeSpinner.setAdapter(teaCoffeeAdapter);

        teaCoffeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == -1){
                    teaCoffeeTextView.setText("drinks : calorie");
                }else {
                    teaCoffeeTextView.setText(teaCoffeeDrinks.getTeaCoffeeDrinkServing()[position] + " : " + teaCoffeeDrinks.getTeaCoffeeDrinkCalorie()[position]+" cal");
                    t = teaCoffeeDrinks.getTeaCoffeeDrinkCalorie()[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void juice(){
        juiceSpinner = findViewById(R.id.main_activity_material_spinner5);
        juiceAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,juice.getOthers());
        juiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        juiceSpinner.setAdapter(juiceAdapter);

        juiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == -1){
                    juiceTextView.setText("juice : calorie");
                }else {
                    juiceTextView.setText(juice.getOthersService()[position] + " : " + juice.getOthersCalorie()[position]+" cal");
                    j = juice.getOthersCalorie()[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void others(){
        otherSpinner = findViewById(R.id.main_activity_material_spinner6);
        otherAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,others.getOthers());
        otherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otherSpinner.setAdapter(otherAdapter);

        otherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == -1){
                    otherTextView.setText("others : calorie");
                }else {
                    otherTextView.setText(others.getOtherServing()[position] + " : " + others.getOtherCalories()[position]+" cal");
                    o = others.getOtherCalories()[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void casting() {
        water_editText = (EditText)findViewById(R.id.food_tracker_water_editText);
        fruit_editText = (EditText)findViewById(R.id.food_tracker_breakfast_editText);
        vegetable_editText = (EditText)findViewById(R.id.food_tracker_breakfast_editText2);
        meat_editText = (EditText)findViewById(R.id.food_tracker_breakfast_editText3);
        tea_editText = (EditText)findViewById(R.id.food_tracker_breakfast_editText4);
        juice_editText =(EditText)findViewById(R.id.food_tracker_breakfast_editText5);
        other_editText = (EditText)findViewById(R.id.food_tracker_breakfast_editText6);
        done = (Button)findViewById(R.id.food_tracker_done_editText);
        imageView = (ImageView) findViewById(R.id.calorie_img);
        fruitTextView = findViewById(R.id.food_tracker_breakfast_Text);
        vegetableTextView = findViewById(R.id.food_tracker_breakfast_Text2);
        meatTextView = findViewById(R.id.food_tracker_breakfast_Text3);
        teaCoffeeTextView = findViewById(R.id.food_tracker_breakfast_Text4);
        juiceTextView = findViewById(R.id.food_tracker_breakfast_Text5);
        otherTextView = findViewById(R.id.food_tracker_breakfast_Text6);
        total_textView = findViewById(R.id.food_tracker_Text);

        //casting
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.navigation);
        navigationListner();

        sliderLayout =findViewById(R.id.imageSlider);
        //set indicator animation by using SliderLayout.Animations.
        // :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :

        setSliderView();
    }

    private boolean checkingEditTextFieldsAreNotNull2() {
        water = water_editText.getText().toString();
        if (!TextUtils.isEmpty(fruit_editText.getText()) || fruiteSpinner.isSelected()) {
            total = total + f * Integer.parseInt(fruit_editText.getText().toString());
        }
        if (!TextUtils.isEmpty(vegetable_editText.getText()) || vegetableSpinner.isSelected()) {
            total = total + v *Integer.parseInt(vegetable_editText.getText().toString());
        }
        if (!TextUtils.isEmpty(meat_editText.getText()) || meatSpinner.isSelected()) {
            total = total +    m  * Integer.parseInt(meat_editText.getText().toString());
        }
        if (!TextUtils.isEmpty(tea_editText.getText()) || teaCoffeeSpinner.isSelected()) {
            total = total + t * Integer.parseInt(tea_editText.getText().toString());
        }
        if (!TextUtils.isEmpty(juice_editText.getText()) || juiceSpinner.isSelected()) {
            total = total + j * Integer.parseInt(juice_editText.getText().toString());
        }
        if (!TextUtils.isEmpty(other_editText.getText()) || otherSpinner.isSelected()) {
           total = total+ o * Integer.parseInt(other_editText.getText().toString());
        } else {
        }

        Log.d("database","true");
        total_textView.setText("Calorie : "+String.valueOf(total));
        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }

    private void checklogin() {
        String credential = new SharePreferenceForUserCredential(MainActivity.this)
                .getCredential();

        if(credential.equals("::::")){
            login();
        }else {
            String[] data = credential.split("::");
            boolean isHaved = helper.check(data[0],data[1]);
            if(isHaved == true){
                username = data[0];
                uid = data[2];
            }else {
                login();
            }
        }

    }

    private void navigationListner() {
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.report:
                        Intent reportIntent = new Intent(MainActivity.this, Chart.class);
                        reportIntent.putExtra("uid",uid);
                        startActivity(reportIntent);
                        break;
                    case R.id.map:
                        Intent foodTrackerIntent = new Intent(MainActivity.this, Map2.class);
                        foodTrackerIntent.putExtra("uid",uid);
                        startActivity(foodTrackerIntent);
                        //Toast.makeText(MainActivity.this, "map",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bmi:
                        Intent i = new Intent(MainActivity.this, BMIcalculator.class);
                        i.putExtra("uid",uid);
                        startActivity(i);
                        break;
                    case R.id.social_media:
                        new ShareSocialMedia(MainActivity.this).share();
                        break;
                    case R.id.feedback:
                        Intent feedbackIntent = new Intent(MainActivity.this, FeedBack.class);
                        startActivity(feedbackIntent);
                        break;
                    case R.id.logout:
                        new SharePreferenceForUserCredential(MainActivity.this)
                                .logoutCredential("","","");
                        checklogin();
                        break;
                    default:
                        return true;
                }

                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.water,menu);
        return true;
    }

    LoadNotification loadNotification = new LoadNotification(this);
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        if(item.getItemId() == R.id.water){
            //showMessage("Water");
            if(item.isChecked()){
               item.setChecked(false);
               loadNotification.stop();
            }else {
                item.setChecked(true);
                loadNotification.start();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSliderView() {
        for (int i = 0; i <= 2; i++) {
            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.vii);
                    sliderView.setDescription("Proper Exercise");
                    //sliderView.setImageUrl("https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.vi);
                    sliderView.setDescription("Green Vegetables and Fruits");
                    //sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.viii);
                    sliderView.setDescription("Sound Sleep");
                    //sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER);
            //sliderView.setDescription("setDescription " + (i + 1));

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }


    private void intent(){
        //finish();
        Intent i = new Intent(MainActivity.this, BMIcalculator.class);
        startActivity(i);
    }

    private void login(){
        finish();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

}
