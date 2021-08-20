package com.example.parkeasy.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.parkeasy.R;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.ParkOwnerSensorResponse;
import com.example.parkeasy.models.SensorData;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkListItemActivity extends AppCompatActivity implements View.OnClickListener {

    //public static java.lang.Class<?> Class;
    private TextView parkNam, add, phonenNum, capacity, avail,pric, bookNow, backList;
    private String ident1, ident2, ident3,ident4, ident5, _ident2, _ident3, _ident4;
    private String thisDate;
    private int n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_list_item);

        parkNam = findViewById(R.id.textViewParkName);
        add = findViewById(R.id.textView3Address);
        phonenNum = findViewById(R.id.textViewPhoneNum);
        capacity = findViewById(R.id.textViewCapacity);
        avail = findViewById(R.id.textViewAvailpljavail);
        pric = findViewById(R.id.textViewPrice);

        findViewById(R.id.bookNow).setOnClickListener(this);
        findViewById(R.id.backToList).setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        ident1 = b.getString("parkName");
        ident2 = b.getString("price");
        ident3 = b.getString("phn");
        ident4 = b.getString("cap");
        ident5 = b.getString("add");
        add.setText(ident5);


        parkNam.setText(ident1);
        pric.setText(ident2);
        phonenNum.setText(ident3);
        capacity.setText(ident4);


        SimpleDateFormat currentDate = new SimpleDateFormat( "yyyy-MM-dd" );
        Date todayDate = new Date();
        thisDate = currentDate.format( todayDate );

        Call<ParkOwnerSensorResponse> call = RetrofitClient.getInstance().getApi().getParkOwnerSensorData(ident1);
        call.enqueue(new Callback<ParkOwnerSensorResponse>() {
            @Override
            public void onResponse(Call<ParkOwnerSensorResponse> call, Response<ParkOwnerSensorResponse> response) {

                ParkOwnerSensorResponse ownerSensorResponse = response.body();
                SensorData sensorData = ownerSensorResponse.getSensorData();

                if (ownerSensorResponse.isError() == false){
                    if( sensorData==null || sensorData.getAvailable().isEmpty() || sensorData.getAvailable().equals(null) ){
                        avail.setText("xx");
                    }else {
                        avail.setText(sensorData.getAvailable());
                        n = Integer.valueOf(sensorData.getAvailable());
                        if (n == 0){
                            //  findViewById(R.id.bookNow).setVisibility(View.INVISIBLE);
                            findViewById(R.id.bookNow).setVisibility(View.GONE);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ParkOwnerSensorResponse> call, Throwable t) {

            }
        });





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bookNow:
                validity_for_booking();
                //Intent intent = new Intent(ParkListItemActivity.this, HomepageActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
                break;
            case R.id.backToList:
                finish();
                break;
        }
    }

    private void validity_for_booking() {
        Intent intent = new Intent( ParkListItemActivity.this, Park.class );
        intent.putExtra( "parkName", ident1 );
        intent.putExtra( "price", ident2 );
        startActivity( intent );
        // if (n > 0){
        //        User user = SharedPrefManager.getInstance(this).getUser();
         //       Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().bookNow(ident1, user.getFullName(), user.getPhoneNum(), user.getVehicleType(), user.getVehicleRegNo() );
            //    call.enqueue(new Callback<DefaultResponse>() {
           //         @Override
        //        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
        //           DefaultResponse defaultResponse = response.body();
        //            Toast.makeText(getApplicationContext(), defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
        //             finish();
        //         }

        //         @Override
                            //         public void onFailure(Call<DefaultResponse> call, Throwable t) {

        //           Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        //         }
        //     });

        //   }else {
        //      Toast.makeText(getApplicationContext(),"Not available. Please, try to find another else :)", Toast.LENGTH_LONG).show();
        //  }
    }
}
