package com.example.salesagt.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.salesagt.Activity.DetailProgresActivity;
import com.example.salesagt.Model.MyProgressModel;
import com.example.salesagt.R;


import java.util.List;
public class MyProgressAdapter extends RecyclerView.Adapter<MyProgressAdapter.ViewHolder> {
    private Context mContext;
    private List<MyProgressModel> mProgres;
    private View mEmptyView;

    public MyProgressAdapter(Context mContext, List<MyProgressModel> mProgres, View mEmptyView) {
        this.mContext = mContext;
        this.mProgres = mProgres;
        this.mEmptyView = mEmptyView;
    }

    @NonNull
    @Override
    public MyProgressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customrecylerview_myprogress,parent,false);
        return new MyProgressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProgressAdapter.ViewHolder holder, int position) {
        if (mProgres.size()==0){
            mEmptyView.setVisibility(View.VISIBLE);
        }else{
            mEmptyView.setVisibility(View.GONE);
        }
        holder.namaPerusahaan.setText(mProgres.get(position).getCompanyName());
        holder.namaSales.setText(mProgres.get(position).getSalesName());
        holder.checkStatus.setText(mProgres.get(position).getCheckStatus());
        holder.income.setText(mProgres.get(position).getIncome());
        holder.date.setText(mProgres.get(position).getDate());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailProgresActivity.class);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (mProgres!=null)?mProgres.size():0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;
        private TextView namaPerusahaan,namaSales,checkStatus,income,date;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.recyle_linier_myprogress);
            namaPerusahaan=itemView.findViewById(R.id.namaperusahaan_recyle_myprogress);
            namaSales=itemView.findViewById(R.id.namasales_recyle_myprogress);
            checkStatus=itemView.findViewById(R.id.status_recyle_myprogress);
            income=itemView.findViewById(R.id.income_recyle_myprogress);
            date=itemView.findViewById(R.id.tanggal_myprogress);
        }
    }
}
