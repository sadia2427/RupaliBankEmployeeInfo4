package com.rblcontact.rupalibankemployeeinfo.ui.search;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rblcontact.rupalibankemployeeinfo.MainActivity;
import com.rblcontact.rupalibankemployeeinfo.R;
import com.rblcontact.rupalibankemployeeinfo.api.RetrofitSingleton;
import com.rblcontact.rupalibankemployeeinfo.api.apiInterface.SearchApiInterface;
import com.rblcontact.rupalibankemployeeinfo.api.model.SearchModel;
import com.rblcontact.rupalibankemployeeinfo.ui.employedetails.EmployeeDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SearchFragment extends Fragment   implements SearchView.OnCloseListener{

//    private OnFragmentInteractionListener mListener;
    private List<SearchModel> mSearchModel;
    private Retrofit mRetrofit;
    private SearchApiInterface mSearchApiInterface;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SearchAdapter mSearchAdapter;
    private Bundle mBundle;
    private String TAG="SearchFragment";
    private ProgressBar mProgressBar;

    private static int mRegID=0;
    private boolean loading = false;
    private TextView mSearchText;
    int pastVisiblesItems, visibleItemCount, totalItemCount;



//    boolean isLoading = false;
    public SearchFragment() {
        // Required empty public constructor
   }


    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(int param1) {
        SearchFragment fragment = new SearchFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        mRegID=param1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view=inflater.inflate(R.layout.fragment_search, container, false);
            mRetrofit= RetrofitSingleton.getInstance(getActivity());
            mSearchApiInterface=mRetrofit.create(SearchApiInterface.class);
            mRecyclerView=view.findViewById(R.id.search_recylerview);
            mSearchModel=new ArrayList<>();
            mBundle=new Bundle();
            mProgressBar=view.findViewById(R.id.search_progress);
            mSearchText=view.findViewById(R.id.search_text);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
//        {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
//            {
//                if(dy > 0) //check for scroll down
//                {
//                    visibleItemCount = mLayoutManager.getChildCount();
//                    totalItemCount = mLayoutManager.getItemCount();
//                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
//
//                    if (loading)
//                    {
//                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
//                        {
//                            loading = false;
//                            Log.w("onChange", "Last Item Wow !");
//                            //Do pagination.. i.e. fetch new data
//                        }
//                    }
//                }
//            }
//        });
        return view;
    }

//    private void populateData(){
//        for (int i=0;i<10;i++){
//            mSearchModel.add( i);
//
//        }
//    }


    private void initAdapter(String data){
        mProgressBar.setVisibility(View.VISIBLE);
        Log.w(TAG, "initAdapter: "+data );
        Call<List<SearchModel>> getData=mSearchApiInterface.getRegistrationID(data,0);
        getData.enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                if (response.isSuccessful()){

                    mSearchModel.clear();
                    if (response.body().size()>0){
                        mProgressBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mSearchModel.addAll(response.body());
                        Log.w(TAG, "onResponse: "+response.body().get(0) );
                        mSearchAdapter=new SearchAdapter(mSearchModel,getActivity());
                        mRecyclerView.setAdapter(mSearchAdapter);
                        Log.w(TAG, "onItemClick: onClick 12" );
                        mSearchAdapter.setOnItemClickListener(new SearchAdapter.SearchInterFace() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Log.w(TAG, "onItemClick: onClick 123" );
                                mBundle.putString("regNo", String.valueOf(mSearchModel.get(position).getEmpRegNo()));
                                mBundle.putString("EmpName",mSearchModel.get(position).getEmpName());
                                mBundle.putString("EmpNameBan",mSearchModel.get(position).getEmpNameBN());
                                mBundle.putString("EmpMob",mSearchModel.get(position).getEmpMobile());
                                mBundle.putString("empEmail",mSearchModel.get(position).getEmpEmail());
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
                    else {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Sorry no Data Found", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

//    private void initScrollListener(){
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged( RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//
//                if (!loading) {
//                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mSearchModel.size() - 1) {
//                        //bottom of list!
//                        loadMore();
//                        loading = true;
//                    }
//                }
//            }
//        });
//    }

//    private void loadMore(){
//        mSearchModel.clear();
//        mSearchAdapter.notifyItemInserted(mSearchModel.size() - 1);
//
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mSearchModel.remove(mSearchModel.size() - 1);
//                int scrollPosition = mSearchModel.size();
//                mSearchAdapter.notifyItemRemoved(scrollPosition);
//                int currentSize = scrollPosition;
//                int nextLimit = currentSize + 10;
//
//                while (currentSize - 1 < nextLimit) {
//                    mSearchModel.add("Item " + currentSize);
//                    currentSize++;
//                }
//
//                mSearchAdapter.notifyDataSetChanged();
//                loading = false;
//            }
//        }, 2000);
//
//
//    }
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menusearch, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                populateData();
                initAdapter(query);
                mSearchText.setVisibility(View.GONE);
//                initScrollListener();


                    return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length()>2) {
                    mSearchText.setVisibility(View.GONE);
                    initAdapter(newText);
                }
                return false;
            }
        });


        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onClose() {
        initAdapter("");
        return false;
    }
    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
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
//
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
