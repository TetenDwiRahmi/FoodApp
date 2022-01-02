package com.mobile.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mobile.foodapp.PrefManager;
import com.mobile.foodapp.Server.Server;
import com.mobile.foodapp.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import static java.lang.String.*;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    String id_item, list_id_item, stock;
    PrefManager prefManager;
    int banyak, harga, status;
    int banyak_item, quantity, total_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);

        prefManager = new PrefManager(this);

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        Intent i = new Intent(getIntent());
        id_item = valueOf(i.getIntExtra("id_item", 0));
        stock = valueOf(i.getIntExtra("stock", 0));
        Log.d("id_item", "ID ITEM : " + id_item + " STOCK : " + stock);

        detailpesanan();
        gethargadanbanyakitem();

        binding.imageAddBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekKeranjang();
            }
        });

        insert_alamat();
    }

    ///menambah alamat ke db
    private void insert_alamat() {
        Log.d("tag", "ALamat : " + Server.site + "insert_penerima.php" + " nama : " + prefManager.getidKeranjang());
        AndroidNetworking.post(Server.site + "insert_penerima.php")
                .addBodyParameter("id_customer", prefManager.getIdUser())
                .addBodyParameter("namapengirim", prefManager.getidKeranjang())
                .addBodyParameter("kodepos", prefManager.getKodePos())
                .addBodyParameter("alamat", prefManager.getAlamat())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("kode").equalsIgnoreCase("1")) {
                                Log.d("tag", response.getString("pesan"));
                            } else {
                                Log.d("tag", response.getString("pesan"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag", "error alamat : " + anError +
                                "nama : " + prefManager.getidKeranjang() + " id : " + prefManager.getIdUser() +
                                " kodepos : " + prefManager.getKodePos() + " alama: " + prefManager.getAlamat());
                    }
                });
    }

    private void subtotal() {
        harga = 0;
        try {
            String d = prefManager.getHarga();
            harga = Integer.parseInt(d);
            Locale localeId = new Locale("in", "ID");
            final NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeId);
            binding.txtHargaItem.setText(rupiah.format(harga));
            binding.txtQuantity.setText("1");

            Log.d("harga " + d, "codeharga " + harga);

            binding.btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stock.equals(binding.txtQuantity.getText().toString())) {
                        Toast.makeText(DetailActivity.this, "Maaf !! Hanya " + stock + " Stock Yang Tersedia", Toast.LENGTH_SHORT).show();
                    } else {
                        banyak = Integer.parseInt(binding.txtQuantity.getText().toString()) + 1;
                        binding.txtQuantity.setText(String.valueOf(banyak));
                        int tot = banyak * harga;
                        Locale localeId = new Locale("in", "ID");
                        final NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeId);
                        binding.txtHargaItem.setText(rupiah.format(tot));
                        Log.d("tot " + tot, "codeharga " + harga);
                    }
                }
            });

            binding.btnKurang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    banyak = Integer.parseInt(binding.txtQuantity.getText().toString()) - 1;
                    if (banyak == 0) {
                        Toast.makeText(DetailActivity.this, "Pesanan Minimal 1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    binding.txtQuantity.setText(valueOf(banyak));
                    int tot = banyak * harga;
                    Locale localeId = new Locale("in", "ID");
                    final NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeId);
                    binding.txtHargaItem.setText(rupiah.format(tot));
                    Log.d("tot " + tot, "codeharga " + harga);
                }
            });

        } catch (NumberFormatException e) {
            System.out.println("parse value is not valid : " + e);
        }
    }


    public void cekKeranjang() {
        AndroidNetworking.post(Server.site + "keranjang.php")
                .addBodyParameter("id_customer", prefManager.getIdUser())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() >= 1) {
                                for (int i = 0; i < response.length(); i++) {
                                    String[] arrID = new String[response.length()];
                                    JSONObject k = response.getJSONObject(i);
                                    list_id_item = k.getString("id_item");
                                    arrID[i] = list_id_item;
                                    Log.d("tag ", "id Item : " + list_id_item + " Array ID : " + arrID[i]);
                                    if (id_item.equalsIgnoreCase(arrID[i])) {
                                        Log.d("tag ", "ID Yg Diklik : " + id_item);
                                        status = 1;
                                    }
                                }
                            }

                            if (status == 1) {
//                                Toast.makeText(DetailActivity.this, "Item sudah ada " + prefManager.getBanyak(), Toast.LENGTH_SHORT).show();
                                updateBanyakItem();
                            } else {
                                tambahkeranjang();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(DetailActivity.this, "Gagal Menambah Ke Keranjang", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void detailpesanan() {
        AndroidNetworking.post(Server.site + "detail_pesanan.php")
                .addBodyParameter("id_item", id_item)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("1")) {
                                binding.progress.setVisibility(View.GONE);
                                JSONObject data = response.getJSONObject("data");
                                prefManager.setHarga(data.getString("harga_item"));
                                binding.txtNamaKategori.setText(data.getString("nama_kategori"));
                                String foto = data.getString("foto_item");
                                Picasso.get().load(Server.site_foto + foto).into(binding.imageDetail);
                                binding.txtNamaItem.setText(data.getString("nama_item"));

                                subtotal();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "code : " + anError);
                        Toast.makeText(DetailActivity.this, "Gagal get Detail tanpa banyak", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateBanyakItem() {
        banyak_item = Integer.parseInt(prefManager.getBanyak());
        if (stock.equals(String.valueOf(banyak_item))) {
            Toast.makeText(this, "Stock tidak tersedia!!", Toast.LENGTH_SHORT).show();
        } else {
            quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
            total_item = banyak_item + quantity;
            AndroidNetworking.post(Server.site + "update_itemkeranjang.php")
                    .addBodyParameter("id_item", id_item)
                    .addBodyParameter("banyak_item", String.valueOf(total_item))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("status").equalsIgnoreCase("1")) {
                                    Log.d("tag ", " Banyak Item Berd ID : " + banyak_item + " Quantity : " + quantity +
                                            " Total Item : " + total_item);
                                    Intent i = new Intent(DetailActivity.this, KeranjangActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(DetailActivity.this, "Pesanan Gagal Masuk Ke Keranjnag", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("error", "code : " + anError);
                            Toast.makeText(DetailActivity.this, "Gagal Masukkan Data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void tambahkeranjang() {
        AndroidNetworking.post(Server.site + "insert_keranjang.php")
                .addBodyParameter("id_customer", prefManager.getIdUser())
                .addBodyParameter("id_item", id_item)
                .addBodyParameter("banyak_item", binding.txtQuantity.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("1")) {
                                Intent i = new Intent(DetailActivity.this, KeranjangActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(DetailActivity.this, "Pesanan Gagal Masuk Ke Keranjnag", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "code : " + anError);
                        Toast.makeText(DetailActivity.this, "Gagal Masukkan Data", Toast.LENGTH_SHORT).show();
                    }
                });
        Log.d("tag", "idI : " + id_item + " idC : " + prefManager.getIdUser() + " jml : " + binding.txtQuantity.getText());
    }


    private void gethargadanbanyakitem() {
        AndroidNetworking.post(Server.site + "detail_harga_banyak.php")
                .addBodyParameter("id_customer", prefManager.getIdUser())
                .addBodyParameter("id_item", id_item)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("1")) {
                                JSONObject data = response.getJSONObject("data");
                                prefManager.setBanyak(data.getString("banyak_item"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "code : " + anError);
                        Toast.makeText(DetailActivity.this, "Gagal get Detail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}