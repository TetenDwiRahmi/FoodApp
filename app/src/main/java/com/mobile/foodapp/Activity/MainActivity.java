package com.mobile.foodapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.foodapp.Adapter.ItemAdapter;
import com.mobile.foodapp.Adapter.KategoriAdapter;
import com.mobile.foodapp.Model.Item;
import com.mobile.foodapp.Model.Kategori;
import com.mobile.foodapp.PrefManager;
import com.mobile.foodapp.R;
import com.mobile.foodapp.Server.Server;
import com.mobile.foodapp.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    List<Kategori> kategoriList;
    List<Item> itemList;
    TextView txtUser;
    PrefManager prefManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    Geocoder geocoder;
    List<Address> addresses;
    String address, postalCode, nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AndroidNetworking.initialize(this);

        MenuPerkategori();
        ListMenu();

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MenuPerkategori();
                ListMenu();
            }
        });

        prefManager = new PrefManager(this);
        if (!prefManager.getIdUser().isEmpty()) {
            getfotoprofile();
        }
        Log.d("id User", " id : " + prefManager.getIdUser());


        geocoder = new Geocoder(this, Locale.getDefault());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION}
                    , 100);
        }


        binding.recyclerKategori.setHasFixedSize(true);
        binding.recyclerKategori.setLayoutManager(new LinearLayoutManager(this, GridLayoutManager.HORIZONTAL, false));

        binding.recyclerItem.setHasFixedSize(true);
        binding.recyclerItem.setLayoutManager(new LinearLayoutManager(this, GridLayoutManager.VERTICAL, false));
        binding.recyclerItem.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
//        binding.recyclerItem.setLayoutManager(new GridLayoutManager(this, 2));

        //show hide bottom navigation while scrolling
        binding.recyclerItem.setOnTouchListener(new TranslateAnimation(this, binding.cardMenu));

        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfilActivity.class);
                i.putExtra("id_customer", prefManager.getIdUser());
                startActivity(i);
            }
        });

        txtUser = findViewById(R.id.txtUser);

        kategoriList = new ArrayList<>();
        itemList = new ArrayList<>();

        ImageSlider imageSlider = findViewById(R.id.image_slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.image_slide1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_slide2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_slide3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        binding.btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.keranjang_nav:
                        Intent i = new Intent(MainActivity.this, KeranjangActivity.class);
                        startActivity(i);
                        break;
                    case R.id.user_nav:
                        Intent in = new Intent(MainActivity.this, AkunActivity.class);
                        startActivity(in);
                        break;
                    case R.id.pesanan_nav:
                        Intent it = new Intent(MainActivity.this, OrderActivity.class);
                        it.putExtra("id_customer", prefManager.getIdUser());
                        startActivity(it);
                        break;
                }
                return true;
            }
        });

    }



    ///LOKASI
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                detectLocation();
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
               detectLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void detectLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        address = addresses.get(0).getAddressLine(0);
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    LocationRequest locationRequest = new LocationRequest()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(10000)
                            .setFastestInterval(1000)
                            .setNumUpdates(1);
                    LocationCallback locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            Location location1 = locationResult.getLastLocation();
                            try {
                                addresses = geocoder.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1);
                                address = addresses.get(0).getAddressLine(0);
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest
                            , locationCallback, Looper.myLooper());
                }
                prefManager.setAlamat(address);
                prefManager.setKodePos(postalCode);
                Log.d("tag", "idCUs : " + prefManager.getIdUser() + " alamat : " + prefManager.getAlamat() + " kode : " + prefManager.getKodePos());
                //insert alamat ketika user klik Detail Menu (DetailActivity.java)
            }
        });
    }

    ///END LOKASI


//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        setMode(item.getItemId());
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void setMode(int itemId) {
//        switch (itemId) {
//            case R.id.logout:
//                new AlertDialog.Builder(this)
//                        .setTitle("Perhatian")
//                        .setMessage("Apakah anda yakin ingin keluar?")
//                        .setCancelable(false)
//                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //Aksi Logout
//                                prefManager.setLoginStatus(false);
//                                prefManager.setIdUser("");
//                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                                startActivity(i);
//                            }
//                        })
//                        .setNegativeButton("Tidak", null)
//                        .show();
//                break;
//            case R.id.keranjang:
//                Intent i = new Intent(MainActivity.this, KeranjangActivity.class);
//                startActivity(i);
//                break;
//        }
//    }

    private void getfotoprofile() {
        AndroidNetworking.post(Server.site + "detailprofil_customer.php")
                .addBodyParameter("id_customer", prefManager.getIdUser())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("data ", " code :" + response);
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject data = response.getJSONObject(i);
                                nama = data.getString("username");
                                txtUser.setText("Hello " + nama +"!");
                                String foto = data.getString("foto_customer");
                                Picasso.get().load(Server.site_foto_customer + foto).into(binding.imageProfile);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "code : " + anError);
                        Toast.makeText(MainActivity.this, "Gagal get Profil", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void ListMenu() {
        binding.swipe.setRefreshing(true);
        AndroidNetworking.post(Server.site + "showpesanan.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            binding.swipe.setRefreshing(false);
                            itemList.clear();
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
                                int stock = item.getInt("stock");
//                                if(stock == 0){
//                                    layoutTidakTersedia.setVisibility(View.VISIBLE);
//                                    cardItem.setVisibility(View.GONE);
//                                }else{
//                                    layoutTidakTersedia.setVisibility(View.GONE);
//                                    cardItem.setVisibility(View.VISIBLE);
//                                }
                            }
                            ItemAdapter adapter = new ItemAdapter(MainActivity.this, itemList);
                            binding.recyclerItem.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    ///menampilkan nama-nama kategori di menu home
    private void MenuPerkategori() {
        binding.swipe.setRefreshing(true);
        AndroidNetworking.post(Server.site + "namakategori.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            binding.swipe.setRefreshing(false);
                            kategoriList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject kategori = null;
                                kategori = response.getJSONObject(i);
                                kategoriList.add(new Kategori(
                                        kategori.getInt("id_kategori"),
                                        kategori.getString("foto_kategori")
                                ));
                            }
                            KategoriAdapter adapter = new KategoriAdapter(MainActivity.this, kategoriList);
                            binding.recyclerKategori.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror", "code : " + anError);
                        Toast.makeText(MainActivity.this, "Gagal get Data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}