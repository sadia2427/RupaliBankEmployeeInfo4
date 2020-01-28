package com.example.rupalibankemployeeinfo.ui.changepassword;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rupalibankemployeeinfo.R;
import com.example.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.example.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.example.rupalibankemployeeinfo.api.model.ChangePassword;
import com.example.rupalibankemployeeinfo.ui.home.HomeFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
// class is created for change password and implements the fragment interface and also call the onclicked event
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    // REGION 1 ---required variable is declared for changeing password
    private TextView mRegNo;
    private EditText mOldPassEt;
    private EditText mNewPassEt;
    private EditText mConfirmPassEt;
    private Button mUpdateBtn;
// END REGION -1

    ///api call from single tone
    private Retrofit mRetrofit;
    // SearchApiInterface is assain for call SearchApiInterface and it's used for calling API data from query
    private SearchApiInterface mSearchApiInterface;
    private List<ChangePassword> mChangePasswordList;

    private String mRegNumber;
    private String mOldPass;
    private String mNewPass;
    private String mConfirmpass;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_change_password, container, false);
        mRegNo=view.findViewById(R.id.reg_no);
        mOldPassEt=view.findViewById(R.id.old_pass);
        mNewPassEt=view.findViewById(R.id.new_pass);
        mConfirmPassEt=view.findViewById(R.id.confirm_pass);
        mUpdateBtn=view.findViewById(R.id.change_password);
        mUpdateBtn.setOnClickListener(this);
        mRetrofit= RetrofitSingleton.getInstance(getActivity());
        mSearchApiInterface=mRetrofit.create(SearchApiInterface.class);

        //// get password from user

        mOldPass=mOldPassEt.getText().toString();
        mNewPass=mNewPassEt.getText().toString();
        mConfirmpass=mConfirmPassEt.getText().toString();

        return  view;
    }

    private void getChangePass(String Reg) {

        Call<ChangePassword> getBranchWiseList = mSearchApiInterface.getMessageResponse("13945", mOldPass, mNewPass, mConfirmpass);
       getBranchWiseList.enqueue(new Callback<ChangePassword>() {
           @Override
           public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
               Log.w("change", "onResponse: "+response.body().isStatus() );
               if (response.isSuccessful() && response.body()!=null){
                   if(response.body().isStatus()){
//                       Bundle bundle=new Bundle();
                       Fragment fragment = new HomeFragment();
                       FragmentManager fm = getFragmentManager();
                       FragmentTransaction ft = fm.beginTransaction();
                       ft.replace(R.id.nav_host_fragment, fragment);
                       ft.addToBackStack(null);
                       ft.commit();
                   }
               }

           }

           @Override
           public void onFailure(Call<ChangePassword> call, Throwable t) {
               Log.w("changePasss", "onFailure: "+t.getMessage() );
           }
       });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),"update",Toast.LENGTH_SHORT).show();
        getChangePass(mRegNumber);
    }
}
