package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    EditText weightInput;
    EditText heightInput;
    Button btncal;
    Button btnreset;
    TextView lastDate;
    TextView lastBMI;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weightInput=findViewById(R.id.weight);
        heightInput=findViewById(R.id.height);
        btncal=findViewById(R.id.calculate);
        btnreset=findViewById(R.id.reset);
        lastDate=findViewById(R.id.lastdate);
        lastBMI=findViewById(R.id.lastBMI);
        result=findViewById(R.id.result);



        btncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strWeight = weightInput.getText().toString();
                String strHeight = heightInput.getText().toString();
                float weight = Float.parseFloat(strWeight);
                float height = Float.parseFloat(strHeight);
                float bmi = weight/(height*height);

                if(bmi < 18.5){
                    result.setText("You are underweight");
                }else if(bmi >=18.5 || bmi<=24.9){
                    result.setText("You are normal");
                }else if(bmi >=25 || bmi<=29.9){
                    result.setText("You are overweight");
                }else{
                    result.setText("You are abese");
                }


                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH)+"/"+
                        (now.get(Calendar.MONTH)+1)+"/"+ now.get(Calendar.YEAR)+"  "+
                        now.get(Calendar.HOUR_OF_DAY)+":"+ now.get(Calendar.MINUTE);

                lastDate.setText("Last Calculated Date: "+datetime);
                lastBMI.setText(String.format("Last Calculated BMI: %.3f",bmi));
                heightInput.setText("");
                weightInput.setText("");
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightInput.setText("");
                weightInput.setText("");
                lastDate.setText("Last Calculated Date: ");
                lastBMI.setText(String.format("Last Calculated BMI: "));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Step 1a: Get the user input from the EditText and store it in a variable
        String date = lastDate.getText().toString();
        String BMI = lastBMI.getText().toString();

        //Step 1b: Obtain an instance of the SharedPreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor preEdit = prefs.edit();
        //Step 1d: Add the key-value pair
        preEdit.putString("name",date);
        preEdit.putString("bmi",BMI);

        //Step 1e: Call commit(); to save the change into SharedPreferences
        preEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        //Step 2b: Retrieve the saved data from the SharedPreferences object
        String msg = prefs.getString("name","");
        String BMI = prefs.getString("bmi","");
        //Step 2c: Update the UI element with the value
        lastDate.setText(msg);
        lastBMI.setText(BMI);

    }

}
