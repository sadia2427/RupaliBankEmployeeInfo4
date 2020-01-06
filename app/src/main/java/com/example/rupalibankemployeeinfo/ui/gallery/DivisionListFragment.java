package com.example.rupalibankemployeeinfo.ui.gallery;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.rupalibankemployeeinfo.R;
import com.example.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.example.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.example.rupalibankemployeeinfo.api.model.DivisionalList;
import com.example.rupalibankemployeeinfo.api.model.ZonalList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DivisionListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DivisionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DivisionListFragment extends Fragment {
    private String TAG="DivisionListFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private List<DivisionalList> mDivisionalLists;
    private List<ZonalList> zonalLists;
    private Retrofit mRetrofit;
    private SearchApiInterface mSearchApiInterface;;
    private LinearLayoutManager linearLayoutManager;
    private BrowseAdapter mBrowseAdapter;
    private Bundle bundle;
    private Bundle mBundle;
    private int divisionID=0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DivisionListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DivisionListFragment newInstance(String param1, String param2) {
        DivisionListFragment fragment = new DivisionListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_division_list, container, false);
        mDivisionalLists=new ArrayList<>();
        zonalLists=new ArrayList<>();
        mRetrofit= RetrofitSingleton.getInstance(getActivity());
        mSearchApiInterface=mRetrofit.create(SearchApiInterface.class);
        mRecyclerView=view.findViewById(R.id.division_list_rv);
        linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        bundle=this.getArguments();
        if (bundle!=null) {
            divisionID = Integer.parseInt(bundle.getString("divisionID"));
            getZonalList(divisionID);
        }
        else {
            getDivisionList();
        }
        return view;
    }
    private void  getDivisionList(){
        Call<List<DivisionalList>> getData=mSearchApiInterface.getDivisionList();
        getData.enqueue(new Callback<List<DivisionalList>>() {
            @Override
            public void onResponse(Call<List<DivisionalList>> call, Response<List<DivisionalList>> response) {
                mDivisionalLists.clear();
                mDivisionalLists.addAll(response.body());
                Log.w(TAG, "onResponse: "+response.body().get(0).getDivisionalOfficeName() );
                mBrowseAdapter=new BrowseAdapter(mDivisionalLists,getActivity());
                mRecyclerView.setAdapter(mBrowseAdapter);
                mBrowseAdapter.setOnItemClickListener(new BrowseAdapter.BrowseInterFace() {
                    @Override
                    public void onItemClick(int position, View v) {
                        mBundle=new Bundle();
                        mBundle.putString("divisionID",String.valueOf(mDivisionalLists.get(position).getDivisionID()));
                       Fragment fragment=new DivisionListFragment();
                       fragment.setArguments(mBundle);
                        FragmentManager fm=getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.nav_host_fragment, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<DivisionalList>> call, Throwable t) {
                Log.w(TAG, "onFailure: " +t.getMessage()+"    "+t.getMessage());
            }
        });

    }

    private void getZonalList(int divisionId){

        Toast.makeText(getActivity(),String.valueOf(divisionId),Toast.LENGTH_SHORT).show();
        Call<List<ZonalList>> getData=mSearchApiInterface.getZonalListID(divisionId);
        getData.enqueue(new Callback<List<ZonalList>>() {
            @Override
            public void onResponse(Call<List<ZonalList>> call, Response<List<ZonalList>> response) {
                if(response.body().size()>0 && response.isSuccessful()){
                    Toast.makeText(getActivity(),"ZonalList 11111",Toast.LENGTH_SHORT).show();
                    zonalLists.clear();
                    zonalLists.addAll(response.body());
                    mBrowseAdapter=new BrowseAdapter(zonalLists,getActivity(),1);
                    mRecyclerView.setAdapter(mBrowseAdapter);
                    mBrowseAdapter.setOnItemClickListener(new BrowseAdapter.BrowseInterFace() {
                        @Override
                        public void onItemClick(int position, View v) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ZonalList>> call, Throwable t) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
