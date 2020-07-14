package com.rblcontact.rupalibankemployeeinfo.ui.employedetails;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rblcontact.rupalibankemployeeinfo.MainActivity;
import com.rblcontact.rupalibankemployeeinfo.R;
import com.rblcontact.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.rblcontact.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.rblcontact.rupalibankemployeeinfo.api.model.SearchModel;

import java.io.IOException;
import java.io.InputStream;
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
    private ImageView mEmployeeImage;
    private ProgressBar mProgressBar;


    private String mMobileNo;
    private String mRegistrationNo;
    private String mEmployeeName;
    private String mEmployeeNameBn;
    private String mEmployeeEmail;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //popBackStack();
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
        mEmployeeImage=view.findViewById(R.id.imageView2);
        mProgressBar=view.findViewById(R.id.progressBar);
        callNowbtn.setOnClickListener(this);
        messaggeNowBtn.setOnClickListener(this);
        bundle=this.getArguments();
        if (bundle!=null) {
            mRegistrationNo = bundle.getString("regNo");
            mEmployeeName=bundle.getString("EmpName");
            mEmployeeNameBn=bundle.getString("EmpNameBan");
            mMobileNo=bundle.getString("EmpMob");
            mEmployeeEmail=bundle.getString("empEmail");
            Log.w(TAG, "onCreateView: "+mRegistrationNo+"  "+mEmployeeName+"  "+mEmployeeNameBn+"   "+mEmployeeEmail+"   "+mMobileNo );
            getEmployeeData(mRegistrationNo);
        }


        return view;
    }

    private void  getEmployeeData(final String RegistraionNo){
        Call<List<SearchModel>> getUserData=mSearchApiInterface.getRegistrationID(RegistraionNo,0);
        getUserData.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                if (response.isSuccessful()){
                    for (int i=0;i<=mSearchModel.size();i++){
                        try {
                            //////////////////////////////////////////////////////

            String urlLink="http://103.125.136.110/picture/photos/" + RegistraionNo + ".jpg";
            if(urlLink.isEmpty()){
               // Toast.makeText(getApplicationContext(),"Please Enter Url!!",Toast.LENGTH_SHORT).show();
            }else{
                LoadImage loadImage= new LoadImage(mEmployeeImage);
                loadImage.execute(urlLink);


            }
                            ////////////////////////////////////////////////////

                           /* Glide.with(getActivity())
                                    .load("http://103.125.136.110/picture/photos/" + RegistraionNo + ".jpg")
                                
                                    .into(mEmployeeImage);*/
                            Log.w("regNo ", "RegistraionNo Not fouNd:------------------------- |"+RegistraionNo+"|");
                        }
                        catch (Exception e){
                            Toast.makeText(getActivity(), "Slow Internet Connection can't load Image",Toast.LENGTH_SHORT).show();
                        }

                        if (mMobileNo==null){
                        mMobileNo=response.body().get(i).getEmpMobile();
                        }
                        mRegistrationNotv.setText(RegistraionNo);
                        if(mEmployeeName==null){
                            mEmployeeNameTv.setText(response.body().get(i).getEmpName());
                        }
                        else {
                            mEmployeeNameTv.setText(mEmployeeName);
                        }
                       if(mEmployeeNameBn==null) {
                           mEmployeeNameBanglaTv.setText(response.body().get(i).getEmpNameBN());
                       }
                       else {
                           mEmployeeNameBanglaTv.setText(mEmployeeNameBn);
                       }
                       if (!response.body().isEmpty()&& response.body().get(i).getDesignationName()!=null){
                           mDesignationTv.setText(response.body().get(i).getDesignationName());
                       }
                        if(!response.body().isEmpty()&& response.body().get(i).getOfficeName()!=null){
                            mPlaceOfPosting.setText(response.body().get(i).getOfficeName());
                        }

                        mMobileNoTv.setText(mMobileNo);
                        if (mEmployeeEmail==null) {
                            mEmailNoTv.setText(response.body().get(i).getEmpEmail());
                        }
                        else {
                            mEmailNoTv.setText(mEmployeeEmail);
                        }
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


                {
                Intent dial = new Intent();
                dial.setAction(Intent.ACTION_DIAL);
                dial.setData(Uri.parse("tel:" + mMobileNo));
                startActivity(dial);
                break;
                }



            case R.id.messagenow_button:
//                String messageNumber = "01876068187";
//                Intent dialn = new Intent();
//                dialn.setAction("android.intent.action.sms");
//                dialn.setData(Uri.parse("tel:" + messageNumber));
//                startActivity(dialn);
                /*  val  intent=Intent().apply{
                  action= Intent.ACTION_TOSEND
                  data=Uri.parse("smsto:"
                          + mMobileNo)
                  putExtra("sms-body","text ma")
                  }

                  if(intent.resolveActivity(packageManager)!=null)
                  {
                      startActivity(intent);
                  }*/
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"
                        + mMobileNo)));



                /*startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + mMobileNo)));*/
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
    @Override
    public void onDetach() {
        super.onDetach();

    }

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

    ///////////////////////////////////////////////////////////////////////////////////////////


    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView ivResult) {
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;

            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mEmployeeImage.setImageBitmap(bitmap);
            mProgressBar.setVisibility(View.GONE);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////
}
