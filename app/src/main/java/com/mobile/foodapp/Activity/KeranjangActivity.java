package com.mobile.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.mobile.foodapp.Adapter.KeranjangAdapter;
import com.mobile.foodapp.Interface.InterfaceHarga;
import com.mobile.foodapp.Model.Keranjang;
import com.mobile.foodapp.PrefManager;
import com.mobile.foodapp.Server.Server;
import com.mobile.foodapp.databinding.ActivityKeranjangBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class KeranjangActivity extends AppCompatActivity implements InterfaceHarga {
    private ActivityKeranjangBinding binding;
    List<Keranjang> keranjangList;
    PrefManager prefManager;
    int total, harga, banyak;
    String id_keranjang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeranjangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);

        prefManager = new PrefManager(this);

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KeranjangActivity.this, CheckoutActivity.class);
                startActivity(i);
            }
        });
        binding.btnBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KeranjangActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        Intent i = new Intent(getIntent());
        id_keranjang = valueOf(i.getIntExtra("id_keranjang", 0));
        Log.d("id_keranjang", "code : " + id_keranjang);

        keranjangList = new ArrayList<>();
        binding.recyclerKeranjang.setHasFixedSize(true);
        binding.recyclerKeranjang.setLayoutManager(new LinearLayoutManager(this));

        getItem();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(KeranjangActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void getItem() {
        AndroidNetworking.post(Server.site + "keranjang.php")
                .addBodyParameter("id_customer", prefManager.getIdUser())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() >= 1) {
                            binding.checkoutLayout.setVisibility(View.VISIBLE);
                            try {
                                total = 0;
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject k = response.getJSONObject(i);
                                    keranjangList.add(new Keranjang(
                                            k.getInt("id_keranjang"),
                                            k.getString("nama_item"),
                                            k.getInt("banyak_item"),
                                            k.getInt("harga_item"),
                                            k.getInt("stock"),
                                            k.getString("foto_item")
                                    ));
                                    harga = k.getInt("harga_item");
                                    banyak = k.getInt("banyak_item");
                                    total = (harga * banyak) + total;
                                    binding.HargaSubTotal.setText("Rp. " + total);

                                }
//                                Log.d("tag " , " Total : " + total);
                                adapterKeranjang();
//                                KeranjangAdapter adapter = new KeranjangAdapter(KeranjangActivity.this, keranjangList);
//                                binding.recyclerKeranjang.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            binding.CartEmptyLayout.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag", "error :" + anError);
                    }
                });
        Log.d("tag", " idC : " + prefManager.getIdUser());
    }

    private void adapterKeranjang() {
        KeranjangAdapter adapter = new KeranjangAdapter(keranjangList, this);
        binding.recyclerKeranjang.setAdapter(adapter);
    }

    @Override
    public void UpdateHarga() {
        AndroidNetworking.post(Server.site + "keranjang.php")
                .addBodyParameter("id_customer", prefManager.getIdUser())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            total = 0;
                            if (response.length() >= 1) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject k = response.getJSONObject(i);
                                    harga = k.getInt("harga_item");
                                    banyak = k.getInt("banyak_item");
                                    total = (harga * banyak) + total;
                                    binding.HargaSubTotal.setText("Rp. " + total);
                                    Log.d("tag ", "harga : " + harga + " banyak : " + banyak + " total : " + total);
                                }
                            } else {
                                binding.checkoutLayout.setVisibility(View.INVISIBLE);
                                binding.CartEmptyLayout.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(KeranjangActivity.this, "Gagal Menambah Harga", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}