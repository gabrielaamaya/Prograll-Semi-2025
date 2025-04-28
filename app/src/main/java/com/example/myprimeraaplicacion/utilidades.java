package com.example.myprimeraaplicacion;


import java.util.Base64;

public class utilidades {
    static String url_consulta = "http://: 192.168.80.57:5984/josue/_design/_design%2Fjosue/_view/Gabriela";
    static String url_mto = "http://: 192.168.80.57:5984/josue";
    static String user = "josuealejandro";//Agregar usuario
    static String passwd = "123456789";//Agregar contraseña
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());
    public String generarUnicoId(){
        return java.util.UUID.randomUUID().toString();
    }
}