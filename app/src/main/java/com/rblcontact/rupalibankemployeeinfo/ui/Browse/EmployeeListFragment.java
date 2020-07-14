package com.rblcontact.rupalibankemployeeinfo.ui.Browse;


import android.os.Bundle;

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

import com.rblcontact.rupalibankemployeeinfo.R;
import com.rblcontact.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.rblcontact.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.rblcontact.rupalibankemployeeinfo.api.model.BranchWiseEmployeeDetails;
import com.rblcontact.rupalibankemployeeinfo.api.model.SearchModel;
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
public class EmployeeListFragment extends Fragment {
    private String TAG="EmployeeListFragment";
    private ProgressBar mProgressBar;
    private RecyclerView mRecylerView;
    private Bundle bundle;
    private LinearLayoutManager linearLayoutManager;
    private SearchAdapter mSearchAdapter;
    private Retrofit mRetrofit;
    private SearchApiInterface  mSearchApiInterface;
    private List<SearchModel> searchModelList;
    private int mOfficeId;
    private Bundle mBundle;


    public EmployeeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_employee_list, container, false);
        mRecylerView=view.findViewById(R.id.employee_recylerview);
        mProgressBar=view.findViewById(R.id.employee_list_p);
        mRetrofit= RetrofitSingleton.getInstance(getActivity());
        mSearchApiInterface=mRetrofit.create(SearchApiInterface.class);
        searchModelList=new ArrayList<>();
        mBundle=new Bundle();
        linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        mRecylerView.setLayoutManager(linearLayoutManager);
        bundle=this.getArguments();
        if (bundle!=null){
            mOfficeId=bundle.getInt("officeId");
            Log.w(TAG, "onResponse: ---------------------------" + mOfficeId);
            getEmployeeList(mOfficeId);
        }

        return view;
    }

    private void getEmployeeList(int OfficeId){
        Call<BranchWiseEmployeeDetails> getBranchWiseList=mSearchApiInterface.getBranchEmployeeDetails(OfficeId);
        getBranchWiseList.enqueue(new Callback<BranchWiseEmployeeDetails>() {
            @Override
            public void onResponse(Call<BranchWiseEmployeeDetails> call, Response<BranchWiseEmployeeDetails> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    searchModelList.clear();
                    searchModelList.addAll(response.body().getSearchModelList());
                    Log.w(TAG, "getEmployeeList: ---------------------------" + response.body().getSearchModelList().toString());
                    mRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mSearchAdapter=new SearchAdapter(response.body().getSearchModelList(),getActivity());
                    mRecylerView.setAdapter(mSearchAdapter);
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

}
