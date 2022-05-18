package sg.edu.rp.c346.id21025553.l04_reservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declaring Variables
    DatePicker dp;
    TimePicker tp;
    EditText nameEdit, phoneEdit, paxEdit;
    TextView errorMsg;
    CheckBox cbNonSmoke;
    Button reset, confirm;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking variables to UI Elements
        dp = findViewById(R.id.datePicker);
        tp = findViewById(R.id.timePicker);
        nameEdit = findViewById(R.id.nameEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        paxEdit = findViewById(R.id.paxEdit);
        errorMsg = findViewById(R.id.errorMsg);
        cbNonSmoke = findViewById(R.id.checkSmoking);
        reset = findViewById(R.id.resetButton);
        confirm = findViewById(R.id.confirmButton);


        // Confirm Button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating variables for editText for easier reference
                String name = nameEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                String pax = paxEdit.getText().toString();

                // Creating variables for Date and Time for easier reference
                int year = dp.getYear();
                int month = dp.getMonth();
                int day = dp.getDayOfMonth();

                String hour = convertDate(tp.getCurrentHour());
                String minute = convertDate(tp.getCurrentMinute());

                // Initialising string response
                String response = "";

                // Code for the action
                if (!name.equals("") && !phone.equals("") && !pax.equals("")){


                    // For Name, Phone and Number of People to be displayed
                    response += "Reservation made for: \nName: " + name;
                    response += "\nPhone: " + phone;
                    response += "\nNumber Of People: " + pax;

                    // For Date and Time to be Displayed
                    response += String.format("\nDate: %d/%d/%d\nTime: %s:%s", day, month, year, hour, minute);

                    if (cbNonSmoke.isChecked()){
                        response += "\nYou have chosen to seat at a Non-smoking Area";
                    } else {
                        response += "\nYou have chosen to seat at a Smoking Area";
                    }

                } else {
                    response += "Please fill in all the information we require";
                }

                Toast.makeText(MainActivity.this,response, Toast.LENGTH_LONG).show();

            }
        });

        // Reset Button
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameEdit.setText("");
                phoneEdit.setText("");
                paxEdit.setText("");
                errorMsg.setText("");
                cbNonSmoke.setChecked(false);
                tp.setCurrentMinute(30);
                tp.setCurrentHour(19);
                dp.updateDate(2020, 6,1);

            }
        });

        // Restricting reservation time of day
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                if(hourOfDay < 8){
                    view.setCurrentHour(8);
                }
                if (hourOfDay > 20) {
                    view.setCurrentHour(20);
                }
            }
        });

        // Restricting reservation time and date to tomorrow
        dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if((dayOfMonth == 1 && year == 2020 && monthOfYear == 5) || (year == 2020 && monthOfYear < 5) || year < 2020){
                    view.updateDate(2020,6, 1);
                }
            }
        });

    }
    // For Proper Time Format
    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
}