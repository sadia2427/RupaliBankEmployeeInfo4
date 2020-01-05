package com.example.rupalibankemployeeinfo.ui.employedetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rupalibankemployeeinfo.R;
import com.example.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.example.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.example.rupalibankemployeeinfo.api.model.SearchModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class EmployeeDetailsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // Call Api data
    private List<SearchModel> mSearchModel;
    private Retrofit mRetrofit;
    private SearchApiInterface mSearchApiInterface;

    private Button callNowbtn;
    private Button messaggeNowBtn;

    private TextView mRegistrationNotv;
    private TextView mEmployeeNameTv;
    private TextView mEmployeeNameBanglaTv;
    private TextView mDesignationTv;
    private TextView mPlaceOfPosting;
    private TextView mMobileNoTv;
    private TextView mEmailNoTv;

    private String mMobileNo;
    private String mRegistrationNo;
    private Bundle bundle;
    private String TAG="EmployeeDetailsFragment";



    private OnFragmentInteractionListener mListener;
    public EmployeeDetailsFragment() {
        // Required empty public constructor
    }


    public  EmployeeDetailsFragment newInstance(String RegistrationNo) {
        EmployeeDetailsFragment fragment = new EmployeeDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.employee_details, container, false);

        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mSearchModel=new ArrayList<>();
        Log.e("msearchView", mSearchModel.toString() );
        mRetrofit= RetrofitSingleton.getInstance(getContext());
        mSearchApiInterface=mRetrofit.create(SearchApiInterface.class);
        callNowbtn=view.findViewById(R.id.call_byphone_button);
        messaggeNowBtn=view.findViewById(R.id.messagenow_button);
        mRegistrationNotv=view.findViewById(R.id.reg_notv);
        mEmployeeNameTv=view.findViewById(R.id.employee_nametv);
        mEmployeeNameBanglaTv=view.findViewById(R.id.employee_name_bantv);
        mDesignationTv=view.findViewById(R.id.deg_tv);
        mPlaceOfPosting=view.findViewById(R.id.posting_placetv);
        mMobileNoTv=view.findViewById(R.id.mobile_notv);
        mEmailNoTv=view.findViewById(R.id.email_tv);
        callNowbtn.setOnClickListener(this);
        messaggeNowBtn.setOnClickListener(this);
        bundle=this.getArguments();
        if (bundle!=null) {
            mRegistrationNo = bundle.getString("regNo");
        }
        getEmployeeData(mRegistrationNo);

        return view;
    }

    private void  getEmployeeData(String RegistraionNo){
        Call<List<SearchModel>> getUserData=mSearchApiInterface.getRegistrationID(RegistraionNo,0);
        getUserData.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                if (response.body()!=null && response.isSuccessful()){
                    for (int i=0;i<=mSearchModel.size();i++){
                        mMobileNo=response.body().get(i).getEmpMobile();
                        mRegistrationNotv.setText(String.valueOf(response.body().get(i).getEmpRegNo()));
                        mEmployeeNameTv.setText(response.body().get(i).getEmpName());
                        mEmployeeNameBanglaTv.setText(response.body().get(i).getEmpNameBN());
                        mDesignationTv.setText(response.body().get(i).getDesignationName());
                        mPlaceOfPosting.setText(response.body().get(i).getOfficeName());
                        mMobileNoTv.setText(response.body().get(i).getEmpMobile());
                        mEmailNoTv.setText(response.body().get(i).getEmpEmail());
                    }


                }
                else {

                }
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.call_byphone_button:
                Intent dial = new Intent();
                dial.setAction("android.intent.action.DIAL");
                dial.setData(Uri.parse("tel:" + mMobileNo));
                startActivity(dial);
                break;

            case R.id.messagenow_button:
//                String messageNumber = "01876068187";
//                Intent dialn = new Intent();
//                dialn.setAction("android.intent.action.sms");
//                dialn.setData(Uri.parse("tel:" + messageNumber));
//                startActivity(dialn);

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + mMobileNo)));
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
