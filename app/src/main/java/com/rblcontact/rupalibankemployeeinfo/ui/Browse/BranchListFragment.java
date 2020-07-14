package com.rblcontact.rupalibankemployeeinfo.ui.Browse;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rblcontact.rupalibankemployeeinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BranchListFragment extends Fragment {


    public BranchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_branch_list, container, false);
    }

}
