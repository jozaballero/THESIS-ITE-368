package com.example.parkeasy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkeasy.R;
import com.example.parkeasy.adapters.ParkListAdapter;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.ParkList;
import com.example.parkeasy.models.ParkListResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ParkListAdapter parkListAdapter;
    private List<ParkList> parkList;
    private SearchView serachView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_park_list , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serachView = view.findViewById(R.id.search_bar);
        serachView.setOnQueryTextListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        Call<ParkListResponse> call = RetrofitClient.getInstance().getApi().getParkList();
        call.enqueue(new Callback<ParkListResponse>() {
            @Override
            public void onResponse(Call<ParkListResponse> call, Response<ParkListResponse> response) {
                parkList = response.body().getParkList();
                parkListAdapter = new ParkListAdapter(getContext(), parkList);
                recyclerView.setAdapter(parkListAdapter);
            }

            @Override
            public void onFailure(Call<ParkListResponse> call, Throwable t) {

            }
        });

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        final List<ParkList> filteredModeList = filter(parkList, query);
        parkListAdapter.setFilter(filteredModeList);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private List<ParkList>filter(List<ParkList> models, String query){
        query = query.toLowerCase();

        final List<ParkList> filteredModeList = new ArrayList<>();
        for (ParkList model : models){
            final String text = model.getPark_name().toLowerCase();
            if (text.contains(query)){
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }





}
