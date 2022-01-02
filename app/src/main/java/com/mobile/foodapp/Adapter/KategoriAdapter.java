package com.mobile.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.foodapp.Activity.KategoriActivity;
import com.mobile.foodapp.Model.Kategori;
import com.mobile.foodapp.R;
import com.mobile.foodapp.Server.Server;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewRecHolder> {
    private Context context;
    private List<Kategori> kategoriList;

    public KategoriAdapter(Context context, List<Kategori> kategoriList) {
        this.context = context;
        this.kategoriList = kategoriList;
    }

    @NonNull
    @Override
    public KategoriViewRecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_kategori, null);
        return new KategoriAdapter.KategoriViewRecHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull KategoriViewRecHolder holder, int position) {
        final Kategori kategori = kategoriList.get(position);
        //holder.txtNamaKategori.setText(kategori.getNama_kategori());
        holder.cardKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, KategoriActivity.class);
                i.putExtra("id_kategori", kategori.getId_kategori());
                context.startActivity(i);
            }
        });
        Picasso.get().load(Server.site_foto + kategori.getFoto_kategori())
                .into(holder.imageKategori);
    }


    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    class KategoriViewRecHolder extends RecyclerView.ViewHolder {
        CardView cardKategori;
        TextView txtNamaKategori;
        ImageView imageKategori;

        public KategoriViewRecHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            cardKategori = itemView.findViewById(R.id.cardKategori);
            txtNamaKategori = itemView.findViewById(R.id.txtNamaKategori);
            imageKategori = itemView.findViewById(R.id.imageKategori);
        }
    }
}
