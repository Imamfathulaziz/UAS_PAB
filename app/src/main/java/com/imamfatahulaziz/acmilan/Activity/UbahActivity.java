package com.imamfatahulaziz.acmilan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imamfatahulaziz.acmilan.API.APIRequestData;
import com.imamfatahulaziz.acmilan.API.RetroServer;
import com.imamfatahulaziz.acmilan.Model.ModelResponse;
import com.imamfatahulaziz.acmilan.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private EditText etNama, etNoPunggung, etFoto, etAsalKlub, etAsalNegara;
    private String Nama, NoPunggung, Foto, AsalKlub, AsalNegara;
    private String yId, yNama, yNoPunggung, yFoto, yAsalKlub, yAsalNegara;
    private Button btnUbah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        etNama = findViewById(R.id.et_nama);
        etNoPunggung = findViewById(R.id.et_nopunggung);
        etFoto = findViewById(R.id.et_foto);
        etAsalKlub = findViewById(R.id.et_asalklub);
        etAsalNegara = findViewById(R.id.et_asalnegara);
        btnUbah = findViewById(R.id.btn_ubah);

        Intent terima = getIntent();
        yId = terima.getStringExtra("xId");
        yNama = terima.getStringExtra("xNama");
        yNoPunggung = terima.getStringExtra("xNoPunggung");
        yFoto = terima.getStringExtra("xFoto");
        yAsalKlub = terima.getStringExtra("xAsalKlub");
        yAsalNegara = terima.getStringExtra("xAsalNegara");

        etNama.setText(yNama);
        etNoPunggung.setText(yNoPunggung);
        etFoto.setText(yFoto);
        etAsalKlub.setText(yAsalKlub);
        etAsalNegara.setText(yAsalNegara);


        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                NoPunggung = etNoPunggung.getText().toString();
                Foto = etFoto.getText().toString();
                AsalKlub = etAsalKlub.getText().toString();
                AsalNegara = etAsalNegara.getText().toString();

                if (Nama.trim().isEmpty()){
                    etNama.setError("Nama pemain harus di isi");
                }else if (NoPunggung.trim().isEmpty()){
                    etNoPunggung.setError("No punggung pemain harus di isi");
                } else if (AsalNegara.trim().isEmpty()) {
                    etAsalNegara.setError("Asal negara pemain harus di isi");
                } else if (Foto.trim().isEmpty()) {
                    etFoto.setError("Link foto pemain harus di isi");
                } else if (AsalKlub.trim().isEmpty()) {
                    etAsalKlub.setError("Asal klub pemain harus di isi");
                } else {
                    ubahPemain();
                }

            }
        });
    }

    private void ubahPemain(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, Nama, NoPunggung, Foto, AsalKlub, AsalNegara);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "kode : " + kode +" Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Error: Gagal merubah data! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}