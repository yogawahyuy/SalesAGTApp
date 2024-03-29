package com.example.salesagt.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.salesagt.Activity.DetailProgresActivity;
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
    public void onBindViewHolder(@NonNull ProgressAdapter.ViewHolder holder, final int position) {
        if (mProgres.size()==0){
            mEmptyView.setVisibility(View.VISIBLE);
        }else{
            mEmptyView.setVisibility(View.GONE);
        }
        holder.namaPerusahan.setText(mProgres.get(position).getCompanyName());
        holder.sales.setText(mProgres.get(position).getSalesName());
        holder.statuss.setText(mProgres.get(position).getCheckStatus());
        holder.income.setText(mProgres.get(position).getIncome());
        holder.date.setText(mProgres.get(position).getDate());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailProgresActivity.class);
                intent.putExtra("id",mProgres.get(position));
                intent.putExtra("prog",1);
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
        private TextView namaPerusahan,sales,statuss,income,date;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.recyle_linier);
            namaPerusahan=itemView.findViewById(R.id.namaperusahaan_recyle);
            sales=itemView.findViewById(R.id.namasales_recyle);
            statuss=itemView.findViewById(R.id.status_recyle);
            income=itemView.findViewById(R.id.income_recyle);
            date=itemView.findViewById(R.id.tanggal_progress);
        }
    }
}
