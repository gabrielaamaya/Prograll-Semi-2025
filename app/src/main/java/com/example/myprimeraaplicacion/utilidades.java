package com.example.myprimeraaplicacion;

import java.util.Base64;

public class utilidades {
    static String url_consulta = "http://:   192.168.1.10:5984/agenda/_desing/agenda/_view/agenda";
    static String url_mto = "http://:   192.168.1.10:5984/agenda";
    static String user = "gabriela";
    static String passwd = "1234";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":"+passwd).getBytes());

    public String  generarUnicoId(){
        return java.util.UUID.randomUUID().toString();
    }
}
