package com.example.fcampy.demomoviles.Rest;

import com.example.fcampy.demomoviles.Entities.Pedido;
import com.example.fcampy.demomoviles.Entities.Plato;
import com.example.fcampy.demomoviles.Entities.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by FCampy on 05/03/2017.
 */

public interface ApiService {
    @GET("/platos")
    Call<List<Plato>> getPlatos();
    @POST("/pedidos")
    Call<ResponseMessage> createPedido(@Body Pedido pedido);

}
