package com.imamfatahulaziz.acmilan.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelACMilan> data;

    public String getKode(){return kode;}
    public String getPesan(){return pesan;}

    public List<ModelACMilan> getData(){
        return data;
    }
}
