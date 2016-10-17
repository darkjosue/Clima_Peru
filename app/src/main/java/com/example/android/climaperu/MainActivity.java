package com.example.android.climaperu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Agregamos un eventlistener al textview
        TextView lima = (TextView)findViewById(R.id.lima);
        if(lima != null) {
            lima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent limaIntent = new Intent(MainActivity.this, LimaActivity.class);
                    startActivity(limaIntent);
                }
            });
        }

        TextView paracas = (TextView)findViewById(R.id.paracas);
        if(paracas != null) {
            paracas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent paracasIntent = new Intent(MainActivity.this, ParacasActivity.class);
                    startActivity(paracasIntent);
                }
            });
        }

        TextView oxapampa = (TextView)findViewById(R.id.oxapampa);
        if(oxapampa != null) {
            oxapampa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent oxapampaIntent = new Intent(MainActivity.this, OxapampaActivity.class);
                    startActivity(oxapampaIntent);
                }
            });
        }
    }
}
