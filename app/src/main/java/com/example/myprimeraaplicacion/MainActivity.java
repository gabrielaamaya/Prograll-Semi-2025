package com.example.myprimeraaplicacion;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myprimeraaplicacion.R;

import org.w3c.dom.Text;



public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tempVal;
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);
        btn = findViewById(R.id.btnGuardarAmigo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarAmigo();
            }
        });
    }
    private void guardarAmigo(){
        tempVal = findViewById(R.id.txtNombre);
        String nombre = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtDireccion);
        String direccion = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtTelefono);
        String telefono = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtEmail);
        String Email = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtDui);
        String dui = tempVal.getText().toString();

        String[] datos = {"", nombre, direccion, telefono, Email, dui, ""};
        db.administrar_amigos("agregar", datos);
        Toast.makeText(getApplicationContext(),"Registro guardado con exito. ", Toast.LENGTH_LONG).show();
    }
}