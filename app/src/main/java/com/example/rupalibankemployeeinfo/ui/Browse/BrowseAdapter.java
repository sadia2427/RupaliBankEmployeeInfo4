package com.example.rupalibankemployeeinfo.ui.Browse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rupalibankemployeeinfo.R;
import com.example.rupalibankemployeeinfo.api.model.DivisionalList;
import com.example.rupalibankemployeeinfo.api.model.Zone;

import java.util.List;

public class BrowseAdapter extends RecyclerView.Adapter<BrowseAdapter.GalleryViewHolder>{
    private List<DivisionalList> mDivisionalLists;
    private List<Zone>mZonalLists;
    private BrowseInterFace mBrowseInterFace;
    private Context mContext;
    private int id=0;

    public  BrowseAdapter(List<DivisionalList>divisionalLists, Context context){
        mDivisionalLists=divisionalLists;
        mContext=context;
        this.id=id;

    }
    public BrowseAdapter(List<Zone> zonalLists, Context context, int id){
        mZonalLists=zonalLists;
        mContext=context;
        this.id=id;
    }



    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_divisionlist_adapter, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder( GalleryViewHolder holder, int position) {
        if (id==0) {
            holder.mDivisionListTv.setText(mDivisionalLists.get(position).getDivisionalOfficeName());
        }
        else {
            holder.mDivisionListTv.setText(mZonalLists.get(position).getZonalOfficeName());
        }

    }

    @Override
    public int getItemCount() {
        if (id==0) {
            return mDivisionalLists == null ? 0 : mDivisionalLists.size();
        }
        else {
            return mZonalLists == null ? 0 : mZonalLists.size();
        }
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mDivisionLayout;
        private TextView mDivisionListTv;
        public GalleryViewHolder( View itemView) {
            super(itemView);
            mDivisionLayout=itemView.findViewById(R.id.div_adapter_ly);
            mDivisionListTv=itemView.findViewById(R.id.division_List);
            mDivisionLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mBrowseInterFace.onItemClick(getAdapterPosition(),v);
        }
    }

    public void setOnItemClickListener(BrowseInterFace browseInterFace){
        mBrowseInterFace=browseInterFace;
    }

    public interface BrowseInterFace{
        void onItemClick(int position, View v);

    }
}
