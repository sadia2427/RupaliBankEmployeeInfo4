package com.example.rupalibankemployeeinfo.ui.Browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rupalibankemployeeinfo.R;

public class BrowseFragment extends Fragment  implements View.OnClickListener {

    private GalleryViewModel galleryViewModel;
    LinearLayout mHeaderLayout;
    LinearLayout mFieldOfficesBranches;
    LinearLayout mOverseasBranches;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
//        galleryViewModel =
//                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mHeaderLayout=root.findViewById(R.id.id_headOffice);
        mFieldOfficesBranches=root.findViewById(R.id.field_offices);
        mOverseasBranches=root.findViewById(R.id.over_seasBranches);
        mHeaderLayout.setOnClickListener(this);
        mFieldOfficesBranches.setOnClickListener(this);
        mOverseasBranches.setOnClickListener(this);
//        mHeaderLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"Hello I,M on Click ",Toast.LENGTH_LONG).show();
//            }
//        });
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_headOffice:
                Toast.makeText(getActivity(),"headOfficeDivision",Toast.LENGTH_SHORT).show();
                Fragment fragment = new DivisionListFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.nav_host_fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
               break;

            case R.id.field_offices:
                Toast.makeText(getContext(),"Field Office Branches",Toast.LENGTH_LONG).show();
                break;

            case  R.id.over_seasBranches:
                Toast.makeText(getContext(),"Overseas Branches of HeadOffice", Toast.LENGTH_LONG).show();
        }

    }
}