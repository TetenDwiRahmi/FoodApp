package com.mobile.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mobile.foodapp.Model.Keranjang;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class KeranjangAdapter extends RecyclerView.Adapter <KeranjangAdapter.OrderViewRecHolder>{
    private Context context;
    private List<Keranjang> keranjangList;

    public KeranjangAdapter(Context context, List<Keranjang> keranjangList) {
        this.context = context;
        this.keranjangList = keranjangList;
    }

    @NonNull
    @Override
    public KeranjangAdapter.OrderViewRecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_keranjang, null);
        return new OrderViewRecHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull KeranjangAdapter.OrderViewRecHolder holder, final int position) {
        final Keranjang keranjang = keranjangList.get(position);
        holder.NamaItem.setText(keranjang.getNama_item());
        holder.BanyakItem.setText("Banyak : "+keranjang.getBanyak_item());
        holder.HargaItem.setText("Rp. "+ keranjang.getHarga_item());
        holder.SubTotal.setText("Rp. "+ keranjang.getTotal());
        Picasso.get().load(Server.site_foto + keranjang.getFoto_item()).into(holder.FotoItem);
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusdata(keranjang.getId_keranjang());
                keranjangList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, keranjangList.size());
                notifyDataSetChanged();
            }
        });

    }

    private void hapusdata(int id_keranjang) {
        AndroidNetworking.post(Server.site + "delete_keranjang.php")
                .addBodyParameter("id_keranjang", id_keranjang + "")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("sukses", "code : "+response);
                            if (response.getString("status").equalsIgnoreCase("1")){
                                Toast.makeText(context, "Berhasil Menghapus Pesanan", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror","code : "+anError);
                        Toast.makeText(context, "Gagal Menghapus Pesanan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return keranjangList.size();
    }

    public class OrderViewRecHolder extends RecyclerView.ViewHolder {
        TextView NamaItem, HargaItem, BanyakItem, SubTotal, TotalItem;
        ImageView FotoItem;
        ImageButton imageUpdate, imageDelete;
        CardView cardKeranjang;

        public OrderViewRecHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            NamaItem = itemView.findViewById(R.id.NamaItem);
            HargaItem = itemView.findViewById(R.id.HargaItem);
            BanyakItem = itemView.findViewById(R.id.BanyakItem);
            SubTotal = itemView.findViewById(R.id.Subtotal);
            TotalItem = itemView.findViewById(R.id.TotalItem);
            FotoItem = itemView.findViewById(R.id.FotoItem);
            imageUpdate = itemView.findViewById(R.id.imageUpdate);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            cardKeranjang = itemView.findViewById(R.id.cardKeranjang);
        }
    }
}
