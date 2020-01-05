package com.example.rupalibankemployeeinfo.ui.gallery;

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
import com.example.rupalibankemployeeinfo.ui.search.SearchAdapter;

import java.util.List;

public class BrowseAdapter extends RecyclerView.Adapter<BrowseAdapter.GalleryViewHolder>{
    private List<DivisionalList> mDivisionalLists;
    private BrowseInterFace mBrowseInterFace;
    private Context mContext;

    public  BrowseAdapter(List<DivisionalList>divisionalLists, Context context){
        mDivisionalLists=divisionalLists;
        mContext=context;

    }



    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_divisionlist_adapter, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder( GalleryViewHolder holder, int position) {
        holder.mDivisionListTv.setText(mDivisionalLists.get(position).getDivisionalOfficeName());

    }

    @Override
    public int getItemCount() {
        return  mDivisionalLists == null ? 0 : mDivisionalLists.size();
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
