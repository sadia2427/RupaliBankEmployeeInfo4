package com.example.rupalibankemployeeinfo.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rupalibankemployeeinfo.R;
import com.example.rupalibankemployeeinfo.api.model.SearchModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private String TAG="SearchAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public List<SearchModel> mSearchItemList;
    private SearchInterFace mSearchInterFace;
    private Context mContext;


    public SearchAdapter(List<SearchModel> itemList, Context context) {

        mSearchItemList = itemList;
        mContext=context;

    }
    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_adapter, parent, false);
            return new SearchViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
//            return new LoadingViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(SearchViewHolder viewHolder, int position) {
                Log.w("onBindViewHolder", "onBindViewHolder: " );
        viewHolder.mEmployeeNameTv.setText(mSearchItemList.get(position).getEmpName());
        viewHolder.mEmployeeDesignation.setText(mSearchItemList.get(position).getEmpNameBN());
        viewHolder.mEmployeePostingPlace.setText("Reg No : " + String.valueOf(mSearchItemList.get(position).getEmpRegNo()));
        viewHolder.mEmployeeMobileNo.setText(mSearchItemList.get(position).getEmpMobile());
//        if (holder instanceof SearchViewHolder) {
//
//            populateItemRows((SearchViewHolder) holder, position);
//        } else if (holder instanceof LoadingViewHolder) {
//            showLoadingView((LoadingViewHolder) holder, position);
//        }
    }



    @Override
    public int getItemCount() {
        return mSearchItemList == null ? 0 : mSearchItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mSearchItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mEmployeeNameTv;
        private TextView mEmployeeDesignation;
        private TextView mEmployeePostingPlace;
        private TextView mEmployeeMobileNo;
        private LinearLayout mLinearLayout;

        public SearchViewHolder( View itemView) {
            super(itemView);
            mEmployeeNameTv=itemView.findViewById(R.id.s_employee_nametv);
            mEmployeeDesignation=itemView.findViewById(R.id.s_employee_deg);
            mEmployeePostingPlace=itemView.findViewById(R.id.s_place_of_post);
            mEmployeeMobileNo=itemView.findViewById(R.id.s_employee_mobile);
            mLinearLayout=itemView.findViewById(R.id.search_adapter_ly);
            mLinearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Log.w(TAG, "onClick:searchAdapter "+ String.valueOf(getAdapterPosition()) );
            mSearchInterFace.onItemClick(getAdapterPosition(),v);

        }
    }

//    public class  LoadingViewHolder extends RecyclerView.ViewHolder{
//        ProgressBar progressBar;
//
//        public LoadingViewHolder( View itemView) {
//            super(itemView);
//            progressBar = itemView.findViewById(R.id.progressBar);
//        }
//    }

//    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
//        //ProgressBar would be displayed
//
//    }

//    private void populateItemRows(SearchViewHolder viewHolder, int position) {
//
////        List<SearchViewHolder> model = mSearchItemList.get(position);
////        viewHolder.tvItem.setText(item);
//        mSearchItemList=new ArrayList<>();
//        viewHolder.mEmployeeNameTv.setText(mSearchItemList.get(position).getEmpName());
//        viewHolder.mEmployeeDesignation.setText(mSearchItemList.get(position).getDesignationName());
//        viewHolder.mEmployeePostingPlace.setText(mSearchItemList.get(position).getOfficeName());
//        viewHolder.mEmployeeMobileNo.setText(mSearchItemList.get(position).getEmpMobile());
//    }
    public void setOnItemClickListener(SearchInterFace searchInterFace){
        mSearchInterFace=searchInterFace;
    }

    public interface SearchInterFace{
        void onItemClick(int position, View v);

    }
}
