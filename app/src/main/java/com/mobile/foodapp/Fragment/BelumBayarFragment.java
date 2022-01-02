package com.mobile.menu.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mobile.menu.Activity.PesananActivity;
import com.mobile.menu.Adapter.PesananAdapter;
import com.mobile.menu.Model.Pesanan;
import com.mobile.menu.R;
import com.mobile.menu.Server.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BelumBayarFragment extends Fragment {
    List<Pesanan>pesananList;
    RecyclerView recyclerOrderBB;

    public BelumBayarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pesananList = new ArrayList<>();
        row_pesanan();
    }

    private void row_pesanan() {
        AndroidNetworking.post(Server.site + "adm_order.php")
                .addBodyParameter("kode", String.valueOf(3))
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
                                recyclerOrderBB.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        View view = inflater.inflate(R.layout.fragment_belum_bayar, container, false);

//        TextView textView = view.findViewById(R.id.text_view);
//        String sTitle = getArguments().getString("title");
//        textView.setText(sTitle);

        recyclerOrderBB = (RecyclerView) view.findViewById(R.id.recyclerOrderBB);
        PesananAdapter adapter = new PesananAdapter(getContext(), pesananList);
        recyclerOrderBB.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerOrderBB.setAdapter(adapter);
        return view;
    }
}