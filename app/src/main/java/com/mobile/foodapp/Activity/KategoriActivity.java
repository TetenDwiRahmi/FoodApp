package com.mobile.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mobile.foodapp.Adapter.ItemAdapter;
import com.mobile.foodapp.Model.Item;
import com.mobile.foodapp.Server.Server;
import com.mobile.foodapp.databinding.ActivityKategoriBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KategoriActivity extends AppCompatActivity {
    private ActivityKategoriBinding binding;
    List<Item> itemList;
    String id_kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKategoriBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);

        Intent i = new Intent(getIntent());
        id_kategori = String.valueOf(i.getIntExtra("id_kategori", 0));
        Log.d("id_kategori", "code" + id_kategori);

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KategoriActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        binding.recyclerPerkategori.setHasFixedSize(true);
        binding.recyclerPerkategori.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerPerkategori.addItemDecoration(new DividerItemDecoration( KategoriActivity.this, DividerItemDecoration.VERTICAL));
//        binding.recyclerPerkategori.setLayoutManager(new GridLayoutManager(this, 2));

        itemList = new ArrayList<>();

        MenuBerdasarkanKategori();
    }

    private void MenuBerdasarkanKategori() {
        AndroidNetworking.post(Server.site + "detailperkategori.php")
                .addBodyParameter("id_kategori", id_kategori)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = null;
                                item = response.getJSONObject(i);
                                itemList.add(new Item(
                                        item.getInt("id_item"),
                                        item.getInt("id_kategori"),
                                        item.getString("nama_kategori"),
                                        item.getString("nama_item"),
                                        item.getInt("harga_item"),
                                        item.getInt("stock"),
                                        item.getString("foto_item")
                                ));
                            }
                            ItemAdapter adapter = new ItemAdapter(KategoriActivity.this, itemList);
                            binding.recyclerPerkategori.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror", "code : " + anError);
                        Toast.makeText(KategoriActivity.this, "Gagal get Data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}