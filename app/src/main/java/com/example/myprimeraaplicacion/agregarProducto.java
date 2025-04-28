package com.example.myprimeraaplicacion;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarProducto extends AppCompatActivity {

    EditText etNombre, etPrecio, etCosto;
    TextView tvGanancia;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.AgregarProducto);

        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etprecio);
        etCosto = findViewById(R.id.etCosto);
        tvGanancia = findViewById(R.id.tvGanancia);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarProducto();
            }
        });
    }

    private void guardarProducto() {
        String nombre = etNombre.getText().toString();
        String precioStr = etPrecio.getText().toString();
        String costoStr = etCosto.getText().toString();

        if (nombre.isEmpty() || precioStr.isEmpty() || costoStr.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);
        double costo = Double.parseDouble(costoStr);
        double ganancia = 0;

        if (costo > 0) {
            ganancia = ((precio - costo) / costo) * 100;
        }

        // Mostrar ganancia en el TextView
        tvGanancia.setText("Ganancia: " + String.format("%.2f", ganancia) + "%");

        // Guardar en la base de datos
        DB dbHelper = new DB(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("precio", precio);
        values.put("costo", costo);
        values.put("ganancia", ganancia);

        long id = db.insert("productos", null, values);
        db.close();

        if (id > 0) {
            Toast.makeText(this, "Producto guardado con éxito", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar actividad
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }
}