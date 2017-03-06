package com.example.fcampy.demomoviles.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fcampy.demomoviles.Adapters.PlatoAdapter;
import com.example.fcampy.demomoviles.Entities.Plato;
import com.example.fcampy.demomoviles.Entities.RestClient;
import com.example.fcampy.demomoviles.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private ListView listView;
    private List<Plato> platos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Guía 1

        //platos = new ArrayList();
        //platos.add("Hamburguesa");
        //platos.add("pizza");
        //platos.add("papas");
        //platos.add("calentado");
        //platos.add("gaseosa");

        //GUIA 4
        //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,platos);
        //platos.add(new Plato(1, "Hamburguesa", 15000, "http://www.tastyburger.com/wp-content/themes/tastyBurger/images/home/img-large-burger.jpg"));
        //platos.add(new Plato(2, "Pizza", 10000, "http://cronicadexalapa.com/wp-content/uploads/2016/08/pizza.jpg"));
        //platos.add(new Plato(3, "Papas", 6000, "http://s2.dmcdn.net/IZlI9/1280x720-ts8.jpg"));
        //platos.add(new Plato(4, "Gaseosa", 3000, "http://www.superinter.com.co/wp-content/uploads/2014/08/763VgaseosaVcocaVcolaV600Vml.jpg"));


        //Transicion de la main a Pedidos Activity: GUIA 1
        listView=(ListView)findViewById(R.id.platos_listview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                Intent i =new Intent(MainActivity.this,PedidosActivity.class);
                i.putExtra("name",((Plato)platos.get(position)).getNombre());
                i.putExtra("id",((Plato)platos.get(position)).getId());
                startActivity(i);
            }
            });
        getPlatos();
        }

    public void getPlatos()
    {
        Call<List<Plato>> call = RestClient.getInstance().getApiService().getPlatos();
        call.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                platos = response.body();
                PlatoAdapter itemsAdapter = new PlatoAdapter(MainActivity.this, platos);
                listView.setAdapter(itemsAdapter);
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu,menu);
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    switch (item.getItemId())
        {
        case R.id.action_settings:
            AlertDialog dialog = createAlertDialog();
            dialog.show();
            return true;

                default:
            return super.onOptionsItemSelected(item);
        }
    }

    public AlertDialog createAlertDialog()
    {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Hello World")
    .setTitle("Dialogo de Alerta")
    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            //Do$something
        }
    });
    return builder.create();
    }
}




