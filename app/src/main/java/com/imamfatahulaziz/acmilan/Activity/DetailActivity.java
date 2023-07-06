package com.imamfatahulaziz.acmilan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.imamfatahulaziz.acmilan.R;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama, tvNoPunggung, tvAsalKlub, tvAsalNegara;
    private ImageView ivFoto;
    private String yNama, yNoPunggung, yFoto, yAsalKlub, yAsalNegara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama = findViewById(R.id.tv_nama);
        tvNoPunggung = findViewById(R.id.tv_nopunggung);
        tvAsalKlub = findViewById(R.id.tv_asalklub);
        tvAsalNegara = findViewById(R.id.tv_asalnegara);
        ivFoto = findViewById(R.id.iv_foto);

        Intent terima = getIntent();
        yNama = terima.getStringExtra("xNama");
        yNoPunggung = terima.getStringExtra("xNoPunggung");
        yFoto = terima.getStringExtra("xFoto");
        yAsalKlub = terima.getStringExtra("xAsalKlub");
        yAsalNegara = terima.getStringExtra("xAsalNegara");

        tvNama.setText(yNama);
        tvNoPunggung.setText(yNoPunggung);
        tvAsalKlub.setText(yAsalKlub);
        tvAsalNegara.setText(yAsalNegara);
        Glide
                .with(DetailActivity.this)
                .load(yFoto)
                .into(ivFoto);
    }
}