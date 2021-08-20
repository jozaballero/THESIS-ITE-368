package com.example.parkeasy.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkeasy.R;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.LoginResponse;
import com.example.parkeasy.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText phnNm, pass;
    private TextView forgetpasslink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog( this );
        phnNm =  findViewById(R.id.editText_phnNum);
        pass =  findViewById(R.id.editText_passwordLogIn);


        findViewById(R.id.button_forgetPasswortLogIn).setOnClickListener(this);
        findViewById(R.id.button_registerLogIn).setOnClickListener(this);
        findViewById(R.id.button_logInLogIn).setOnClickListener(this);

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
            case R.id.button_forgetPasswortLogIn:
                //progressDialog.setMessage( "Loading..." );
                // progressDialog.show();
                Intent intent = new Intent(LoginActivity.this, ForgetpassActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Clicked on Forget Password",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_registerLogIn:
                Intent inten1t = new Intent(LoginActivity.this, RegistrationActivity.class);
                inten1t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(inten1t);
                //startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                Toast.makeText(getApplicationContext(), "Clicked on Registration",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_logInLogIn:
                check_login();
        }
    }


    private void check_login() {
        String phnNum =phnNm.getText().toString().trim();
        String passw = pass.getText().toString().trim();
        if (phnNum.isEmpty()){
            phnNm.setError("Phone number!");
            phnNm.requestFocus();
            return;
        }else if (passw.isEmpty()){
            pass.setError("Password!");
            pass.requestFocus();
            return;
        }else if (passw.length()<5){
            pass.setError("Password length should be 5 or more!");
            pass.requestFocus();
            return;
        }else {
            Call <LoginResponse> call = RetrofitClient.getInstance().getApi().userLoginCheck(phnNum, passw);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    if(response.body().isError() == false){
                        SharedPrefManager.getInstance(LoginActivity.this).saveUser(loginResponse.getUser());
                        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
