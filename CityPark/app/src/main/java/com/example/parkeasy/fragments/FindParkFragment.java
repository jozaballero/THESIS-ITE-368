package com.example.parkeasy.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.parkeasy.R;
import com.example.parkeasy.activites.ParkListItemActivity;
import com.example.parkeasy.adapters.ParkListAdapter;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.ParkList;
import com.example.parkeasy.models.ParkListResponse;
import com.example.parkeasy.models.ParkOwnerPhnCap;
import com.example.parkeasy.models.ParkOwnerPhnCapResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindParkFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Dialog myDialog;
    private List<ParkList> parkList;
    private ParkListAdapter parkListAdapter;
    private RecyclerView recyclerView;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.fragment_find_park, container, false );
        myDialog = new Dialog(getContext());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        try {
            boolean success = googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle( getContext(),R.raw.mapstyler_stander ) );
            if (!success){
                Log.e("Find Park","Style map failed");
            }
        }catch (Resources.NotFoundException e){
            Log.e("Find Park", "Can't find MAP_Style"+e);
        }

        LatLng cdo = new LatLng(8.475527, 124.634182 );
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( cdo, 16 ) );
        mMap.getUiSettings().setZoomControlsEnabled( true );
        final Marker marker = mMap.addMarker( new MarkerOptions().position( cdo ) );
        marker.remove();
        if (ActivityCompat.checkSelfPermission( getContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled( true );
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

       Call<ParkListResponse> call = RetrofitClient.getInstance().getApi().getParkList();
       call.enqueue(new Callback<ParkListResponse>() {
           @Override
           public void onResponse(Call<ParkListResponse> call, Response<ParkListResponse> response) {
               ParkListResponse listResponse = response.body();
               List<ParkList> parkList = listResponse.getParkList();
               for(int i=0 ; i<parkList.size() ; i++){
                   ParkList object = parkList.get(i);
                   showMarker(new LatLng(object.getLatitude(), object.getLongitude()), object.getPark_name(), object.getPhone(),object.getAddress(), object.getCapacity());
               }

               //Arraylist<ParkListResponse> model = new ArrayList<ParkListResponse>(Arrays.asList(response));


           }

           @Override
           public void onFailure(Call<ParkListResponse> call, Throwable t) {

           }
       });




       // showMarker(new LatLng(23.743757, 90.38459), "DIU Parking Interface", "01700000000","h", "6");
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable= ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap=Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private void showMarker(LatLng point, final String prknm, final String num, final String add, final String cap ) {

        final MarkerOptions markerOptions =  new MarkerOptions();
        markerOptions.position( point );
        mMap.addMarker( markerOptions.title(prknm).snippet(num)
                .icon(bitmapDescriptorFromVector(getContext(),R.drawable.localparking)));
        markerOptions.draggable( true );


        mMap.setOnInfoWindowClickListener( new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {

                Call<ParkOwnerPhnCapResponse> call = RetrofitClient.getInstance().getApi().phnNcapacityByParkNmNAdd_park_owner(marker.getTitle());
                call.enqueue(new Callback<ParkOwnerPhnCapResponse>() {
                    @Override
                    public void onResponse(Call<ParkOwnerPhnCapResponse> call, Response<ParkOwnerPhnCapResponse> response) {
                        ParkOwnerPhnCapResponse parkOwnerPhnCapResponse = response.body();
                        ParkOwnerPhnCap parkOwnerPhnCap = parkOwnerPhnCapResponse.getResult();
                        if (parkOwnerPhnCapResponse.isError() == false){
                            Intent intent = new Intent(getContext(), ParkListItemActivity.class);
                            /**
                            i.putExtra( "parkName", marker.getTitle() );
                            i.putExtra( "add", marker.getSnippet());
                            i.putExtra( "phn",  parkOwnerPhnCap.getPhone() );
                            i.putExtra( "cap", parkOwnerPhnCap.getCapacity() );**/
                            intent.putExtra("parkName", marker.getTitle());
                            intent.putExtra("price", parkOwnerPhnCap.getPrice());
                            intent.putExtra("phn", parkOwnerPhnCap.getPhone());
                            intent.putExtra("cap", parkOwnerPhnCap.getCapacity());
                            intent.putExtra("add", parkOwnerPhnCap.getMap_location());

                            startActivity( intent );
                        }
                    }

                    @Override
                    public void onFailure(Call<ParkOwnerPhnCapResponse> call, Throwable t) {

                    }
                });






            }
        } );

    }




}
