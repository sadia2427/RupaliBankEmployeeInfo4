package com.example.rupalibankemployeeinfo.ui.signin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.rupalibankemployeeinfo.R;

public class AboutFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private TextView mHeaderTv;
    private TextView mFooterTv;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signin, container, false);
        mHeaderTv=root.findViewById(R.id.text_about_header);
        mFooterTv=root.findViewById(R.id.text_footer);
        mFooterTv.setText("Designed & Developed By ");
        mHeaderTv.setText("© 2020 all rightts reserved Rupali Bank Limited Developed By ICT Operation Division");
//        final TextView textView = root.findViewById(R.id.text_tools);
//        toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}