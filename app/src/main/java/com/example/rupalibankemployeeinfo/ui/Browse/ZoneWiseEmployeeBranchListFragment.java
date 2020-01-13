package com.example.rupalibankemployeeinfo.ui.Browse;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rupalibankemployeeinfo.R;
import com.example.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.example.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.example.rupalibankemployeeinfo.api.model.SearchModel;
import com.example.rupalibankemployeeinfo.api.model.ZonalList;
import com.example.rupalibankemployeeinfo.api.model.ZoneEmployeeBranchList;
import com.example.rupalibankemployeeinfo.ui.employedetails.EmployeeDetailsFragment;
import com.example.rupalibankemployeeinfo.ui.search.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoneWiseEmployeeBranchListFragment extends Fragment {


    private String TAG="ZoneWiseEmployeeBranchListFragment";

    private RecyclerView mEmployeeRecyler;
    private RecyclerView mBranchRecyler;
    private ProgressBar mProgressBar;
    private SearchAdapter mSearchAdapter;
    private BrowseAdapter mBrowseAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayoutManager mBrowseLinearLayoutManager;

    private List<SearchModel> searchModelList;
    private List<ZoneEmployeeBranchList> zoneEmployeeBranchLists;
    private Bundle bundle;
    private Bundle mBundle;
    private Fragment fragment;

    private int branchId;

    private Retrofit mRetrofit;
    private SearchApiInterface mSearchApiInterface;
    public ZoneWiseEmployeeBranchListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_zone_wise_employee_branch_list, container, false);
        Log.w(TAG, "onCreateView: " );
        mEmployeeRecyler=view.findViewById(R.id.employee_list_rv);
        mBranchRecyler=view.findViewById(R.id.branch_list_rv);
        mProgressBar=view.findViewById(R.id.progress_z);
        zoneEmployeeBranchLists=new ArrayList<>();
        searchModelList=new ArrayList<>();
        mBundle=new Bundle();
        mRetrofit= RetrofitSingleton.getInstance(getActivity());
        mSearchApiInterface=mRetrofit.create(SearchApiInterface.class);

        mLinearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        mEmployeeRecyler.setLayoutManager(mLinearLayoutManager);

        mBrowseLinearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,0);
        mBranchRecyler.setLayoutManager(mBrowseLinearLayoutManager);

        bundle=this.getArguments();
        if (bundle!=null){
            Log.w(TAG, "onCreateView: "+ bundle.getInt("ZoneID"));
            branchId=bundle.getInt("ZoneID");
            getZoneWiseBranchList(branchId);
        }


        return view;
    }

    private void getZoneWiseBranchList(int branchId){
        Call<ZoneEmployeeBranchList> getZoneWiseBranchList=mSearchApiInterface.getZoneEmployeeOrBranch(branchId);
        getZoneWiseBranchList.enqueue(new Callback<ZoneEmployeeBranchList>() {
            @Override
            public void onResponse(Call<ZoneEmployeeBranchList> call, final Response<ZoneEmployeeBranchList> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    Log.w(TAG, "onResponse: "+response.body().toString() );
                    mProgressBar.setVisibility(View.GONE);
                    zoneEmployeeBranchLists.clear();
                    zoneEmployeeBranchLists.add(response.body());
                    if(!response.body().getEmployeeLists().isEmpty()){
                        mEmployeeRecyler.setVisibility(View.VISIBLE);
                        mSearchAdapter=new SearchAdapter(response.body().getEmployeeLists(),getActivity());
                        mEmployeeRecyler.setAdapter(mSearchAdapter);
                        mSearchAdapter.setOnItemClickListener(new SearchAdapter.SearchInterFace() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Toast.makeText(getActivity(),"ZonalList",Toast.LENGTH_SHORT).show();
                                mBundle.putString("regNo", String.valueOf(response.body().getEmployeeLists().get(position).getEmpRegNo()));
                                mBundle.putString("EmpName",response.body().getEmployeeLists().get(position).getEmpName());
                                mBundle.putString("EmpNameBan",response.body().getEmployeeLists().get(position).getEmpNameBN());
                                mBundle.putString("EmpMob",response.body().getEmployeeLists().get(position).getEmpMobile());
                                mBundle.putString("empEmail",response.body().getEmployeeLists().get(position).getEmpEmail());
//                            mBundle.putString("categoryId",String.valueOf(mCategoryId));
//                            mBundle.putString("subCategoryId",String.valueOf(mSubCategoryId));
//                            mBundle.putString("subSubCategoryId",String.valueOf(mSubsubCatoryIdString));
                                fragment = new EmployeeDetailsFragment();
                                fragment.setArguments(mBundle);
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.nav_host_fragment, fragment);
                                ft.addToBackStack(null);
                                ft.commit();

                            }
                        });
                    }

                    if (!response.body().getBranchInfoLists().isEmpty()){
                        mBranchRecyler.setVisibility(View.VISIBLE);
                        mBranchRecyler.setVisibility(View.GONE);
                        mBrowseAdapter=new BrowseAdapter(response.body().getBranchInfoLists(),2);
                        mBrowseAdapter.setOnItemClickListener(new BrowseAdapter.BrowseInterFace() {
                            @Override
                            public void onItemClick(int position, View v) {
                                mBundle.putInt("officeId",response.body().getBranchInfoLists().get(position).getOfficeId());
                                fragment=new EmployeeListFragment();
                                fragment.setArguments(mBundle);
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.nav_host_fragment, fragment);
                                ft.addToBackStack(null);
                                ft.commit();

                            }
                        });


                        //getActivity().getFragmentManager().popBackStack();

                    }


                }
            }

            @Override
            public void onFailure(Call<ZoneEmployeeBranchList> call, Throwable t) {
                Log.w(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }

}
