package com.example.parkeasy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parkeasy.R;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.ParkStatus;
import com.example.parkeasy.models.ParkStatusResponse;
import com.example.parkeasy.models.User;
import com.example.parkeasy.storage.SharedPrefManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParkStatusFragment extends Fragment {

    private TextView parked, parkName, time, date, type, regNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_park_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parked = view.findViewById(R.id.textView2z);
        parkName = view.findViewById(R.id.textViewprkNm);
        time = view.findViewById(R.id.textVkjhissew);
        date = view.findViewById(R.id.tekjhxtVdateiew);
        type = view.findViewById(R.id.tkjhextVkjhissew);
        regNo = view.findViewById(R.id.telkkjhxtViewreg);
    }

    @Override
    public void onStart() {
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
        Call<ParkStatusResponse> call = RetrofitClient.getInstance().getApi().parkStatus_parkedNwhere( user.getPhoneNum(), user.getVehicleType(), user.getVehicleRegNo());
        call.enqueue(new Callback<ParkStatusResponse>() {
            @Override
            public void onResponse(Call<ParkStatusResponse> call, Response<ParkStatusResponse> response) {
                ParkStatusResponse parkStatusResponse = response.body();
                ParkStatus parkStatus = parkStatusResponse.getParkStatus();
                if (parkStatusResponse.isError() == false){
                    parked.setText("Parked");
                    parkName.setText(parkStatus.getPark_name());
                    type.setText(user.getVehicleType());
                    regNo.setText(user.getVehicleRegNo());
                    date.setText(parkStatus.getDate());

                    String s = parkStatus.getTime();
                    DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                    Date d = null;
                    try {
                        d = f1.parse(s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    DateFormat f2 = new SimpleDateFormat("h:mma");

                    time.setText( f2.format(d).toLowerCase());
                }else {
                    parked.setText(" Not Parked ");
                    parkName.setText(" Not Found ");
                    type.setText(" Not Found ");
                    regNo.setText(" Not Found ");
                    date.setText(" Not Found ");
                    time.setText(" Not Found ");
                }
            }
            @Override
            public void onFailure(Call<ParkStatusResponse> call, Throwable t) {

            }
        });

        super.onStart();
    }

}





