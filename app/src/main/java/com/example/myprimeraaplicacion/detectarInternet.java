package com.example.myprimeraaplicacion;

import android.content.Context;
import android.icu.text.IDNA;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class detectarInternet {
    private Context  Contexto;
    public detectarInternet(Context contexto){
        this.Contexto = Contexto;
    }
    public boolean hayConexionInternet(){
        ConnectivityManager connectivityManager=
                (ConnectivityManager) Contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager==null) return false;

        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if (info==null) return false;

        for (int i=0; i<info.length; i++){
            if (info[i].getState()==NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;
    }
}
