package com.example.parkeasy.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.parkeasy.R;

public class ForgetpassActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        findViewById(R.id.button_need_to_reg).setOnClickListener(this);
        findViewById(R.id.button_back_to_LogIn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_need_to_reg:
                Intent intent = new Intent(ForgetpassActivity.this, RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.button_back_to_LogIn:
                Intent intenta = new Intent(ForgetpassActivity.this, LoginActivity.class);
                intenta.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenta);
                Toast.makeText(getApplicationContext(), "Back to LogIn",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
