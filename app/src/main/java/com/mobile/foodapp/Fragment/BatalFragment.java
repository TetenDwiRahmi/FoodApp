package com.mobile.menu.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mobile.menu.Adapter.PesananAdapter;
import com.mobile.menu.Model.Pesanan;
import com.mobile.menu.R;
import com.mobile.menu.Server.Server;
import com.mobile.menu.databinding.ActivityPesananBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BatalFragment extends Fragment {
    List<Pesanan> pesananList;
    RecyclerView recyclerBatal;
    ImageView img_nodata;
    TextView txtNodata;

    public BatalFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pesananList = new ArrayList<>();
        row_pesanan();
    }

    private void row_pesanan() {
        AndroidNetworking.post(Server.site + "adm_order.php")
                .addBodyParameter("kode", String.valueOf(1))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() >= 1) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject d = response.getJSONObject(i);
                                    pesananList.add(new Pesanan(
                                            d.getString("id_order"),
                                            d.getInt("id_customer"),
                                            d.getString("tgl_order"),
                                            d.getString("status_order"),
                                            d.getString("namapengirim"),
                                            d.getInt("total_bayar"),
                                            d.getInt("total_item")
                                    ));

                                    Log.d("tag", "Total Item : " + d.getInt("total_item"));
                                }
                                PesananAdapter adapter = new PesananAdapter(getContext(), pesananList);
                                recyclerBatal.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            img_nodata.setVisibility(View.VISIBLE);
                            txtNodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("tag", "error pesanan : " + anError);
                    }

                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_batal, container, false);

        img_nodata = view.findViewById(R.id.img_nodata);
        txtNodata = view.findViewById(R.id.txtNodata);

        recyclerBatal = (RecyclerView) view.findViewById(R.id.recyclerBatal);
        PesananAdapter adapter = new PesananAdapter(getContext(), pesananList);
        recyclerBatal.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerBatal.setAdapter(adapter);
        return view;
    }
}