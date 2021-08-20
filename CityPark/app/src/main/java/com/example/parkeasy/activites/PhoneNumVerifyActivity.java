package com.example.parkeasy.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkeasy.R;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.DefaultResponse;
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

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneNumVerifyActivity extends AppCompatActivity   {
    private String ident1,ident2,ident3,ident4,ident5,ident6;
    private EditText editText;
    private TextView textView;
    private ProgressBar progressBar;
    private Button button;
    private String mVerificationId, num;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_num_verify);

        Bundle b = getIntent().getExtras();
        ident1 = b.getString("fullName");
        ident2 = b.getString("phnNum");
        ident3 = b.getString("address");
        ident4 = b.getString("vehicleType");
        ident5 = b.getString("vehicleRegNo");
        ident6 = b.getString("password");

        editText=findViewById(R.id.code);
        button=findViewById(R.id.btn);
        textView=findViewById(R.id.vieljkw);
        Auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

         num = "+63"+ident2;
        textView.setText(num);

        sendVerificationCode(num);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=editText.getText().toString().trim();
                if(code.isEmpty() || code.length()<6){
                    editText.setText("Invalid");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }


        });

        findViewById(R.id.button_regSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration_method();  //this method will go to signInWithPhoneAuthCredential(); phn verify issue
            }
        });

    }

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            Toast.makeText(PhoneNumVerifyActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(PhoneNumVerifyActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(PhoneNumVerifyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    private void registration_method() {
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().createUser(ident1, ident2, ident3, ident4, ident5, ident6);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.body().isErr() == false){
                    Toast.makeText(PhoneNumVerifyActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PhoneNumVerifyActivity.this, LoginActivity.class));
                }else {
                    Toast.makeText(PhoneNumVerifyActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

}