package com.example.myprimeraaplicacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class chatsArrayAdapter extends ArrayAdapter {
    private Context context;
    private List<ChatMessage> ChatMessageList = new ArrayList<>();
    private TextView chatText;

    public chatsArrayAdapter(@NonNull Context context, int resource){
        super(context, resource);
        this.context = context;
    }
    public void add(ChatMessage object){
        ChatMessageList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return ChatMessageList.size();
    }
    public ChatMessage getItem(int index){
        return ChatMessageList.get(index);
    }
    public View getView(int posicion, View view, ViewGroup viewGroup){
        View fila = view;
        try{
            ChatMessage objChatMessage = getItem(posicion);

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if( objChatMessage.posicion ){
                fila = layoutInflater.inflate(R.layout.msgizquierda, viewGroup, false);
                chatText = fila.findViewById(R.id.lblMsgIzquierda);
            }else{
                fila = layoutInflater.inflate(R.layout.mgsderecha, viewGroup, false);
                chatText = fila.findViewById(R.id.lblMsgDerecha);
            }
            chatText.setText(objChatMessage.mensaje);
        }catch (Exception e){
            Toast.makeText(context, "Error al visualizar el mensaje: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return fila;
    }
}


