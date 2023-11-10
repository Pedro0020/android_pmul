package com.example.tramosdelaluz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.icu.number.LocalizedNumberFormatter;
import android.icu.util.BuddhistCalendar;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText time;
    private ConstraintLayout csl;
    private Button button;
    public static final int TRAMO_VERDE = 6;
    public static final int TRAMO_ROJO = 16;
    public static final int TRAMO_AMARILLO = 20;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = (EditText) findViewById(R.id.editTextTime);
        csl = findViewById(R.id.layoutPrincipal);
        button = (Button) findViewById(R.id.button);
        //CAMBIAR DATE POR CALENDAR
        Calendar cl  = Calendar.getInstance();
        cambiarFondoPorHora(new Date().getHours());
        button.setOnClickListener(v -> {
            try {
                int hour = Integer.parseInt(new SimpleDateFormat("HH:mm").format(new SimpleDateFormat("HH:mm").parse(time.getText().toString())).split(":")[0]);
                cambiarFondoPorHora(hour);
            } catch (ParseException e) {
                e.printStackTrace();
                // Aquí puedes agregar un mensaje de error o realizar alguna acción de manejo de excepciones.
            }
        });
    }


    public void cambiarFondoPorHora(int hora) {
        SimpleDateFormat dformat = new SimpleDateFormat("HH:mm");
        if (hora >= 0 && hora < TRAMO_VERDE) {
            csl.setBackgroundResource(R.color.amarillo);
        } else if (hora >= TRAMO_VERDE && hora < TRAMO_ROJO) {
            csl.setBackgroundResource(R.color.verde);
        } else if (hora >= TRAMO_ROJO && hora < TRAMO_AMARILLO) {
            csl.setBackgroundResource(R.color.rojo);
        } else if (hora >= 24) {
            Toast.makeText(getApplicationContext(), "La hora no es válida", Toast.LENGTH_SHORT).show();
        }
    }


}