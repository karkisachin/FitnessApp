package www.sumanmyon.com.fitnessapp.Chart;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import www.sumanmyon.com.fitnessapp.DataBase.BMIdbHelper;
import www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingDBHelper;
import www.sumanmyon.com.fitnessapp.DataBase.MapDataBase;
import www.sumanmyon.com.fitnessapp.MainActivity;
import www.sumanmyon.com.fitnessapp.R;

public class Chart extends AppCompatActivity {

    private String uid;
    private boolean isData = true, isDataM = true, isDataBMI = true;
    private LineChart lineChart;
    ArrayList<Entry> yValues, yValuesM;

    FoodTrackingDBHelper helper2;
    MapDataBase mapDataBase;
    BMIdbHelper bmIdbHelper;

    String weight = "", height = "", BMI = "";
    int food = 0, run = 0;

    TextView BMI_Textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Bundle bundle = getIntent().getExtras();
        uid = bundle.getString("uid");

        lineChart = findViewById(R.id.lineChart);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setHorizontalScrollBarEnabled(true);

        BMI_Textview = findViewById(R.id.BMI_Textview);


        yValues = new ArrayList<>();
        yValuesM = new ArrayList<>();

        getValuesFromFoodTracking();
        getValueFromMap();
        getBMI();

        LineDataSet set1 = new LineDataSet(yValues, "Calorie");
        set1.setFillAlpha(110);
        set1.setValueTextSize(10f);
        set1.setColor(Color.BLUE);

        LineDataSet set2 = new LineDataSet(yValuesM, "Distance");
        set1.setFillAlpha(65);
        set1.setValueTextSize(10f);
        set1.setColor(Color.GREEN);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        if(isData == true)
            dataSets.add(set1);

        if( isDataM == true)
            dataSets.add(set2);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        calculateBMIForstrengthTraningOrCardioVascularExercise();
    }

    private void getValuesFromFoodTracking() {
        helper2 = new FoodTrackingDBHelper(this);
        Cursor cursor = helper2.getAllData(uid);
        if(cursor.getCount() == 0){  //TODO there is no data in database
            //showMessage("There is no data in database");
            isData = false;
            showMessage("There is no Food tracing data in database");
            return;
        }

        //TODO if cursor is at first of row or not
        if(!cursor.isFirst()){
            cursor.moveToFirst();
        }

        food = cursor.getCount();

        //TODO store data in yValues
        //StringBuffer buffer = new StringBuffer();
        int i = 0;
        do{
            String s =cursor.getString(2);      //0 = id, 1=water, 2=calorie, 3=uid
            String[] a = s.split(" : ");
            yValues.add(new Entry(i, Integer.valueOf(a[1])));
            i++;
        }while (cursor.moveToNext());
    }

    private void getValueFromMap() {
        mapDataBase = new MapDataBase(this);
        Cursor cursor = mapDataBase.getAllData(uid);
        if(cursor.getCount() == 0){  //TODO there is no data in database
            //showMessage("There is no data in database");
            isDataM = false;
            showMessage("There is no distance data in database");
            return;
        }

        //TODO if cursor is at first of row or not
        if(!cursor.isFirst()){
            cursor.moveToFirst();
        }

        //TODO store data in yValues
        int i = 0;
        do {
            //"Distance : "+String.valueOf(distance)+" m"
            String s =cursor.getString(1);      //0 = id, 1=distance, 2=uid
            if(!s.equals("")) {
                String[] a = s.split(" : ");
                String[] b = a[1].split(" ");
                float d = Float.parseFloat(b[0]);
                yValuesM.add(new Entry(i, d));
                i++;
            }
        } while (cursor.moveToNext());
    }

    private void getBMI() {
        bmIdbHelper = new BMIdbHelper(this);
        Cursor cursor = bmIdbHelper.getAllData(uid);
        if(cursor.getCount() == 0){  //TODO there is no data in database
            //showMessage("There is no data in database");
            isDataBMI = false;
            BMI = "";
            showMessage("Please calculate your BMI to analyse your health conditions.");
            return;
        }

        //TODO if cursor is at first of row or not
        if(!cursor.isFirst()){
            cursor.moveToLast();
        }

        run = cursor.getCount();
        //TODO store data in yValues
        weight = cursor.getString(1);
        height = cursor.getString(2);
        BMI = cursor.getString(3);
    }

    private void calculateBMIForstrengthTraningOrCardioVascularExercise() {
        if(food>5 || run>5) {
            if (!BMI.equals("") || !BMI.equals(null)) {
                float f = Float.parseFloat(BMI);
                if (f > 25.00) {
                    BMI_Textview.setText("You have to do strength training to maintain your health.");
                } else if (f < 18.50) {
                    BMI_Textview.setText("You have to do cardio vascular exercise to maintain your health.");
                } else {
                    BMI_Textview.setText("Your health is normal");
                }
            }
        }else {
            BMI_Textview.setText("There is no much data to analysis your health condition.");
        }
    }


    private void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}

