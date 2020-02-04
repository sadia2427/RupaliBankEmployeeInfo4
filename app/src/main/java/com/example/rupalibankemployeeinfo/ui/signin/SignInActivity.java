package com.example.rupalibankemployeeinfo.ui.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.rupalibankemployeeinfo.MainActivity;
import com.example.rupalibankemployeeinfo.SplashActivity;
import com.example.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.example.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.example.rupalibankemployeeinfo.api.model.ChangePassword;
import com.example.rupalibankemployeeinfo.api.model.SearchModel;
import com.example.rupalibankemployeeinfo.api.model.SignIn;
import com.example.rupalibankemployeeinfo.api.model.SignInInfromation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rupalibankemployeeinfo.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";


    ////model class
    private SignInInfromation signinInformation;

    //////api call
    private Retrofit retrofit;
    private SearchApiInterface searchApiInterface;

    ////dialogbox
    private ProgressDialog mProgressDialog;

    //// Session And SharedPreferences
    private StoreUserInformation storeUserInformation;
    private SessionManager session;
    private Menu menu;
    /////Button
    private EditText userEmail;
    private EditText userPassword;
    private Button aDsigninButton;
    private ImageView visibleBtnNot;
    private ImageView visibleBtn;
    private List<SignIn> mSearchModelList;
    private List<SearchModel> mSearchModel;

    // button add for forgot pass
    private Button forgotPassword;

    //String Value
    private String mMobileNo;
    private String mRegistrationNo;
    private String mEmployeeName;
    private String mEmployeeNameBn;
    private String mEmployeeEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        supportInvalidateOptionsMenu();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        visibleBtn = (ImageView) findViewById(R.id.visibleBtn);
        visibleBtnNot = (ImageView) findViewById(R.id.visibleBtn2);
        userEmail = (EditText) findViewById(R.id.signinUserEmail);
        userPassword = (EditText) findViewById(R.id.signInUserPassword);
        aDsigninButton = (Button) findViewById(R.id.email_sign_in_button);
        session = new SessionManager(getBaseContext());
        storeUserInformation = new StoreUserInformation(getBaseContext());
        signinInformation=new SignInInfromation(getBaseContext());
        Retrofit retrofit = RetrofitSingleton.getInstance(getBaseContext());
        searchApiInterface = retrofit.create(SearchApiInterface.class);
        mSearchModel = new ArrayList<>();
//        checkOutProcesedModelList=new ArrayList<>();

//        Log.e("mee", "onCreate: "+String.valueOf(getIntent().getExtras().getInt("trackFromCart")));




        visibleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                visibleBtn.setVisibility(View.INVISIBLE);
                userPassword.setSelection(userPassword.length());
                visibleBtnNot.setVisibility(View.VISIBLE);
            }
        });
        visibleBtnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                visibleBtnNot.setVisibility(View.INVISIBLE);
                userPassword.setSelection(userPassword.length());
                visibleBtn.setVisibility(View.VISIBLE);
            }
        });




        aDsigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userSignIn();
                userPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                userEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);

            }


        });


    }



    public void userSignIn() {

        final String email = userEmail.getText().toString();
        final String password = userPassword.getText().toString();
        //String emailGoogle=textViewEmail.getText().toString();
//        Toast.makeText(getApplicationContext(),"user Sign IN",Toast.LENGTH_SHORT).show();
//        Log.w(TAG, "userSignIn: "+email+ " "+password );
//
//        searchApiInterface.userSignIn(email.trim(),password.trim()).enqueue(new Callback<SignIn>() {
//            @Override
//            public void onResponse(Call<SignIn> call, Response<SignIn> response) {
//                Log.w(TAG, "onResponse: "+response.body().toString()+"   "+response.message() +"  "+email+"  "+password);
//                Toast.makeText(getApplicationContext(),"on Responde",Toast.LENGTH_SHORT).show();
//                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(getApplicationContext(), "on If", Toast.LENGTH_SHORT).show();
////                    sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                    mSearchModelList.add(response.body());
//                    Toast.makeText(SignInActivity.this, R.string.success_in_signin, Toast.LENGTH_SHORT).show();
//                    final int userLoginId = response.body().getEmployeeInfo().get(0).getEmpRegNo();
//
//
//                    //for storing user information
//                    String username = "";
//                    String useremail = "";
//                    String usermobile = "";
//                    String usergender = "";
//                    String useraddress = "";
//                    String userdistricbangla = "";
//                    String userdistrictenglish = "";
//                    String userknowaboutus = "";
//                    String userMobile = "";
//                    int userdistrictid = -1;
//                    int userareaid = -1;
//                    if (response.body().getEmployeeInfo().get(0).getEmpName() != null) {
//                        username = response.body().getEmployeeInfo().get(0).getEmpName();
//                    }
//                    if (response.body().getEmployeeInfo().get(0).getEmpEmail()!= null) {
//                        useremail = response.body().getEmployeeInfo().get(0).getEmpEmail();
//                    }
//                    if (response.body().getEmployeeInfo().get(0).getEmpMobile()!= null) {
//                        usermobile = response.body().getEmployeeInfo().get(0).getEmpMobile();
//                    }
//                    if (response.body().getEmployeeInfo().get(0).getOfficeName()!= null) {
//                        useraddress = response.body().getEmployeeInfo().get(0).getOfficeName();
//                    }
//
//                    storeUserInformation.storeUserInformation(useremail, username, usermobile, useraddress);
//                    //end stroing user information
//                    if (response.body().getEmployeeInfo().get(0).getEmpMobile()!= null) {
//                        userMobile = response.body().getEmployeeInfo().get(0).getEmpMobile();
//
//                    }
//                    session.createLoginSession(password, email, userMobile, userLoginId);
//                    signinInformation.storeSignin(1, -1, -1);
//                    menuInitialize();
//
//                } else {
//                    Toast.makeText(getApplicationContext(), R.string.wrong_email_password, Toast.LENGTH_SHORT).show();
//                    hideProgressDialog();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SignIn> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"on failure",Toast.LENGTH_SHORT).show();
//                hideProgressDialog();
//              Toast.makeText(getApplicationContext(), R.string.sunable_to_connect, Toast.LENGTH_SHORT).show();
//            }
//        });

        RequestBody registrationNo = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody userpassword1=RequestBody.create(MediaType.parse("multipart/form-data"),password);
        Call<ResponseBody>call=searchApiInterface.uploadEmailPassword(registrationNo,userpassword1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.w(TAG, "onResponse: "+response.message() );
                if (response.message().equals("OK"))
                {
                    Log.w(TAG, "onResponse: success "+response.message() );
                    getEmployeeData(email,password);
                }
                else if (response.message().equals("Not Acceptable")){
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.putExtra("changePass",email);
                    startActivity(intent);
                    finish();

                }
                else if(response.message().equals("Not Implemented")){
                   Toast.makeText(getApplicationContext(),R.string.wrong_email_password,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.w(TAG, "onFailure: "+t.getMessage() );
                Toast.makeText(getApplicationContext(),R.string.sunable_to_connect,Toast.LENGTH_SHORT).show();
            }
        });
//        Call call=searchApiInterface.uploadEmailPassword(registrationNo,userpassword1);
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.w(TAG, "onResponse: hellow Test " );
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//
//            }
//        });


    }
    private void  getEmployeeData(final String RegistraionNo, final String password){
        Call<List<SearchModel>> getUserData=searchApiInterface.getRegistrationID(RegistraionNo,0);
        getUserData.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                if (response.isSuccessful() && !response.body().isEmpty()){
                    Toast.makeText(SignInActivity.this, R.string.success_in_signin, Toast.LENGTH_SHORT).show();
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
                    menuInitialize();
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(),"User not Found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
            }
        });
    }





    public void menuInitialize() {
        setResult(RESULT_OK);
        hideProgressDialog();
        finish();
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public boolean validate() {
        boolean valid = true;
        String mUserEmail = userEmail.getText().toString();
        String mUserPassword = userPassword.getText().toString();

        if (TextUtils.isEmpty(mUserEmail)) {
            userEmail.setError("enter a valid email address");
            valid = false;
        } else {
            userEmail.setError(null);
        }

        if (TextUtils.isEmpty(mUserPassword)) {
            userPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            userPassword.setError(null);
        }

        return valid;
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
