package com.rblcontact.rupalibankemployeeinfo.ui.ForgetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rblcontact.rupalibankemployeeinfo.R;
import com.rblcontact.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.rblcontact.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button email_sign_in_button;
    AutoCompleteTextView signinUserEmail;
    SearchApiInterface searchApiInterface;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_sign_in_button = findViewById(R.id.email_sign_in_button);
        signinUserEmail = findViewById(R.id.signinUserEmail);
        container = findViewById(R.id.container);
       // Intent intent=getIntent();
      //  String regNo=intent.getStringExtra("regNo");
        Intent intent = this.getIntent();
        String regNo = intent.getStringExtra("regNo").toString();
        //Log.d("++++++++++", regNo);
        //String regNo="13945";
        signinUserEmail.setText(regNo);
        Retrofit retrofit = RetrofitSingleton.getInstance(getBaseContext());
        searchApiInterface = retrofit.create(SearchApiInterface.class);

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signinUserEmail.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter your registration number", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap<String, String> data = new HashMap<>();
                data.put("regNo", signinUserEmail.getText().toString());

                Log.d("++++++++++", data.get("regNo"));

               Call<JsonObject> call = searchApiInterface.sendOTP("application/json",data);
               call.enqueue(new Callback<JsonObject>() {
                   @Override
                   public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                       Log.d("===========",call.request().toString());

                       if (response.code() == 200){
                           Log.d("==========", response.body().toString());
                           Toast.makeText(getApplicationContext(), "OTP Sent!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ForgotPasswordActivity.this, SetPasswordActivity.class)
                                    .putExtra("reg", signinUserEmail.getText().toString()));
                            finish();
                       }else{
                           Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<JsonObject> call, Throwable t) {
                       Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();

                   }
               });

            }
        });
    }
}
