package com.example.android.climaperu;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Usuario on 17/10/2016.
 */
public class ClimaAdapter extends ArrayAdapter<Clima> {

    public ClimaAdapter(Activity context, ArrayList<Clima> climas){
        super(context, 0, climas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.list_item, parent, false);
        }

        Clima currentClima = getItem(position);

        TextView tempTextView = (TextView)listItemView.findViewById(R.id.temp);
        tempTextView.setText(formatoTemperatura(currentClima.getTemp()));

        TextView tempMintTextView = (TextView)listItemView.findViewById(R.id.temp_min);
        tempMintTextView.setText(formatoTemperatura(currentClima.getTempMin())+" mín.");

        TextView tempMaxTextView = (TextView)listItemView.findViewById(R.id.temp_max);
        tempMaxTextView.setText(formatoTemperatura(currentClima.getTempMax())+" máx.");

        TextView ciudadTextView = (TextView)listItemView.findViewById(R.id.ciudad);
        ciudadTextView.setText(currentClima.getCiudad());

        TextView climaTextView = (TextView)listItemView.findViewById(R.id.clima);
        climaTextView.setText(currentClima.getClima());

        TextView fechaTextView = (TextView)listItemView.findViewById(R.id.fecha);
        fechaTextView.setText(currentClima.getFecha());

        return listItemView;
    }

    private double TempACelsius (double tempK){
        return (tempK-273.15);
    }

    private String formatoTemperatura(double temp) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.00");
        return magnitudeFormat.format(TempACelsius(temp))+" °C";
    }



}
