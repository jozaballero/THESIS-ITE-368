package com.example.parkeasy.api;


import com.example.parkeasy.models.DefaultResponse;
import com.example.parkeasy.models.LoginResponse;
import com.example.parkeasy.models.ParkListResponse;
import com.example.parkeasy.models.ParkOwnerPhnCapResponse;
import com.example.parkeasy.models.ParkOwnerSensorResponse;
import com.example.parkeasy.models.ParkStatusResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface Api {

    @FormUrlEncoded
    @POST("registerUser.php")
    Call<DefaultResponse> createUser(
            @Field("fullName") String fullName,
            @Field("phoneNum") String phoneNum,
            @Field("address") String address,
            @Field("vehicleType") String vehicleType,
            @Field("vehicleRegNo") String vehicleRegNo,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("userLogin.php")
    Call<LoginResponse> userLoginCheck(
            @Field("phoneNum") String phoneNum,
            @Field("password") String password
    );

    @GET("parkList.php")
    Call<ParkListResponse> getParkList();

    @FormUrlEncoded
    @POST("parkOwnerSensorStatus.php")
    Call<ParkOwnerSensorResponse> getParkOwnerSensorData(
            @Field("park_name") String park_name
    );

    @FormUrlEncoded
    @POST("bookNow.php")
    Call<DefaultResponse> bookNow(
            @Field("park_name") String park_name,
            @Field("user_fullName") String user_fullName,
            @Field("user_phoneNum") String user_phoneNum,
            @Field("date") String date,
            @Field("time") String time,
            @Field("durations") String durations,
            @Field("total") String total,
            @Field("vehicleType") String vehicleType,
            @Field("vehicleRegNo") String vehicleRegNo
    );

    @FormUrlEncoded
    @POST("rentYourSpace.php")
    Call<DefaultResponse> rentYourSpaceReg(
            @Field("park_name") String park_name,
            @Field("owner_name") String owner_name,
            @Field("phone") String phone,
            @Field("capacity") String capacity,
            @Field("price") String price,
            @Field("password") String password,
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude,
            @Field("map_location") String map_location
    );



    @FormUrlEncoded
    @POST("phnNcapacity.php")
    Call<ParkOwnerPhnCapResponse> phnNcapacityByParkNmNAdd_park_owner(
            @Field("park_name") String park_name
    );


    @FormUrlEncoded
    @POST("parkStatus.php")
    Call<ParkStatusResponse> parkStatus_parkedNwhere(
            @Field("user_phoneNum") String user_phoneNum,
            @Field("vehicleType") String vehicleType,
            @Field("vehicleRegNo") String vehicleRegNo
    );


}
