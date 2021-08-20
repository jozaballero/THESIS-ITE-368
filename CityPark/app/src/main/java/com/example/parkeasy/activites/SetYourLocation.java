package com.example.parkeasy.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkeasy.R;
import com.example.parkeasy.api.RetrofitClient;
import com.example.parkeasy.models.DefaultResponse;
import com.example.parkeasy.models.User;
import com.example.parkeasy.storage.SharedPrefManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetYourLocation extends AppCompatActivity implements OnMapReadyCallback {

    private TextView textView;
    private String park_name;
    private String phone;
    private String capacity;
    private String price;
    private String address;
    private String password;
    private  GoogleMap mMap;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_your_location);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById( R.id.map_loc );
        mapFragment.getMapAsync( this );



        Bundle b = getIntent().getExtras();
        park_name = b.getString("park_name");
        phone = b.getString("phone");
        price = b.getString("price");
        capacity = b.getString("capacity");
        password = b.getString("password");

        //textView.setText(park_name+" "+phone+" "+capacity+" "+map_locatin+" "+password);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            boolean success = googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle( getApplicationContext(), R.raw.mapstyler_stander ) );
            if (!success) {
                Log.e( "Set Your Location", "Style map failed" );
            }
        } catch (Resources.NotFoundException e) {
            Log.e( "Set Your Location", "Can't find MAP_Style" + e );
        }

        LatLng cdo = new LatLng(8.475527, 124.634182 );
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( cdo, 16 ) );
        mMap.getUiSettings().setZoomControlsEnabled( true );
        mMap.getUiSettings().setZoomGesturesEnabled( false );
        final Marker marker = mMap.addMarker( new MarkerOptions().position( cdo ) );
        marker.remove();
        if (ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled( true );
        mMap.setOnMapClickListener( new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                final double lat = point.latitude ;
                final double lng =   point.longitude  ;
                double zoomV =    mMap.getCameraPosition().zoom  ;
                location = getCompleteAddressString(  point.latitude , point.longitude  );

                drawMarker(point,location);



                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        new AlertDialog.Builder(SetYourLocation.this)
                                .setTitle("Location")
                                .setMessage(location )
                                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext()," Lat:"+lat+" Lng:"+lng+" Add:"+location, Toast.LENGTH_LONG).show();
                                        User user = SharedPrefManager.getInstance(getApplication()).getUser();

                                        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().rentYourSpaceReg(park_name,  user.getFullName(), phone,capacity,  price, password, lat, lng, location);
                                        call.enqueue(new Callback<DefaultResponse>() {
                                            @Override
                                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                                DefaultResponse defaultResponse = response.body();
                                                if(response.isSuccessful()) {
                                                    Intent intent = new Intent(SetYourLocation.this, HomepageActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    Toast.makeText(getApplicationContext(), defaultResponse.toString(), Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                                            }
                                        });

                                    }
                                })
                                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .show();
                        return false;
                    }
                });






            }
        } );

    }


    public void drawMarker(LatLng point, String add){
        MarkerOptions markerOptions = new MarkerOptions();
        mMap.addMarker(markerOptions.position(point).title( add ).snippet(add).draggable( true ));
        mMap.addMarker(markerOptions.icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA) ) );

        Toast.makeText( getApplicationContext()," "+point+" "+add,Toast.LENGTH_SHORT ).show();
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String fullAdd = null;
        try {
            Geocoder geocoder = new Geocoder( getBaseContext(), Locale.getDefault() );
            List<Address> addresses = geocoder.getFromLocation( LATITUDE, LONGITUDE, 1 );
            if (addresses.size() > 0) {
                Address address = addresses.get( 0 );
                fullAdd = address.getAddressLine( 0 );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullAdd;
    }


}
