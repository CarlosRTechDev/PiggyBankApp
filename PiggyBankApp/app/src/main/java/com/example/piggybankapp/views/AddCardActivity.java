package com.example.piggybankapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piggybankapp.controllers.CallbackService;
import com.example.piggybankapp.controllers.PiggyBankServiceController;
import com.example.piggybankapp.R;

public class AddCardActivity extends AppCompatActivity implements CallbackService {


    private Button btnAceptar;
    private Button btnCancelar;
    private EditText edTxtNombre;
    private TextView errorTV;


    String tipoTarjetaSeleccionada;
    String editTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        btnAceptar = findViewById(R.id.btnAceptar);
        btnCancelar = findViewById(R.id.btnCancelar);
        edTxtNombre = findViewById(R.id.edTxtNombre);
        errorTV = findViewById(R.id.errorTV);

        setToolbar();

        /* Spinner */
        Spinner spinner = findViewById(R.id.spinner); // Reemplaza "spinner" con el ID de tu spinner en el diseño de actividad

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoTarjetaSeleccionada = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), "Elemento seleccionado: " + tipoTarjetaSeleccionada, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aquí puedes manejar el caso cuando no se ha seleccionado ningún elemento
            }
        });
        /* Spinner */

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTarjeta = edTxtNombre.getText().toString().trim();
                if (editTarjeta.isEmpty()) {
                    errorTV.setText("El nombre no debe estar vacío");
                    errorTV.setVisibility(View.VISIBLE);
                } else {
                    errorTV.setVisibility(View.GONE);
                    // Realizar acciones adicionales si el campo no está vacío
                    //Toast.makeText(getApplicationContext(), "Aceptar" + text, Toast.LENGTH_SHORT).show();
                    /*Toast.makeText(getApplicationContext(), "CHA: Consumir servicio aqui: " +
                            tipoTarjetaSeleccionada + editTarjeta, Toast.LENGTH_SHORT).show();*/

                    serviceCrearTarjeta(editTarjeta, tipoTarjetaSeleccionada);

                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Cancelar", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void setToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }


    public void serviceCrearTarjeta(String nombreTitular, String tipoTarjeta){
        new PiggyBankServiceController(AddCardActivity.this, this)
                .crearTarjeta(nombreTitular, tipoTarjeta);
    }


    @Override
    public void onFinish(String message) {
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}