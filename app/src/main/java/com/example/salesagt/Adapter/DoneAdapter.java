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
import com.example.salesagt.Model.DoneModel;
import com.example.salesagt.R;

import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.ViewHolder> {
    private Context mContext;
    private List<DoneModel> mDone;
    private View mEmptyView;
    private int clickCount=0;

    public DoneAdapter(Context mContext, List<DoneModel> mDone, View mEmptyView) {
        this.mContext = mContext;
        this.mDone = mDone;
        this.mEmptyView = mEmptyView;
    }

    @NonNull
    @Override
    public DoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customrecylerview_done,parent,false);
        return new DoneAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoneAdapter.ViewHolder holder, final int position) {
        if (mDone.size()==0){
            mEmptyView.setVisibility(View.VISIBLE);
        }else{
            mEmptyView.setVisibility(View.GONE);
        }
        holder.namaPerusahan.setText(mDone.get(position).getCompanyName());
        holder.sales.setText(mDone.get(position).getSalesName());
        holder.statuss.setText(mDone.get(position).getCheckStatus());
        holder.income.setText(mDone.get(position).getIncome());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailProgresActivity.class);
                intent.putExtra("id",mDone.get(position));
                intent.putExtra("doneprog",3);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {return (mDone!=null)?mDone.size():0;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;
        private TextView namaPerusahan,sales,statuss,income;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.recyle_linier_done);
            namaPerusahan= itemView.findViewById(R.id.namaperusahaan_recyle_done);
            sales = itemView.findViewById(R.id.namasales_recyle_done);
            statuss = itemView.findViewById(R.id.status_recyle_done);
            income = itemView.findViewById(R.id.income_recyle_done);
        }
    }
}
