package com.example.android.climaperu;

/**
 * Created by Usuario on 17/10/2016.
 */
public class Clima {

    private double mTemp;
    private double mTempMin;
    private double mTempMax;
    private String mCiudad;
    private String mClima;
    private String mFecha;

    public Clima (double temp, double temp_min, double temp_max, String ciudad, String clima, String fecha){
        mTemp = temp;
        mTempMin = temp_min;
        mTempMax = temp_max;
        mCiudad = ciudad;
        mClima = clima;
        mFecha = fecha;
    }

    public double getTemp(){return mTemp;}
    public double getTempMin(){return mTempMin;}
    public double getTempMax(){return mTempMax;}
    public String getCiudad() {return mCiudad;}
    public String getClima() {return mClima;}
    public String getFecha() {return mFecha;}
}
