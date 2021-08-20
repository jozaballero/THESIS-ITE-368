package com.example.parkeasy.adapters;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkeasy.R;
import com.example.parkeasy.activites.ParkListItemActivity;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.fragments.FindParkFragment;
import com.example.parkeasy.models.ParkList;
import com.example.parkeasy.models.ParkListResponse;
import com.example.parkeasy.models.ParkOwnerSensorResponse;
import com.example.parkeasy.models.SensorData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkListAdapter  extends RecyclerView.Adapter<ParkListAdapter.ParkListViewHolder>  {
    private Context mContext;
    private List<ParkList> parkList;
    private Fragment fragment;


    public ParkListAdapter(Context mContext, List<ParkList> parkList) {
        this.mContext = mContext;
        this.parkList = parkList;
    }

    @NonNull
    @Override
    public ParkListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_park_list, parent, false);
        return new ParkListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ParkListViewHolder holder, final int position) {
        final ParkList list = parkList.get(position);
        holder.parkName.setText(list.getPark_name());
        holder.phnNum.setText(list.getPhone());
        holder.pric.setText(list.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = (int) getItemId(position);
                Intent intent = new Intent(mContext,ParkListItemActivity.class);
                intent.putExtra("parkName", list.getPark_name());
                intent.putExtra("price", list.getPrice());
                intent.putExtra("phn", list.getPhone());
                intent.putExtra("cap", list.getCapacity());
                intent.putExtra("add", list.getMap_location());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return parkList.size();
    }


    class ParkListViewHolder extends RecyclerView.ViewHolder{

        TextView parkName, phnNum, capacity, add,pric;
        private LinearLayout linearLayout;

        public ParkListViewHolder(@NonNull View itemView) {
            super(itemView);

            parkName = itemView.findViewById(R.id.textView_ParkName);
            phnNum = itemView.findViewById(R.id.textView_Phn_num);
            pric = itemView.findViewById(R.id.textView_map_loc);
            add = itemView.findViewById(R.id.textView3Address);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

    public void setFilter(List<ParkList> dataModels){
        parkList = new ArrayList<>();
        parkList.addAll(dataModels);
        notifyDataSetChanged();
    }


}