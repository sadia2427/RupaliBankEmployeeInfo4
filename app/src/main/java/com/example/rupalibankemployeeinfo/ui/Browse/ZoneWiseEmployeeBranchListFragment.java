package com.example.rupalibankemployeeinfo.ui.Browse;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.rupalibankemployeeinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoneWiseEmployeeBranchListFragment extends Fragment {

    private RecyclerView mEmployeeRecyler;
    private RecyclerView mBranchRecyler;
    private ProgressBar mProgressBar;
    public ZoneWiseEmployeeBranchListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_zone_wise_employee_branch_list, container, false);
        return view;
    }

}
