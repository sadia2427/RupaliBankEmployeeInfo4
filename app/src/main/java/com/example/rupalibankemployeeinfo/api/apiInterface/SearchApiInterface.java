package com.example.rupalibankemployeeinfo.api.apiInterface;

import com.example.rupalibankemployeeinfo.api.model.DivisionalList;
import com.example.rupalibankemployeeinfo.api.model.SearchModel;
import com.example.rupalibankemployeeinfo.api.model.ZonalList;
import com.example.rupalibankemployeeinfo.api.model.ZoneEmployeeBranchList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchApiInterface {
    @GET("employee/get_EmployeeByRegNo?")
    Call<List<SearchModel>> getRegistrationID(@Query("reg") String reg, @Query("count")int count);

    /// Divisional List
    @GET("employee/get_DivisionList")
    Call<List<DivisionalList>> getDivisionList();

    ////get ZonalList
    @GET("employee/get_ZoneList?")
    Call<ZonalList>getZonalListID(@Query("division_id") int divisionId);

    //////Get ZonewiseList
    @GET("Branch/getBranchList?")
    Call<List<ZoneEmployeeBranchList>> getZoneEmployeeOrBranch(@Query("zone_id") int zoneId);

}
