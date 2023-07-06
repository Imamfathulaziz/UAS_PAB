package com.imamfatahulaziz.acmilan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imamfatahulaziz.acmilan.API.APIRequestData;
import com.imamfatahulaziz.acmilan.API.RetroServer;
import com.imamfatahulaziz.acmilan.Activity.DetailActivity;
import com.imamfatahulaziz.acmilan.Activity.MainActivity;
import com.imamfatahulaziz.acmilan.Activity.UbahActivity;
import com.imamfatahulaziz.acmilan.Model.ModelACMilan;
import com.imamfatahulaziz.acmilan.Model.ModelResponse;
import com.imamfatahulaziz.acmilan.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterACMilan extends RecyclerView.Adapter<AdapterACMilan.VHACMilan>{
    private Context ctx;
    private List<ModelACMilan> listTampilan;

    public AdapterACMilan(Context ctx, List<ModelACMilan> listTampilan) {
        this.ctx = ctx;
        this.listTampilan = listTampilan;
    }

    @NonNull
    @Override
    public VHACMilan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_tampilan, parent, false);
        return new VHACMilan(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHACMilan holder, int position) {
        ModelACMilan MACM = listTampilan.get(position);
        holder.tvId.setText(MACM.getId());
        holder.tvNama.setText(MACM.getNama());
        holder.tvNoPunggung.setText(MACM.getNoPunggung());
        holder.tvFoto.setText(MACM.getFoto());
        holder.tvAsalKlub.setText(MACM.getAsalKlub());
        holder.tvAsalNegara.setText(MACM.getAsalNegara());
        Glide
                .with(ctx)
                .load(MACM.getFoto())
                .into(holder.ivFoto);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xNama, xNoPunggung, xFoto, xAsalKlub, xAsalNegara;
                xNama = MACM.getNama();
                xNoPunggung = MACM.getNoPunggung();
                xFoto = MACM.getFoto();
                xAsalKlub = MACM.getAsalKlub();
                xAsalNegara = MACM.getAsalNegara();

                Intent kirim = new Intent(ctx, DetailActivity.class);
                kirim.putExtra("xNama",xNama);
                kirim.putExtra("xNoPunggung",xNoPunggung);
                kirim.putExtra("xFoto",xFoto);
                kirim.putExtra("xAsalKlub",xAsalKlub);
                kirim.putExtra("xAsalNegara",xAsalNegara);
                ctx.startActivity(kirim);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listTampilan.size();
    }

    public class VHACMilan extends RecyclerView.ViewHolder{
        private TextView tvId, tvNama, tvNoPunggung, tvFoto, tvAsalKlub, tvAsalNegara;
        private Button btnHapus, btnUbah, btnDetail;

        private ImageView ivFoto;
        public VHACMilan(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNoPunggung = itemView.findViewById(R.id.tv_nopunggung);
            tvFoto = itemView.findViewById(R.id.tv_foto);
            tvAsalKlub = itemView.findViewById(R.id.tv_asalklub);
            tvAsalNegara = itemView.findViewById(R.id.tv_asalnegara);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            btnUbah = itemView.findViewById(R.id.btn_ubah);
            btnDetail = itemView.findViewById(R.id.btn_detail);


            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteBaju(tvId.getText().toString());

                }
            });


            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, UbahActivity.class);
                    kirim.putExtra("xId", tvId.getText().toString());
                    kirim.putExtra("xNama", tvNama.getText().toString());
                    kirim.putExtra("xNoPunggung", tvNoPunggung.getText().toString());
                    kirim.putExtra("xFoto", tvFoto.getText().toString());
                    kirim.putExtra("xAsalKlub", tvAsalKlub.getText().toString());
                    kirim.putExtra("xAsalNegara", tvAsalNegara.getText().toString());
                    ctx.startActivity(kirim);
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


        void deleteBaju(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode:"+kode+"Pesan : "+ pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retriveACMilan();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
