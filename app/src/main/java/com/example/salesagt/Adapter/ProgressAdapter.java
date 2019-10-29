package com.example.salesagt.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.salesagt.Model.ProgressModel;
import com.example.salesagt.R;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {

    private Context mContext;
    private List<ProgressModel> mProgres;
    private View mEmptyView;
    private int clickCount=0;

    public ProgressAdapter(Context context,List<ProgressModel> mProgres,View mEmptyView){
        this.mContext=context;
        this.mProgres=mProgres;
        this.mEmptyView=mEmptyView;
    }

    @NonNull
    @Override
    public ProgressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customrecyleview_dashboard,parent,false);
        return new ProgressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressAdapter.ViewHolder holder, int position) {
        if (mProgres.size()==0){
            mEmptyView.setVisibility(View.VISIBLE);
        }else{
            mEmptyView.setVisibility(View.GONE);
        }
        holder.namaPerusahan.setText(mProgres.get(position).getCompanyName());
        holder.sales.setText(mProgres.get(position).getSalesName());
        holder.statuss.setText(mProgres.get(position).getCheckStatus());
        holder.income.setText(mProgres.get(position).getIncome());


    }

    @Override
    public int getItemCount() {
        return (mProgres!=null)?mProgres.size():0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView namaPerusahan,sales,statuss,income;
        public ViewHolder(View itemView) {
            super(itemView);
            namaPerusahan=itemView.findViewById(R.id.namaperusahaan_recyle);
            sales=itemView.findViewById(R.id.namasales_recyle);
            statuss=itemView.findViewById(R.id.status_recyle);
            income=itemView.findViewById(R.id.income_recyle);
        }
    }
}
