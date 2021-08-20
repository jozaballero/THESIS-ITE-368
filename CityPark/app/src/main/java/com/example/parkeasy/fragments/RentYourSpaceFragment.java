package com.example.parkeasy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parkeasy.R;
import com.example.parkeasy.activites.SetYourLocation;
import com.example.parkeasy.models.User;
import com.example.parkeasy.storage.SharedPrefManager;

public class RentYourSpaceFragment extends Fragment implements View.OnClickListener {

    private EditText prkNm, phnNm, add, cap,pric, pass;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rent_your_space , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        prkNm = view.findViewById(R.id.editText_prkNmRent);
        phnNm = view.findViewById(R.id.editText_phnnumRegRent);
        pric = view.findViewById(R.id.editText_addRent);
        cap = view.findViewById(R.id.editText_VehicleRegNumRennt);
        pass = view.findViewById(R.id.editText_passwasdordsRegRent);

        view.findViewById(R.id.button_regNextRent).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_regNextRent:
                go_to_map_loc_with_input();
        }
    }

    private void go_to_map_loc_with_input() {
        String park_name = prkNm.getText().toString().trim();
        String phone = phnNm.getText().toString().trim();
        String price = pric.getText().toString().trim();
        String capacity = cap.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (park_name.isEmpty()){
            prkNm.setError("Park Name is required");
            prkNm.requestFocus();
            return;
        }else if (phone.isEmpty()){
            phnNm.setError("Phone number is required");
            phnNm.requestFocus();
            return;
        }else if (capacity.isEmpty()){
            cap.setError("Capacity is required");
            cap.requestFocus();
            return;
        }else if (price.isEmpty()){
            pric.setError("Price is required");
            pric.requestFocus();
            return;
        }else if (password.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }else if (password.length() < 5){
            pass.setError("Length should be 5 or more");
            pass.requestFocus();
            return;
        }else {
            Intent intent = new Intent( getContext(), SetYourLocation.class );
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra( "park_name", park_name );
            intent.putExtra( "phone", phone );
            intent.putExtra( "price", price );
            intent.putExtra( "capacity", capacity );
            intent.putExtra( "password", password );
             startActivity( intent );

        }

    }

}
