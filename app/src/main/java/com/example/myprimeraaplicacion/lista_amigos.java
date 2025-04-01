package com.example.myprimeraaplicacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class lista_amigos extends Activity {
    Bundle parametros = new Bundle();
    ListView ltsAmigos;
    Cursor cAmigos;
    DB db;
    final ArrayList<amigos> alAmigos = new ArrayList<amigos>();
    final ArrayList<amigos> alAmigosCopia = new ArrayList<amigos>();
    JSONArray jsonArray;
    JSONObject jsonObject;
    amigos misAmigos;
    FloatingActionButton fab;
    int posicion = 0;
    obtenerDatosServidor datosServidor;
    detectarInternet di;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_amigos);
        parametros.putString("accion", "nuevo");
        db = new DB(this);
        fab = findViewById(R.id.fabagregarAmigo);
        fab.setOnClickListener(view -> abriVentana());
        listarDatos();
        buscarAmigos();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.minenu, menu);
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            menu.setHeaderTitle(jsonArray.getJSONObject(posicion).getJSONObject("value").getString("nombre"));
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            if( item.getItemId()==R.id.mnxNuevo){
                abriVentana();
            }else if( item.getItemId()==R.id.mnxModificar){
                parametros.putString("accion", "modificar");
                parametros.putString("amigos", jsonArray.getJSONObject(posicion).getJSONObject("value").toString());
                abriVentana();
            } else if (item.getItemId()==R.id.mnxEliminar) {
                eliminarAmigo();
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarAmigo(){
        try{
            String nombre = jsonArray.getJSONObject(posicion).getJSONObject("value").getString("nombre");
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(nombre);
            confirmacion.setPositiveButton("Si", (dialog, which) -> {
                try {
                    di = new detectarInternet(this);
                    if(di.hayConexionInternet()){//online
                        JSONObject datosAmigos = new JSONObject();
                        String _id = jsonArray.getJSONObject(posicion).getJSONObject("value").getString("_id");
                        String _rev = jsonArray.getJSONObject(posicion).getJSONObject("value").getString("_rev");
                        String url = utilidades.url_consulta + "/" + _id + "?rev=" + _rev;
                        enviarDatosServidor objEnviarDatosServidor = new enviarDatosServidor(this);
                        String respuesta = objEnviarDatosServidor.execute(datosAmigos.toString(), "DELETE", url).get();
                        JSONObject respuestaJSON = new JSONObject(respuesta);
                        if (respuestaJSON.getBoolean("ok")){
                            obtenerDatosAmigos();
                            mostrarMsg("Registro eliminado con exito");
                        }else {
                            mostrarMsg("Error: " + respuesta);
                        }
                    }
                    String respuesta = db.administrar_amigos("eliminar", new String[]{jsonArray.getJSONObject(posicion).getJSONObject("value").getString("idAmigo")});
                    if(respuesta.equals("ok")) {
                        obtenerDatosAmigos();
                        mostrarMsg("Registro eliminado con exito");
                    }else{
                        mostrarMsg("Error: " + respuesta);
                    }
                }catch (Exception e){
                    mostrarMsg("Error: " + e.getMessage());
                }
            });
            confirmacion.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            confirmacion.create().show();
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    private void abriVentana(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(parametros);
        startActivity(intent);
    }
    private void listarDatos(){
        try{
            di = new detectarInternet(this);
            if(di.hayConexionInternet()){//online
                datosServidor = new obtenerDatosServidor();
                String respuesta = datosServidor.execute().get();
                jsonObject = new JSONObject(respuesta);
                jsonArray = jsonObject.getJSONArray("rows");
                mostrarDatosAmigos();
            }else{//offline
                obtenerDatosAmigos();
            }
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    private void obtenerDatosAmigos(){
        try{
            cAmigos = db.lista_amigos();
            if(cAmigos.moveToFirst()){
                jsonArray = new JSONArray();
                do{
                    jsonObject = new JSONObject();
                    jsonObject.put("idAmigo", cAmigos.getString(0));
                    jsonObject.put("nombre", cAmigos.getString(1));
                    jsonObject.put("direccion", cAmigos.getString(2));
                    jsonObject.put("telefono", cAmigos.getString(3));
                    jsonObject.put("email", cAmigos.getString(4));
                    jsonObject.put("dui", cAmigos.getString(5));
                    jsonObject.put("foto", cAmigos.getString(6));
                    jsonArray.put(jsonObject);
                }while(cAmigos.moveToNext());
                mostrarDatosAmigos();
            }else{
                mostrarMsg("No hay amigos registrados.");
                abriVentana();
            }
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    private void mostrarDatosAmigos(){
        try{
            if(jsonArray.length()>0){
                ltsAmigos = findViewById(R.id.listaamigos);
                alAmigos.clear();
                alAmigosCopia.clear();
                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i).getJSONObject("value");
                    misAmigos = new amigos(
                            jsonObject.getString("idAmigo"),
                            jsonObject.getString("nombre"),
                            jsonObject.getString("direccion"),
                            jsonObject.getString("telefono"),
                            jsonObject.getString("email"),
                            jsonObject.getString("dui"),
                            jsonObject.getString("urlFoto")
                    );
                    alAmigos.add(misAmigos);
                }
                alAmigosCopia.addAll(alAmigos);
                ltsAmigos.setAdapter(new AdaptadorAmigos(this, alAmigos));
                registerForContextMenu(ltsAmigos);
            }else{
                mostrarMsg("No hay amigos registrados.");
                abriVentana();
            }
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    private void buscarAmigos(){
        TextView tempVal = findViewById(R.id.txtBuscarAmigos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alAmigos.clear();
                String buscar = tempVal.getText().toString().trim().toLowerCase();
                if( buscar.length()<=0){
                    alAmigos.addAll(alAmigosCopia);
                }else{
                    for (amigos item: alAmigosCopia){
                        if(item.getNombre().toLowerCase().contains(buscar) ||
                                item.getDui().toLowerCase().contains(buscar) ||
                                item.getEmail().toLowerCase().contains(buscar)){
                            alAmigos.add(item);
                        }
                    }
                    ltsAmigos.setAdapter(new AdaptadorAmigos(getApplicationContext(), alAmigos));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
