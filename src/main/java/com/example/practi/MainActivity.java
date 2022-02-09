package com.example.practi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RequestQueue queue;
    String url="https://apirestfactura.herokuapp.com/api/Producto";

    List<String> datos;
    ListView listDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //inicializar

        queue = Volley.newRequestQueue(this);

        GetApiData();
        listDatos=(ListView)findViewById(R.id.listDatos);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        listDatos.setAdapter(adapter);

    }

    private void GetApiData(){

        datos =new ArrayList<String>();
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject obj=response.getJSONObject(i);
                            Grupo g=new Grupo();
                            g.setProducto_id(Integer.parseInt(obj.get("producto_Id").toString()));
                            g.setProducto(obj.get("producto").toString());
                            g.setPrecio(Double.parseDouble(obj.get("precio").toString()));
                            g.setStock(Integer.parseInt(obj.get("stock").toString()));
                            g.setEstado(Integer.parseInt(obj.get("estado").toString()));

                            datos.add(g.getProducto());

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int x=0;
            }
        });

        queue.add(request);
    }
}