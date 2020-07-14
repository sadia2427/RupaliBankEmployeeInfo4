package com.rblcontact.rupalibankemployeeinfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rblcontact.rupalibankemployeeinfo.R;
import com.rblcontact.rupalibankemployeeinfo.api.model.BranchInfo;
import com.rblcontact.rupalibankemployeeinfo.listener.AdapterItemClickHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.BranchViewHolder> {

    private List<BranchInfo> mBranchInfoList;
    private AdapterItemClickHandler listener;

    public BranchListAdapter(List<BranchInfo> mBranchInfoList, AdapterItemClickHandler listener) {
        this.mBranchInfoList = mBranchInfoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_text_item,parent,false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, final int position) {
        holder.tvName.setText(mBranchInfoList.get(position).getOfficeName());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBranchInfoList == null ? 0 : mBranchInfoList.size() ;
    }

    public class BranchViewHolder extends RecyclerView.ViewHolder {

         @BindView(R.id.tvTitle)
        //@BindView(R.id.rvBranchList)
        TextView tvName;

        public BranchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
