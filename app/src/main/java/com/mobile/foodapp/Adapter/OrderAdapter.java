package com.mobile.foodapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.foodapp.Activity.DetailOrderActivity;
import com.mobile.foodapp.Model.DetailOrder;
import com.mobile.foodapp.R;
import com.mobile.foodapp.Server;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.DetailOrderViewRecHolder>{
    private List<DetailOrder> detailOrderList;

    public DetailOrderAdapter(List<DetailOrder> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }

    @NonNull
    @Override
    public DetailOrderViewRecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_pesanan, null);
        return new DetailOrderAdapter.DetailOrderViewRecHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailOrderViewRecHolder holder, int position) {
        final DetailOrder detailOrder = detailOrderList.get(position);
        holder.statusOrder.setText(detailOrder.getStatus_order());
        holder.NamaItemDO.setText(detailOrder.getNama_item());
        Locale locale = new Locale("in", "ID");
        final NumberFormat rp = NumberFormat.getCurrencyInstance(locale);
        holder.JumlahItemDO.setText("x" + detailOrder.getBanyak_item());
        holder.HargaItemDO.setText(rp.format(detailOrder.getHarga_item()));
        holder.TotalProdukDO.setText(detailOrder.getTotal_item() + " Item");
        holder.TotalPesananDO.setText(rp.format(detailOrder.getTotal_bayar()));
        Picasso.get().load(Server.site_foto + detailOrder.getFoto_item()).into(holder.imgOrder);
        holder.LayoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetailOrderActivity.class);
                i.putExtra("id_order", detailOrder.getId_order());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailOrderList.size();
    }

    public class DetailOrderViewRecHolder extends RecyclerView.ViewHolder {
        TextView statusOrder, NamaItemDO, JumlahItemDO, HargaItemDO, TotalProdukDO, TotalPesananDO;
        ImageView imgOrder;
        RelativeLayout LayoutOrder;
        public DetailOrderViewRecHolder(@NonNull View itemView) {
            super(itemView);
            statusOrder = itemView.findViewById(R.id.statusOrder);
            NamaItemDO = itemView.findViewById(R.id.NamaItemDO);
            JumlahItemDO = itemView.findViewById(R.id.JumlahItemDO);
            HargaItemDO = itemView.findViewById(R.id.HargaItemDO);
            TotalProdukDO = itemView.findViewById(R.id.TotalProdukDO);
            TotalPesananDO = itemView.findViewById(R.id.TotalPesananDO);
            imgOrder = itemView.findViewById(R.id.FotoItemDO);
            LayoutOrder = itemView.findViewById(R.id.LayoutOrder);
        }
    }
}
