package com.example.myprimeraaplicacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "productos";
    private static final int DATABASE_VERSION = 5; // Incrementamos la versión
    private static final String SQLdb = "CREATE TABLE productos (idProducto TEXT, codigo TEXT, descripcion TEXT, marca TEXT, presentacion TEXT, precio TEXT, costo TEXT, gananciaPorcentaje TEXT, urlFoto TEXT, urlFoto1 TEXT, urlFoto2 TEXT)";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLdb);
        // Crear tabla actualizados
        db.execSQL("CREATE TABLE actualizado (id TEXT, actualizado TEXT)");
        // Insertar datos iniciales
        db.execSQL("INSERT INTO actualizado (id, actualizado) VALUES ('0','0')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            // Migración para versiones anteriores a la 4
            db.execSQL("ALTER TABLE productos ADD COLUMN urlFoto1 TEXT");
            db.execSQL("ALTER TABLE productos ADD COLUMN urlFoto2 TEXT");
            db.execSQL("CREATE TABLE actualizado (id TEXT, actualizado TEXT)");
            db.execSQL("INSERT INTO actualizado (id, actualizado) VALUES ('0','0')");
        }
        if (oldVersion < 5) {
            // Migración para la versión 5 (agregamos costo y gananciaPorcentaje)
            db.execSQL("ALTER TABLE productos ADD COLUMN costo TEXT DEFAULT '0'");
            db.execSQL("ALTER TABLE productos ADD COLUMN gananciaPorcentaje TEXT DEFAULT '0'");
        }
    }

    public String administrar_productos(String accion, String[] datos) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String mensaje = "ok", sql = " ";

            switch (accion) {
                case "nuevo":
                    sql = "INSERT INTO productos (idProducto, codigo, descripcion, marca, presentacion, precio, costo, gananciaPorcentaje, urlFoto, urlFoto1, urlFoto2) " +
                            "VALUES ('"+ datos[0] +"','"+ datos[1] +"', '" + datos[2] + "', '" + datos[3] + "', '" + datos[4] + "', '" + datos[5] + "', '" + datos[6] + "', '" +
                            calcularGanancia(datos[5], datos[6]) + "', '" + datos[7] + "', '" + datos[8] + "', '" + datos[9] + "')";
                    break;
                case "modificar":
                    sql = "UPDATE productos SET " +
                            "codigo = '" + datos[1] + "', " +
                            "descripcion = '" + datos[2] + "', " +
                            "marca = '" + datos[3] + "', " +
                            "presentacion = '" + datos[4] + "', " +
                            "precio = '" + datos[5] + "', " +
                            "costo = '" + datos[6] + "', " +
                            "gananciaPorcentaje = '" + calcularGanancia(datos[5], datos[6]) + "', " +
                            "urlFoto = '" + datos[7] + "', " +
                            "urlFoto1 = '" + datos[8] + "', " +
                            "urlFoto2 = '" + datos[9] + "' " +
                            "WHERE idProducto = '" + datos[0] + "'";
                    break;
                case "eliminar":
                    sql = "DELETE FROM productos WHERE idProducto = '" + datos[0] + "'";
                    break;
                case "eliminarTodo":
                    sql = "DELETE FROM productos";
                    break;
            }

            db.execSQL(sql);
            db.close();
            return mensaje;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Método para calcular el porcentaje de ganancia
    private String calcularGanancia(String precioStr, String costoStr) {
        try {
            double precio = Double.parseDouble(precioStr);
            double costo = Double.parseDouble(costoStr);

            if (costo == 0) return "0";

            double ganancia = ((precio - costo) / costo) * 100;
            return String.format("%.2f", ganancia);
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public String administrarActualizados(String mod, String datos, String idProducto) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String mensaje = "ok", sql = "";

            switch (mod) {
                case "modificar":
                    sql = "UPDATE actualizado SET actualizado = '"+datos+"' WHERE id = '0'";
                    break;
                case "nuevo":
                    sql = "INSERT INTO actualizado (id, actualizado) VALUES ('"+idProducto+"','"+datos+"')";
                    break;
                case "eliminar":
                    sql = "DELETE FROM actualizado WHERE id != '0'";
                    break;
            }

            db.execSQL(sql);
            db.close();
            return mensaje;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Cursor lista_productosActializados() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM actualizado", null);
    }

    public Cursor lista_productos() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM productos", null);
    }
}