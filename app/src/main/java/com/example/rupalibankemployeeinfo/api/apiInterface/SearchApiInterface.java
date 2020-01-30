package com.example.rupalibankemployeeinfo.api.apiInterface;

import com.example.rupalibankemployeeinfo.api.model.BranchWiseEmployeeDetails;
import com.example.rupalibankemployeeinfo.api.model.ChangePassword;
import com.example.rupalibankemployeeinfo.api.model.DivisionalList;
import com.example.rupalibankemployeeinfo.api.model.SearchModel;
import com.example.rupalibankemployeeinfo.api.model.SignIn;
import com.example.rupalibankemployeeinfo.api.model.ZonalList;
import com.example.rupalibankemployeeinfo.api.model.ZoneEmployeeBranchList;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SearchApiInterface {
    @GET("employee/get_EmployeeByRegNo?")
    Call<List<SearchModel>> getRegistrationID(@Query("reg") String reg, @Query("count") int count);

    /// Divisional List
    @GET("employee/get_DivisionList")
    Call<List<DivisionalList>> getDivisionList();

    ////get ZonalList
    @GET("employee/get_ZoneList?")
    Call<ZonalList> getZonalListID(@Query("division_id") int divisionId);

    //////Get ZonewiseList
    @GET("Branch/getBranchList?")
    Call<ZoneEmployeeBranchList> getZoneEmployeeOrBranch(@Query("zone_id") int zoneId);

    //////////Get Branchwise Employee()
    @GET("Branch/getBranchEmployeeList?")
    Call<BranchWiseEmployeeDetails> getBranchEmployeeDetails(@Query("office_id") int officeId);

    //////getLoginData
//    @POST("api/login/user_login")
//    Call<SignIn> userSignIn(@Query("regNo") String reg, @Query("password") String pw);
    @Multipart
    @POST("api/login/user_login")
    Call<ResponseBody> uploadEmailPassword( @Part("regNo") RequestBody regNo, @Part("password") RequestBody password);

    ////Change password
    @Multipart
    @POST("api/login/password_change")
    Call<ResponseBody> getMessageResponse(@Part("regNo") RequestBody regNo, @Part("old_password") RequestBody password, @Part("conf_password") RequestBody Confirmpass, @Part("password") RequestBody pass);
}