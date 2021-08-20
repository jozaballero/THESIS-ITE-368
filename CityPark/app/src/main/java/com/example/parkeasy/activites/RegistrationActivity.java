package com.example.parkeasy.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkeasy.R;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.DefaultResponse;
import com.example.parkeasy.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText _fullName, _phnNum, _address, _vehicleRegNo, _passwprd;
    private Spinner spinner;
    private TextView vhl_type;
    private String vehicleType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        _fullName = findViewById(R.id.editText_nmfull);
        spinner = findViewById(R.id.spinner1);
        _phnNum = findViewById(R.id.editText_phnnumReg);
        _address = findViewById(R.id.editText_add);
        _vehicleRegNo = findViewById(R.id.editText_VehicleRegNum);
        _passwprd = findViewById(R.id.editText_passwasdordsReg);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vehicle_type, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        findViewById(R.id.button_regdffdisterLogIn).setOnClickListener(this);
        findViewById(R.id.button_regSave).setOnClickListener(this);
  }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, HomepageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_regdffdisterLogIn:
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                break;
            case R.id.button_regSave:
                info_save();
                Toast.makeText(getApplicationContext(), "Saved",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        vehicleType = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),vehicleType,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void info_save() {
        String fullName = _fullName.getText().toString().trim();
        String phnNum = _phnNum.getText().toString().trim();
        String address = _address.getText().toString().trim();
        String vehicleRegNo = _vehicleRegNo.getText().toString().trim();
        String password = _passwprd.getText().toString().trim();

        if (fullName.isEmpty()){
            _fullName.setError("Name is required");
            _fullName.requestFocus();
            return;
        }else if (phnNum.isEmpty()){
            _phnNum.setError("Phone number is required");
            _phnNum.requestFocus();
            return;
        }else if (address.isEmpty()){
            _address.setError("Address is required");
            _address.requestFocus();
            return;
        }else if (vehicleRegNo.isEmpty()){
            _vehicleRegNo.setError("Registration number is required");
            _vehicleRegNo.requestFocus();
            return;
        }else if (password.isEmpty()){
            _passwprd.setError("Password is required");
            _passwprd.requestFocus();
            return;
        }else if (password.length()<5){
            _passwprd.setError("Password length should be 5 or more!");
            _passwprd.requestFocus();
            return;
        }else {
           // Intent intent = new Intent( RegistrationActivity.this, PhoneNumVerifyActivity.class );
          //  intent.putExtra( "fullName", fullName );
          ////  intent.putExtra( "phnNum", phnNum );
          //  intent.putExtra( "address", address );
          //  intent.putExtra( "vehicleType", vehicleType );
           // intent.putExtra( "vehicleRegNo", vehicleRegNo );
           // intent.putExtra( "password", password );
          //  startActivity( intent );
            Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().createUser(fullName, phnNum, address, vehicleType, vehicleRegNo, password);
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    if(response.body().isErr() == false){
                       Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                         startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        Toast.makeText(RegistrationActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(RegistrationActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }


                }

                @Override
               public void onFailure(Call<DefaultResponse> call, Throwable t) {
                   Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
               }
            });

        }







    }

}
