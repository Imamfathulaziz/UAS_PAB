package com.imamfatahulaziz.acmilan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.imamfatahulaziz.acmilan.API.APIRequestData;
import com.imamfatahulaziz.acmilan.API.RetroServer;
import com.imamfatahulaziz.acmilan.Adapter.AdapterACMilan;
import com.imamfatahulaziz.acmilan.Model.ModelACMilan;
import com.imamfatahulaziz.acmilan.Model.ModelResponse;
import com.imamfatahulaziz.acmilan.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvAcmilan;
    private FloatingActionButton fabAdd;
    private ProgressBar pbacmilan;
    private RecyclerView.Adapter adacmilan;
    private RecyclerView.LayoutManager lmacmilan;
    private List<ModelACMilan> listACMilan = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvAcmilan = findViewById(R.id.rv_acmilan);
        fabAdd = findViewById(R.id.fab_add);
        pbacmilan = findViewById(R.id.pb_acmilan);

        lmacmilan = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvAcmilan.setLayoutManager(lmacmilan);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retriveACMilan();
    }

    public void retriveACMilan(){
        pbacmilan.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrive();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listACMilan = response.body().getData();
                adacmilan = new AdapterACMilan(MainActivity.this, listACMilan);
                rvAcmilan.setAdapter(adacmilan);
                adacmilan.notifyDataSetChanged();
                pbacmilan.setVisibility(View.GONE);

                fabAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, TambahActivity.class));
                    }
                });
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbacmilan.setVisibility(View.GONE);
            }

        });
    }
}