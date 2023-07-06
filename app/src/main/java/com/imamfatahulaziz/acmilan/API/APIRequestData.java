package com.imamfatahulaziz.acmilan.API;

import com.imamfatahulaziz.acmilan.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {

    @GET("retrive.php")
    Call<ModelResponse> ardRetrive();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("no_punggung") String no_punggung,
            @Field("foto") String foto,
            @Field("asal_klub") String asal_klub,
            @Field("asal_negara") String asal_negara
    );

    @FormUrlEncoded
    @POST("update.php")
    Call <ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("no_punggung") String no_punggung,
            @Field("foto") String foto,
            @Field("asal_klub") String asal_klub,
            @Field("asal_negara") String asal_negara
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call <ModelResponse> ardDelete(
            @Field("id") String id
    );
}
