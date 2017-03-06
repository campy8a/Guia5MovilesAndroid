package com.example.fcampy.demomoviles.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fcampy.demomoviles.Entities.Pedido;
import com.example.fcampy.demomoviles.Entities.ResponseMessage;
import com.example.fcampy.demomoviles.Entities.RestClient;
import com.example.fcampy.demomoviles.R;
import com.squareup.picasso.Downloader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidosActivity extends AppCompatActivity {
    private String platoName;
    CoordinatorLayout coordinatorLayout;
    private int platoId;
    public final static int PAYMENT = 1111;
    private static final String TAG = "Pedidos";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Guía 1
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recibe el intent del main activity: Guía 1
        if (getIntent() != null && getIntent().getExtras().getString("name") != null) {
            platoName = getIntent().getExtras().getString("name");
            platoId = getIntent().getExtras().getInt("id");
        }
        getSupportActionBar().setTitle(platoName);

        Button btnComment = (Button) findViewById(R.id.pedir_btn);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w)
            {
                realizarPedido();
            }
        });
    }

    public void realizarPedido() {
        String cliente = ((TextView) findViewById(R.id.nombre_cliente)).getText().toString();
        String lugar = ((TextView) findViewById(R.id.lugar_Orden)).getText().toString();
        Pedido pedido = new Pedido(cliente, lugar, platoId);

        Call<ResponseMessage> call =
                RestClient.getInstance().getApiService().createPedido(pedido);
        call.enqueue(new Callback <ResponseMessage>() {
           @Override
            public void onResponse(Call<ResponseMessage> call,
                                   Response<ResponseMessage> response)
           {
                ResponseMessage res = response.body();
               Snackbar snackbar=Snackbar.make(coordinatorLayout,res.getMsg(),
                   Snackbar.LENGTH_LONG);
               snackbar.show();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
            }
        });
    }

    private void solicitarMetodo()
    {
        Intent i = new Intent(this, MetodoActivity.class);
        startActivityForResult(i,PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {

        Log.d(TAG,"Llega resultado de otra actividad");
        if (requestCode==PAYMENT){
            if(resultCode==111){

                btnPayment.setText(data.getStringExtra("method"));
            }}
    }
}
