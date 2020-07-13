package sg.edu.rp.c346.id18016222.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight, etHeight;
    Button btnCalculate, btnReset;
    TextView tv, tv2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextW);
        etHeight = findViewById(R.id.editTextH);
        btnCalculate = findViewById(R.id.buttonC);
        btnReset = findViewById(R.id.buttonR);
        tv = findViewById(R.id.textView);
        tv2 =findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);

        etWeight.requestFocus();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float W = Float.parseFloat(etWeight.getText().toString());
                Float H = Float.parseFloat(etHeight.getText().toString());
                Float BMI = W/(H*H);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                String msg = "Last Calculated Date: " + datetime;
                String msg2 = "Last Calculated BMI: " + BMI;

                if (BMI >= 30) {
                    tv3.setText("You are obese");
                } else if (BMI >= 25) {
                    tv3.setText("You are overweight");
                } else if (BMI >= 18.5) {
                    tv3.setText("Your BMI is normal");
                } else {
                    tv3.setText("You are underweight");
                }

                tv.setText(msg);
                tv2.setText(msg2);
                prefEdit.commit();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etHeight.setText("");
                tv.setText("Last Calculated Date:");
                tv2.setText("Last Calculated BMI:");
                tv3.setText("");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();

        Float W = Float.parseFloat(etWeight.getText().toString());
        Float H = Float.parseFloat(etHeight.getText().toString());
        Float BMI = W/(H*H);
        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);
        String msg = "Last Calculated Date: " + datetime;
        String msg2 = "Last Calculated BMI: " + BMI;
        //Obtain a SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences editor for edit
        SharedPreferences.Editor prefEdit = prefs.edit();
        //Add key value pair
        prefEdit.putFloat("WH", BMI);
        prefEdit.putString("Date", msg);
        prefEdit.putString("BMI", msg2);
        //save changes
        prefEdit.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();

        //Obtain a SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Retrieve the saved data with the key "greetings" from SharedPreferences
        String msg = prefs.getString("Date", "Last Calculated Date:");
        String msg2 = prefs.getString("BMI", "Last Calculated BMI:");
        tv.setText(msg);
        tv2.setText(msg2);
    }
}