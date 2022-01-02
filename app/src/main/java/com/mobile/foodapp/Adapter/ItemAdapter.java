package com.mobile.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.foodapp.Activity.DetailActivity;
import com.mobile.foodapp.Model.Item;
import com.mobile.foodapp.R;
import com.mobile.foodapp.Server.Server;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewRecHolder> {
    private int selectedItemPosition = -1;
    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewRecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_item, null);
        return new ItemViewRecHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewRecHolder holder, final int position) {
        final Item item = itemList.get(position);
        holder.txtNamaItem.setText(item.getNama_item());
        holder.txtKategoriItem.setText(item.getNama_kategori());
        Locale localeId = new Locale("in", "ID");
        final NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeId);
        holder.txtHargaItem.setText(rupiah.format(item.getHarga_item()));
        Picasso.get().load(Server.site_foto + item.getFoto_item()).into(holder.imageItem);
        int stock = item.getStock();
        if (stock == 0) {
            holder.layoutTidakTersedia.setVisibility(View.VISIBLE);
        } else {
            holder.layoutTidakTersedia.setVisibility(View.GONE);
            holder.cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItemPosition = position;
                    notifyDataSetChanged();

                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("id_item", item.getId_item());
                    i.putExtra("stock", item.getStock());
                    context.startActivity(i);
                }
            });
        }

        if (selectedItemPosition == position) {
            holder.cardItem.setBackgroundColor(Color.parseColor("#E3E4DF"));
        } else
            holder.cardItem.setBackgroundColor(Color.parseColor("#ffffff"));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewRecHolder extends RecyclerView.ViewHolder {
        RelativeLayout cardItem, layoutTidakTersedia;
        ImageView imageItem;
        TextView txtNamaItem, txtKategoriItem, txtHargaItem;

        public ItemViewRecHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            cardItem = itemView.findViewById(R.id.cardItem);
            imageItem = itemView.findViewById(R.id.imageItem);
            txtNamaItem = itemView.findViewById(R.id.txtNamaItem);
            txtKategoriItem = itemView.findViewById(R.id.txtKategoriItem);
            txtHargaItem = itemView.findViewById(R.id.txtHargaItem);
            layoutTidakTersedia = itemView.findViewById(R.id.layoutTidakTersedia);
            cardItem = itemView.findViewById(R.id.cardItem);
        }
    }
}
