package com.example.piggybankapp.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piggybankapp.controllers.CallbackService;
import com.example.piggybankapp.models.Cuenta;
import com.example.piggybankapp.models.Movimientos;
import com.example.piggybankapp.controllers.PiggyBankServiceController;
import com.example.piggybankapp.R;
import com.example.piggybankapp.models.Saldos;
import com.example.piggybankapp.models.Tarjetas;
import com.example.piggybankapp.controllers.Util;
import com.example.piggybankapp.views.adapter.CommonAdapter;

import java.text.ParseException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CallbackService, PiggyBankServiceController.CallbackCuenta,
        PiggyBankServiceController.CallbackSaldos, PiggyBankServiceController.CallbackTarjetas,
        PiggyBankServiceController.CallbackMovimientos {

    private TextView tvNombre;
    private TextView tvSesion;
    private TextView tvSaldoGeneral;
    private TextView tvTotalIngresos;
    private TextView tvTotalGastos;
    private TextView tvAgregarTarjeta;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView, rvMovRecientes;
    private CommonAdapter commonAdapter;

    //Expand
    private TextView tvExpandMovRecientes;
    public RelativeLayout expandableLayout;
    public LinearLayout linearLayoutt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNombre = findViewById(R.id.tvNombre);
        tvSesion = findViewById(R.id.tvSesion);

        tvSaldoGeneral = findViewById(R.id.tvSaldoGeneral);
        tvTotalIngresos = findViewById(R.id.tvTotalIngresos);
        tvTotalGastos = findViewById(R.id.tvTotalGastos);

        tvAgregarTarjeta = findViewById(R.id.tvAgregarTarjeta);

        //Configura Recycler Tarjetas
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        //

        //Configura Recycler Movimientos
        rvMovRecientes = findViewById(R.id.rvMovRecientes);
        tvExpandMovRecientes = findViewById(R.id.tvExpandMovRecientes);
        expandableLayout = findViewById(R.id.expandableLayout);
        linearLayoutt = findViewById(R.id.linearLayoutt);

        rvMovRecientes.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(MainActivity.this);
        //rvMovRecientes.setLayoutManager(layoutManager);
        layoutManager = new GridLayoutManager(MainActivity.this, 3, GridLayoutManager.HORIZONTAL, false);
        rvMovRecientes.setLayoutManager(layoutManager);
        //


        /*tvExpandMovRecientes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(expandableLayout.getVisibility() == View.VISIBLE){
                    androidx.transition.TransitionManager.beginDelayedTransition(linearLayoutt, new androidx.transition.AutoTransition());
                    expandableLayout.setVisibility(View.GONE);
                }else{
                    TransitionManager.beginDelayedTransition(linearLayoutt, new AutoTransition());
                    expandableLayout.setVisibility(View.VISIBLE);
                }
            }
        });*/

        tvExpandMovRecientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvExpandMovRecientes.getText().equals("Ver Más Movimientos")) {
                    //androidx.transition.TransitionManager.beginDelayedTransition(linearLayoutt, new androidx.transition.AutoTransition());
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    rvMovRecientes.setLayoutManager(layoutManager);
                    tvExpandMovRecientes.setText("Ver Menos Movimientos");
                } else {
                    //TransitionManager.beginDelayedTransition(linearLayoutt, new AutoTransition());
                    layoutManager = new GridLayoutManager(MainActivity.this, 3, GridLayoutManager.HORIZONTAL, false);
                    rvMovRecientes.setLayoutManager(layoutManager);
                    tvExpandMovRecientes.setText("Ver Más Movimientos");
                }
            }
        });

        tvAgregarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Agregar tarjeta", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivity(intent);
            }
        });


        new PiggyBankServiceController(MainActivity.this, this)
                .getCuenta(this);

        new PiggyBankServiceController(MainActivity.this, this)
                .getSaldos(this);

        new PiggyBankServiceController(MainActivity.this, this)
                .getTarjetas(this);

        new PiggyBankServiceController(MainActivity.this, this)
                .getMovimientos(this);
    }

    @Override
    public void onFinish(String message) {
        Toast.makeText(MainActivity.this, "OK: " +message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinish(Cuenta cuenta) {
        //Toast.makeText(MainActivity.this, "OK2: " +cuenta.getUltimaSesion(), Toast.LENGTH_LONG).show();
        tvNombre.setText("Bienvenido, " + cuenta.getNombre());

        String fechaString = cuenta.getUltimaSesion();
        String formato = "yyyy-MM-dd HH:mm:ss";

        try {
            //Convertir String a Calendar
            String calendar = Util.convertirStringACalendar(fechaString, formato);

            //Convertir SDF a formato 12 horas
            Date date = Util.convertirStringASimpleDF(fechaString, formato);
            String tiempo12Horas = Util.convertirSDFA12Horas(date);

            tvSesion.setText("Último acceso: " + calendar + ", " + tiempo12Horas);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(Saldos saldos) {
        //Toast.makeText(MainActivity.this, "Saldos: " +saldos.getSaldoGeneral(), Toast.LENGTH_LONG).show();
        tvSaldoGeneral.setText("$" + Util.formatNumber(saldos.getSaldoGeneral()));
        tvTotalIngresos.setText("$" + Util.formatNumber(saldos.getIngresos()));
        tvTotalGastos.setText("$" + Util.formatNumber(saldos.getGastos()));
    }

    @Override
    public void onFinish(Tarjetas[] tarjetas) {
        commonAdapter = new CommonAdapter(R.layout.item_tarjeta, tarjetas.length) {
            @Override
            public void bindView(RecyclerView.ViewHolder vh, int position) {
                TextView tvStatusTarjeta = vh.itemView.findViewById(R.id.tvStatusTarjeta);
                TextView tvNumTarjeta = vh.itemView.findViewById(R.id.tvNumTarjeta);
                TextView tvNombreTarjeta = vh.itemView.findViewById(R.id.tvNombreTarjeta);
                TextView tvTipo = vh.itemView.findViewById(R.id.tvTipo);
                TextView tvSaldoTarjeta = vh.itemView.findViewById(R.id.tvSaldoTarjeta);
                ImageView imgTarjeta = vh.itemView.findViewById(R.id.imgTarjeta);
                ConstraintLayout clTarjetas = vh.itemView.findViewById(R.id.clTarjetas);

                tvStatusTarjeta.setText(tarjetas[position].getEstado());
                tvNumTarjeta.setText(String.valueOf(tarjetas[position].getTarjeta()));
                tvNombreTarjeta.setText(tarjetas[position].getNombre());
                tvTipo.setText(tarjetas[position].getTipo());
                tvSaldoTarjeta.setText("$" + Util.formatNumber(tarjetas[position].getSaldo()));

                if(tarjetas[position].getEstado().equals("Inactiva")) {
                    tvStatusTarjeta.setTextColor(Color.LTGRAY);
                    tvNumTarjeta.setTextColor(Color.LTGRAY);
                    tvNombreTarjeta.setTextColor(Color.LTGRAY);
                    tvTipo.setTextColor(Color.LTGRAY);
                    tvSaldoTarjeta.setTextColor(Color.LTGRAY);
                    imgTarjeta.setImageResource(R.drawable.ic_inactive_card);
                }
                clTarjetas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, "Tarjeta: " + position, Toast.LENGTH_LONG).show();
                        int idTarjeta = tarjetas[position].getId();
                        String nombre = tvNombreTarjeta.getText().toString();
                        int numTarjeta = Integer.parseInt(tvNumTarjeta.getText().toString());
                        int numCuenta = tarjetas[position].getCuenta();
                        Double saldo = tarjetas[position].getSaldo();
                        String status = tvStatusTarjeta.getText().toString();
                        String tipoTarjeta = tvTipo.getText().toString();

                        /*Toast.makeText(MainActivity.this, "id: " + idTarjeta + " " + nombre + " "
                                +"numTarjeta: " + numTarjeta + " " +"numCuenta: " + numCuenta + " "
                                +"saldo: " + saldo + " " +"status: " + status + " "
                                +"tipo: " + tipoTarjeta, Toast.LENGTH_LONG).show();*/

                        Intent intent = new Intent(MainActivity.this, MiTarjetaActivity.class);
                        intent.putExtra("id", idTarjeta);
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("numTarjeta", numTarjeta);
                        intent.putExtra("numCuenta", numCuenta);
                        intent.putExtra("saldo", saldo);
                        intent.putExtra("status", status);
                        intent.putExtra("tipoTarjeta", tipoTarjeta);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void onFinish(Movimientos[] movimientos) {
        System.out.println("respMovimientos: " +movimientos[0].getDescripcion());

        commonAdapter = new CommonAdapter(R.layout.item_movimientos, movimientos.length) {
            @Override
            public void bindView(RecyclerView.ViewHolder vh, int position) {
                TextView tvDescripcionMov = vh.itemView.findViewById(R.id.tvDescripcionMov);
                TextView tvMonto = vh.itemView.findViewById(R.id.tvMonto);
                TextView tvFecha = vh.itemView.findViewById(R.id.tvFecha);

                tvDescripcionMov.setText(movimientos[position].getDescripcion());
                tvFecha.setText(movimientos[position].getFecha());

                if (movimientos[position].getTipo().equals("Abono")) {
                    tvMonto.setText("+$" + Util.formatNumber(movimientos[position].getMonto()));
                    tvMonto.setTextColor(getResources().getColor(R.color.turquoise));
                } else {
                    tvMonto.setText("-$" + Util.formatNumber(movimientos[position].getMonto()));
                    //tvMonto.setTextColor(Color.RED);
                    tvMonto.setTextColor(getResources().getColor(R.color.dark_red));
                }

            }
        };
        rvMovRecientes.setAdapter(commonAdapter);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(MainActivity.this, "Error: " +errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar app");
        builder.setMessage("¿Quieres salir de la app?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}