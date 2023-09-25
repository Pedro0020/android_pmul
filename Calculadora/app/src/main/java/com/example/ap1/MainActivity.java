package com.example.ap1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button suma = (Button) this.findViewById(R.id.mas);
        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suma();
            }
        });

        Button resta = (Button) this.findViewById(R.id.menos);
        resta.setOnClickListener(view -> resta());

        Button multiplicacion = (Button) this.findViewById(R.id.multiplicar);
        multiplicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplicar();
            }
        });
    }

    private void suma() {
        EditText n1 = this.findViewById(R.id.n1);
        EditText n2 = this.findViewById(R.id.n2);
        try {
            int number1 = Integer.parseInt(n1.getText().toString());
            int number2 = Integer.parseInt(n2.getText().toString());
            TextView tv = this.findViewById(R.id.result);
            tv.setText((String.valueOf(number2+number1)));
        }catch (NumberFormatException ex){
            Toast.makeText(this,R.string.mensaje, Toast.LENGTH_SHORT).show();
        }
    }
    private void resta() {
        EditText n1 = this.findViewById(R.id.n1);
        EditText n2 = this.findViewById(R.id.n2);
        try {
            int number1 = Integer.parseInt(n1.getText().toString());
            int number2 = Integer.parseInt(n2.getText().toString());
            TextView tv = this.findViewById(R.id.result);
            tv.setText((String.valueOf(number1-number2)));
        }catch (NumberFormatException ex){
            Toast.makeText(this,R.string.mensaje, Toast.LENGTH_SHORT).show();
        }
    }
    private void multiplicar() {
        EditText n1 = this.findViewById(R.id.n1);
        EditText n2 = this.findViewById(R.id.n2);
        try {
            int number1 = Integer.parseInt(n1.getText().toString());
            int number2 = Integer.parseInt(n2.getText().toString());
            TextView tv = this.findViewById(R.id.result);
            tv.setText((Integer.toString(number2*number1)));
        }catch (NumberFormatException ex){
            Toast.makeText(this,R.string.mensaje, Toast.LENGTH_SHORT).show();
        }

    }
}