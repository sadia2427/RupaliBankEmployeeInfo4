package com.example.rupalibankemployeeinfo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.rupalibankemployeeinfo.R;
import com.example.rupalibankemployeeinfo.ui.search.SearchFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;
    private ImageView mImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set action of fab button
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                SearchFragment fragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.example_fragment);
//                fragment.<specific_function_name>();
                Fragment fragment=new SearchFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.nav_host_fragment, fragment);
                ft.addToBackStack(null);
                fm.popBackStackImmediate();
                ft.commit();
            }
        });
//        final TextView textView = root.findViewById(R.id.text_home);
//        mImageView=root.findViewById(R.id.home_page);
//        mImageView.setImageDrawable(R.drawable.app_homepage);

//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
////                textView.setText(s);
//            }
//        });
        return root;
    }
}