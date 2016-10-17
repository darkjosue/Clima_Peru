package com.example.android.climaperu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class OxapampaActivity extends AppCompatActivity {

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL to query the USGS dataset for earthquake information */
    private static final String REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/forecast?id=3933874&APPID=77ceef6c40769b0d4fae12c45e6342b3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad);

        TsunamiAsyncTask task = new TsunamiAsyncTask();
        task.execute();
    }

    private class TsunamiAsyncTask extends AsyncTask<URL, Void, ArrayList<Clima>> {

        @Override
        protected ArrayList<Clima> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            ArrayList<Clima> climas = new ArrayList<>();

            if(TextUtils.isEmpty(jsonResponse)){
                return null;
            }

            try {
                JSONObject baseJsonResponse = new JSONObject(jsonResponse);
                JSONObject cityobj = baseJsonResponse.getJSONObject("city");
                String ciudad = cityobj.getString("name");
                Log.v(LOG_TAG,"La ciudad es "+ciudad);
                JSONArray arrayList = baseJsonResponse.getJSONArray("list");

                // If there are results in the features array
                if (arrayList.length() > 0) {

                    for(int i=0;i<arrayList.length();i++){
                        JSONObject myobj = arrayList.getJSONObject(i);
                        String fecha = myobj.getString("dt_txt");
                        //otros datos
                        JSONObject mainjson = myobj.getJSONObject("main");
                        double temp = mainjson.getDouble("temp");
                        double temp_min = mainjson.getDouble("temp_min");
                        double temp_max = mainjson.getDouble("temp_max");
                        JSONArray climaArray = myobj.getJSONArray("weather");
                        JSONObject climaobj = climaArray.getJSONObject(0);
                        String clima = climaobj.getString("main");
                        climas.add(new Clima(temp,temp_min,temp_max,ciudad, clima,fecha));
                    }

                    /*
                    // Create a new {@link Event} object
                    return new UsageEvents.Event(title, time, tsunamiAlert);
                    */
                    return climas;
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return climas;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link TsunamiAsyncTask}).
         */
        @Override
        protected void onPostExecute(ArrayList<Clima> climas) {
            if (climas == null) {
                return;
            }

            //updateUi(earthquake);
            ClimaAdapter adapter = new ClimaAdapter(OxapampaActivity.this,climas);

            ListView listView = (ListView)findViewById(R.id.listviewLima);
            listView.setAdapter(adapter);

        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";

            if(url == null){
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200){
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else{
                    Log.e(LOG_TAG, "Error response code: "+urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                // TODO: Handle the exception
                Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.",e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }


    }

}
