package com.rblcontact.rupalibankemployeeinfo.ui.Browse;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rblcontact.rupalibankemployeeinfo.R;
import com.rblcontact.rupalibankemployeeinfo.adapter.BranchListAdapter;
import com.rblcontact.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.rblcontact.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.rblcontact.rupalibankemployeeinfo.api.model.BranchInfo;
import com.rblcontact.rupalibankemployeeinfo.api.model.BranchWiseEmployeeDetails;
import com.rblcontact.rupalibankemployeeinfo.api.model.SearchModel;
import com.rblcontact.rupalibankemployeeinfo.api.model.ZoneEmployeeBranchList;
import com.rblcontact.rupalibankemployeeinfo.listener.AdapterItemClickHandler;
import com.rblcontact.rupalibankemployeeinfo.ui.employedetails.EmployeeDetailsFragment;
import com.rblcontact.rupalibankemployeeinfo.ui.search.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoneWiseEmployeeBranchListFragment extends Fragment implements AdapterItemClickHandler {


    private String TAG = "ZoneWiseEmployeeBranchListFragment";

    private RecyclerView mEmployeeRecyler;
    private RecyclerView mBranchRecyler;
    private ProgressBar mProgressBar;
    private SearchAdapter mSearchAdapter;
    //    private BrowseAdapter mBrowseAdapter;
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


    BranchListAdapter adapter;
    private List<BranchInfo> branchLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_zone_wise_employee_branch_list,container,false);

        Log.w(TAG, "onCreateView: ");
       mEmployeeRecyler=view.findViewById(R.id.employee_list_rv);
      // mEmployeeRecyler=view.findViewById(R.id.employee_recylerview);
        mBranchRecyler = view.findViewById(R.id.branch_list_rv);
        mProgressBar = view.findViewById(R.id.progress_z);
//        tvEmptyView = view.findViewById(R.id.tvEmptyView);
        zoneEmployeeBranchLists = new ArrayList<>();
        searchModelList = new ArrayList<>();
        mBundle = new Bundle();
        mRetrofit = RetrofitSingleton.getInstance(getActivity());
        mSearchApiInterface = mRetrofit.create(SearchApiInterface.class);

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        mEmployeeRecyler.setLayoutManager(mLinearLayoutManager);

        mBrowseLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        mBranchRecyler.setLayoutManager(mBrowseLinearLayoutManager);

        bundle = this.getArguments();
        if (bundle != null) {
            Log.w(TAG, "onCreateView:---------------------------- " + bundle.getInt("ZoneID"));
            branchId = bundle.getInt("ZoneID");
            mProgressBar.setVisibility(View.VISIBLE);
            getZoneWiseBranchList(branchId);
        }
        branchLists = new ArrayList<>();

        ///////////////////////////////////
        //bundle=this.getArguments();
        if (bundle!=null){
          /*  mOfficeId=bundle.getInt("officeId");
            Log.w(TAG, "onResponse: ---------------------------" + mOfficeId);
          */
        //  getEmployeeList(branchId);
        }

        //////////////////////////////////
        return view;
    }

    private void getZoneWiseBranchList(int branchId) {
        Call<ZoneEmployeeBranchList> getZoneWiseBranchList = mSearchApiInterface.getZoneEmployeeOrBranch(branchId);
        getZoneWiseBranchList.enqueue(new Callback<ZoneEmployeeBranchList>() {
            @Override
            public void onResponse(Call<ZoneEmployeeBranchList> call, final Response<ZoneEmployeeBranchList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.w(TAG, "onResponse: " + response.body().getEmployeeLists().toString());
                    zoneEmployeeBranchLists.clear();

     //////////////////// LOAD EMP DATA /////////////////////////////////////////////////////
                    if(!response.body().getEmployeeLists().isEmpty()){
                       zoneEmployeeBranchLists.add(response.body());
                       mEmployeeRecyler.setVisibility(View.VISIBLE);
                        mSearchAdapter=new SearchAdapter(response.body().getEmployeeLists(),getActivity());
                        mEmployeeRecyler.setAdapter(mSearchAdapter);
                        mSearchAdapter.setOnItemClickListener(new SearchAdapter.SearchInterFace() {
                            @Override
                            public void onItemClick(int position, View v) {
                               // Toast.makeText(getActivity(),"ZonalList",Toast.LENGTH_SHORT).show();
                                mBundle.putString("regNo", String.valueOf(response.body().getEmployeeLists().get(position).getEmpRegNo()));
                                mBundle.putString("EmpName",response.body().getEmployeeLists().get(position).getEmpName());
                                mBundle.putString("EmpNameBan",response.body().getEmployeeLists().get(position).getEmpNameBN());
                                mBundle.putString("EmpMob",response.body().getEmployeeLists().get(position).getEmpMobile());
                                mBundle.putString("empEmail",response.body().getEmployeeLists().get(position).getEmpEmail());
////                            mBundle.putString("categoryId",String.valueOf(mCategoryId));
////                            mBundle.putString("subCategoryId",String.valueOf(mSubCategoryId));
////                            mBundle.putString("subSubCategoryId",String.valueOf(mSubsubCatoryIdString));
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
                    //////////////////// LOAD EMP DATA /////////////////////////////////////////////////////

                    if (!response.body().getBranchInfoLists().isEmpty()) {
                        branchLists = response.body().getBranchInfoLists();
                        //Log.w(TAG, "Get Emp list------------------: " + response.body().getEmployeeLists().toString());
                        prepareRecyclerView(branchLists);
                        //getActivity().getFragmentManager().popBackStack();
                    } else {
                        mBranchRecyler.setVisibility(View.GONE);
                     //  tvEmptyView.setVisibility(View.VISIBLE);
                    }
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ZoneEmployeeBranchList> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void prepareRecyclerView(List<BranchInfo> branchInfoLists) {
        mBranchRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BranchListAdapter(branchInfoLists, this);
        mBranchRecyler.setAdapter(adapter);
    }

    private void getEmployeeList(int OfficeId){
        Call<BranchWiseEmployeeDetails> getBranchWiseList=mSearchApiInterface.getBranchEmployeeDetails(OfficeId);
        getBranchWiseList.enqueue(new Callback<BranchWiseEmployeeDetails>() {
            @Override
            public void onResponse(Call<BranchWiseEmployeeDetails> call, Response<BranchWiseEmployeeDetails> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    searchModelList.clear();
                    searchModelList.addAll(response.body().getSearchModelList());
                  //  Log.w(TAG, "getEmployeeList: ---------------------------" + response.body().getSearchModelList().toString());
                    mEmployeeRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
                    mSearchAdapter=new SearchAdapter(response.body().getSearchModelList(),getActivity());
                    mEmployeeRecyler.setAdapter(mSearchAdapter);
                    mSearchAdapter.setOnItemClickListener(new SearchAdapter.SearchInterFace() {
                        @Override
                        public void onItemClick(int position, View v) {
                            mBundle.putString("regNo", String.valueOf(searchModelList.get(position).getEmpRegNo()));
                            mBundle.putString("EmpName",searchModelList.get(position).getEmpName());
                            mBundle.putString("EmpNameBan",searchModelList.get(position).getEmpNameBN());
                            mBundle.putString("EmpMob",searchModelList.get(position).getEmpMobile());
                            mBundle.putString("empEmail",searchModelList.get(position).getEmpEmail());
//                            mBundle.putString("categoryId",String.valueOf(mCategoryId));
//                            mBundle.putString("subCategoryId",String.valueOf(mSubCategoryId));
//                            mBundle.putString("subSubCategoryId",String.valueOf(mSubsubCatoryIdString));
                            Fragment fragment = new EmployeeDetailsFragment();
                            fragment.setArguments(mBundle);

                            //getActivity().getFragmentManager().popBackStack();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.nav_host_fragment, fragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<BranchWiseEmployeeDetails> call, Throwable t) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {
        if (!branchLists.isEmpty()) {
            mBundle.putInt("officeId", branchLists.get(position).getOfficeId());
            fragment = new EmployeeListFragment();
            fragment.setArguments(mBundle);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
