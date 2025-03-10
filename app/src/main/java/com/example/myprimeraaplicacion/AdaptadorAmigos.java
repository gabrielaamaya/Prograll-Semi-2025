package com.example.myprimeraaplicacion;

import static android.widget.Toast.makeText;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorAmigos extends BaseAdapter {
    Context context;
    ArrayList<amigos> alAmigos;
    amigos misAmigos;
    LayoutInflater inflater;

    public AdaptadorAmigos(Context context, ArrayList<amigos> alAmigos) {
        this.context = context;
        this.alAmigos = alAmigos;
    }

    @Override
    public int getCount() {
        return alAmigos.size();
    }

    @Override
    public Object getItem(int position) {
        return alAmigos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.fotos, parent, false);
        try {
            misAmigos = alAmigos.get(position);

            TextView tempVal = itemView.findViewById(R.id.lblNombreAdaptador);
            tempVal.setText(misAmigos.getNombre());

            tempVal = itemView.findViewById(R.id.lblTelefonoAdaptador);
            tempVal.setText(misAmigos.getTelefono());

            tempVal = itemView.findViewById(R.id.lblEmailAdaptador);
            tempVal.setText(misAmigos.getEmail());
        } catch (Exception e) {
            makeText(context, "", Toast.LENGTH_SHORT).show();makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}