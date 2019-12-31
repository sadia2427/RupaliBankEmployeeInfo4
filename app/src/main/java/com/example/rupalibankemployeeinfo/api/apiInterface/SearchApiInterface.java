package com.example.rupalibankemployeeinfo.api.apiInterface;

import com.example.rupalibankemployeeinfo.api.model.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchApiInterface {
    @GET("/employee/get_EmployeeByRegNo")
    Call<List<SearchModel>> getRegistrationID(@Query("reg") String reg, @Query("regid") int apiKey);

}
