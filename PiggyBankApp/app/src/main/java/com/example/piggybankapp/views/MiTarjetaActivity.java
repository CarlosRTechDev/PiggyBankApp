package com.example.piggybankapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piggybankapp.controllers.CallbackService;
import com.example.piggybankapp.controllers.PiggyBankServiceController;
import com.example.piggybankapp.R;

public class MiTarjetaActivity extends AppCompatActivity implements CallbackService {
    private Button btnAceptar;
    private Button btnCancelar;
    private Switch switchBtn;
    private TextView tvNumTarjeta;
    private TextView tvNumCuenta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_tarjeta);

        Intent intent = getIntent();
        int idTarjeta = intent.getIntExtra("id", 0);
        String nombre = intent.getStringExtra("nombre");
        int numTarjeta = intent.getIntExtra("numTarjeta", 0);
        int numCuenta = intent.getIntExtra("numCuenta", 0);
        Double saldo = intent.getDoubleExtra("saldo", 0);
        String status = intent.getStringExtra("status");
        String tipoTarjeta = intent.getStringExtra("tipoTarjeta");

        btnAceptar = findViewById(R.id.btnAceptar);
        btnCancelar = findViewById(R.id.btnCancelar);
        switchBtn = findViewById(R.id.switchBtn);
        tvNumTarjeta = findViewById(R.id.tvNumTarjeta);
        tvNumCuenta = findViewById(R.id.tvNumCuenta);

        //set de intents
        tvNumTarjeta.setText(String.valueOf(numTarjeta));
        tvNumCuenta.setText(String.valueOf(numCuenta));

        //set de intents en switch
        if (status.equals("Inactiva")) {
            switchBtn.setChecked(true);
        } else if (status.equals("Activa")) {
            switchBtn.setChecked(false);
        }

        setToolbar();

        /*switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MiTarjetaActivity.this, "Switch activado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MiTarjetaActivity.this, "Switch desactivado", Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean switchStatusActual = switchBtn.isChecked();

                //Para tarjetas activas
                if (status.equals("Activa") && switchStatusActual) {
                    //Toast.makeText(getApplicationContext(), "ejecutar servicio con switch ON", Toast.LENGTH_SHORT).show();
                    serviceBloquearDesbloquearTarjeta(idTarjeta, nombre, numTarjeta, numCuenta, saldo, tipoTarjeta);
                } else if (status.equals("Activa") && !switchStatusActual) {
                    Toast.makeText(getApplicationContext(), "No puedes hacer el bloqueo con el mismo status. Selecciona ON.", Toast.LENGTH_SHORT).show();
                }

                //Para tarjetas inactivas
                if (status.equals("Inactiva") && !switchStatusActual) {
                    //Toast.makeText(getApplicationContext(), "ejecutar servicio con switch OFF", Toast.LENGTH_SHORT).show();
                    serviceBloquearDesbloquearTarjeta(idTarjeta, nombre, numTarjeta, numCuenta, saldo, tipoTarjeta);
                } else if (status.equals("Inactiva") && switchStatusActual) {
                    Toast.makeText(getApplicationContext(), "No puedes hacer el desbloqueo con el mismo status. Selecciona OFF.", Toast.LENGTH_SHORT).show();
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


    private void serviceBloquearDesbloquearTarjeta(int idTarjeta, String nombre, int numTarjeta, int numCuenta,
                                                   Double saldo, String tipoTarjeta) {

        String estatus;
        if (!switchBtn.isChecked()) {
            estatus = "Activa";
        } else {
            estatus = "Inactiva";
        }

        new PiggyBankServiceController(MiTarjetaActivity.this, this)
                .bloquearDesbloquearTarjeta(idTarjeta, nombre, numTarjeta, numCuenta, saldo, estatus, tipoTarjeta);
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

    @Override
    public void onFinish(String message) {
        Intent intent = new Intent(MiTarjetaActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}