package com.example.parkeasy.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parkeasy.R;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.DefaultResponse;
import com.example.parkeasy.models.User;
import com.example.parkeasy.storage.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Park extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private String ident1,ident2,ident3,ident4,ident5,ident6;
    private TextView parkname, total;
    private ProgressBar progressBar;
    private Button button, dateButton, timeButton;
    private FirebaseAuth Auth;
    private Spinner spinner;
    int hour, minute;
    private DatePickerDialog datePickerDialog;
    private String duration1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);
        initDatePicker();
        dateButton=findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        parkname = findViewById(R.id.textParkname);
        spinner = findViewById(R.id.spinner1);
        timeButton = findViewById(R.id.timePickerButton);
        total = findViewById(R.id.textTotal);
        Bundle b = getIntent().getExtras();
        ident1 = b.getString("parkName");
        ident2 = b.getString("price");
        parkname.setText(ident1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.durations, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return  makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day,month,year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month)+" "+day+" "+year;
    }

    private String getMonthFormat(int month) {
        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUN";
        if(month==7)
            return "JUL";
        if(month==8)
            return "AUG";
        if(month==9)
            return "SEP";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";
        return "JAN";
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,hour,minute,false);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        duration1 = adapterView.getItemAtPosition(i).toString();
        int value = Integer.parseInt(ident2);
        if(duration1.equals("1 Hour")){
            total.setText("Total Price: ₱"+value);
        }if(duration1.equals("2 Hours")){
            total.setText("Total Price: ₱"+value*2);
        }if(duration1.equals("3 Hours")){
            total.setText("Total Price: ₱"+value*3);
        }if(duration1.equals("4 Hours")){
            total.setText("Total Price: ₱"+value*4);
        }if(duration1.equals("5 Hours")){
            total.setText("Total Price: ₱"+value*5);
        }if(duration1.equals("6 Hours")){
            total.setText("Total Price: ₱"+value*6);
        }if(duration1.equals("7 Hours")){
            total.setText("Total Price: ₱"+value*7);
        }if(duration1.equals("8 Hours")){
            total.setText("Total Price: ₱"+value*8);
        }if(duration1.equals("9 Hours")){
            total.setText("Total Price: ₱"+value*9);
        }if(duration1.equals("10 Hours")){
            total.setText("Total Price: ₱"+value*10);
        }if(duration1.equals("11 Hours")){
            total.setText("Total Price: ₱"+value*11);
        }if(duration1.equals("Half a Day")){
            total.setText("Total Price: ₱"+value*12);
        }if(duration1.equals("Overnight")){
            total.setText("Total Price: ₱"+value*25);
        }
        Toast.makeText(adapterView.getContext(),duration1,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void validity_for_booking() {
        String date = dateButton.getText().toString().trim();
        String time = timeButton.getText().toString().trim();
        int value = Integer.parseInt(ident2);
        int values=0;
        if(duration1.equals("1 Hour")){
            values=value;
        }if(duration1.equals("2 Hours")){
            values=value*2;
        }if(duration1.equals("3 Hours")){
            values=value*3;
        }if(duration1.equals("4 Hours")){
            values=value*4;
        }if(duration1.equals("5 Hours")){
            values=value*5;
        }if(duration1.equals("6 Hours")){
            values=value*6;
        }if(duration1.equals("7 Hours")){
            values=value*7;
        }if(duration1.equals("8 Hours")){
            values=value*8;
        }if(duration1.equals("9 Hours")){
            values=value*9;
        }if(duration1.equals("10 Hours")){
            values=value*10;
        }if(duration1.equals("11 Hours")){
            values=value*11;
        }if(duration1.equals("Half a Day")){
            values=value*12;
        }if(duration1.equals("Overnight")){
            values=value*25;
        }
        User user = SharedPrefManager.getInstance(this).getUser();
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().bookNow(ident1, user.getFullName(), user.getPhoneNum(),date,time,duration1,String.valueOf(values), user.getVehicleType(), user.getVehicleRegNo());
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                Toast.makeText(getApplicationContext(), defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public void booking(View view) {
        validity_for_booking();
        Intent intent = new Intent(Park.this, HomepageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Saved",Toast.LENGTH_SHORT).show();

    }
}
