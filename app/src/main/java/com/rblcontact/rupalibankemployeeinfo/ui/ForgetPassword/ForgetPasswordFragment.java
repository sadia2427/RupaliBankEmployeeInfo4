package com.rblcontact.rupalibankemployeeinfo.ui.ForgetPassword;

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

import com.rblcontact.rupalibankemployeeinfo.R;
import com.rblcontact.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.rblcontact.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.rblcontact.rupalibankemployeeinfo.api.model.ChangePassword;
import com.rblcontact.rupalibankemployeeinfo.api.model.SearchModel;
import com.rblcontact.rupalibankemployeeinfo.api.model.SignInInfromation;
import com.rblcontact.rupalibankemployeeinfo.ui.home.HomeFragment;
import com.rblcontact.rupalibankemployeeinfo.ui.signin.SessionManager;
import com.rblcontact.rupalibankemployeeinfo.ui.signin.StoreUserInformation;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ForgetPasswordFragment extends Fragment implements View.OnClickListener {
    // REGION 1 ---required variable is declared for changing password

    private String TAG="ForgetPasswordFragment";
    private TextView mRegNo;
    private EditText mOtpEt;
    private EditText mNewPassEt;
    private EditText mConfirmPassEt;
    private Button mUpdateBtn;
// END REGION -1

    ///api call from single tone
    private Retrofit mRetrofit;
    // SearchApiInterface is assign for call SearchApiInterface and it's used for calling API data from query
    private SearchApiInterface mSearchApiInterface;
    private List<ChangePassword> mChangePasswordList;

    private String mRegNumber;
    private String mOtp;
    private String mNewPass;
    private String mConfirmpass;

    private Bundle bundle;
    private String mRegistrationNo;

    private StoreUserInformation storeUserInformation;
    private SignInInfromation signinInformation;
    private SessionManager session;
    private List<SearchModel> mSearchModel;
    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_forget_password, container, false);
        mOtpEt=view.findViewById(R.id.old_pass);
        mNewPassEt=view.findViewById(R.id.new_pass);
        mConfirmPassEt=view.findViewById(R.id.confirm_pass);
        mUpdateBtn=view.findViewById(R.id.change_password);
        mUpdateBtn.setOnClickListener(this);
        mRetrofit= RetrofitSingleton.getInstance(getActivity());
        mSearchApiInterface=mRetrofit.create(SearchApiInterface.class);
        session = new SessionManager(getActivity());
        storeUserInformation = new StoreUserInformation(getActivity());
        signinInformation=new SignInInfromation(getActivity());
        mSearchModel=new ArrayList<>();
        bundle=this.getArguments();
        if (bundle!=null) {
            mRegistrationNo = bundle.getString("regNo");

        }
        //// get password from user



        return  view;
    }

    private void getChangePass(final String Reg) {
        mOtp=mOtpEt.getText().toString().trim();
        mNewPass=mNewPassEt.getText().toString().trim();
        mConfirmpass=mConfirmPassEt.getText().toString().trim();
        final RequestBody registrationNo = RequestBody.create(MediaType.parse("multipart/form-data"), Reg);
        final RequestBody oldpass=RequestBody.create(MediaType.parse("multipart/form-data"),mOtp);
        final RequestBody newpass = RequestBody.create(MediaType.parse("multipart/form-data"), mNewPass);
        final RequestBody confpass=RequestBody.create(MediaType.parse("multipart/form-data"),mConfirmpass);
        Call<ResponseBody>call=mSearchApiInterface.getMessageResponse(registrationNo,oldpass,newpass,confpass);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.w(TAG, "onResponse: test "+response.message());
                if (response.message().equals("OK")){
                    getEmployeeData(Reg,mNewPass);
                }
                else if (response.message().equals("Not Implemented")){
                    Toast.makeText(getActivity(),R.string.wrong_email_password,Toast.LENGTH_SHORT).show();
                }
                else if(response.message().equals("Not Acceptable")){
                    Toast.makeText(getActivity(),"New Password and Confirm Password is not match",Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(),R.string.sunable_to_connect,Toast.LENGTH_SHORT).show();
            }
        });
//        Log.w("change", "onResponse:reg "+Reg+" mOldPass: "+mOldPass+" mNewPass: "+mNewPass+" mCon: "+mConfirmpass);
//        Call<ChangePassword> getBranchWiseList = mSearchApiInterface.getMessageResponse(Reg.trim(), mOldPass, mNewPass, mConfirmpass);
//       getBranchWiseList.enqueue(new Callback<ChangePassword>() {
//           @Override
//           public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
//
//               Log.w("change", "onResponse: "+response.body().toString() );
//               if (response.isSuccessful() && response.body()!=null){
//                       Toast.makeText(getActivity(),"Change Password Ok", Toast.LENGTH_SHORT).show();
////                       Bundle bundle=new Bundle();
//
//
//               }
//
//           }
//
//           @Override
//           public void onFailure(Call<ChangePassword> call, Throwable t) {
//               Log.w("changePasss", "onFailure: "+t.getMessage() );
//           }
//       });

    }
    private void  getEmployeeData(final String RegistraionNo, final String password){
        Call<List<SearchModel>> getUserData=mSearchApiInterface.getRegistrationID(RegistraionNo,0);
        getUserData.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                if (response.isSuccessful() && !response.body().isEmpty()){
                    Toast.makeText(getActivity(), R.string.change_pass, Toast.LENGTH_SHORT).show();
                    for (int i=0;i<=mSearchModel.size();i++){
                        final int userLoginId = response.body().get(0).getEmpRegNo();


                        //for storing user information
                        String username = "";
                        String useremail = "";
                        String usermobile = "";
                        String usergender = "";
                        String useraddress = "";
                        String userdistricbangla = "";
                        String userdistrictenglish = "";
                        String userknowaboutus = "";
                        String userMobile = "";
                        int userdistrictid = -1;
                        int userareaid = -1;
                        if (response.body().get(0).getEmpName() != null) {
                            username = response.body().get(0).getEmpName();
                        }
                        if (response.body().get(0).getEmpEmail()!= null) {
                            useremail = response.body().get(0).getEmpEmail();
                        }
                        if (response.body().get(0).getEmpMobile()!= null) {
                            usermobile = response.body().get(0).getEmpMobile();
                        }
                        if (response.body().get(0).getOfficeName()!= null) {
                            useraddress = response.body().get(0).getOfficeName();
                        }

                        storeUserInformation.storeUserInformation(useremail, username, usermobile, useraddress);
                        //end stroing user information
                        if (response.body().get(0).getEmpMobile()!= null) {
                            userMobile = response.body().get(0).getEmpMobile();

                        }
                        session.createLoginSession(password, RegistraionNo, userMobile, userLoginId);
                        signinInformation.storeSignin(1, -1, -1);
                        Fragment fragment = new HomeFragment();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.nav_host_fragment, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }


                }
                else {
                    Toast.makeText(getActivity(),"User not Found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        getChangePass(mRegistrationNo);
    }
}
