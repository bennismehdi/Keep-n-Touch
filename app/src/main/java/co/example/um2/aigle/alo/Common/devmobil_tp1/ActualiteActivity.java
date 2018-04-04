package co.example.um2.aigle.alo.Common.devmobil_tp1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import co.example.um2.aigle.alo.R;
import zh.wang.android.yweathergetter4a.WeatherInfo;
import zh.wang.android.yweathergetter4a.YahooWeather;
import zh.wang.android.yweathergetter4a.YahooWeatherInfoListener;

//import com.example.dewispelaere.test.R;

public class ActualiteActivity extends AppCompatActivity implements LocationListener, YahooWeatherInfoListener {

    private static int CODE_ACTIVITE = 1;

    private TextView textView_location;
    private TextView textView_weather;
    private ImageView imageView_weather;
    private Button button_retour;
    private Button button_reveil;
    private ListView listNews;
    private ProgressBar loader;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private YahooWeather mYahooWeather = YahooWeather.getInstance(5000, true);

    public double longitude;
    public double latitude;



    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Intent intent = new Intent(this, ReveilActivity.class);

        final Context context = this;
        final Activity activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualite);


        textView_location = findViewById(R.id.TextView_location);
        textView_weather = findViewById(R.id.TextView_weather);
        imageView_weather = findViewById(R.id.ImageView_weather);
        button_retour = findViewById(R.id.Button_retour);
        button_reveil = findViewById(R.id.Button_reveil);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        listNews = findViewById(R.id.Listview_news);
        loader = findViewById(R.id.Progress_news);
        listNews.setEmptyView(loader);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    11);
        }

        if(NewsFunction.isNetworkAvailable(getApplicationContext()))
        {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        onLocationChanged(location);
                    }
                }
            });
        }

        button_reveil.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, CODE_ACTIVITE);
            }
        });

        button_retour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        mYahooWeather.setNeedDownloadIcons(true);
        mYahooWeather.setUnit(YahooWeather.UNIT.CELSIUS);
        mYahooWeather.setSearchMode(YahooWeather.SEARCH_MODE.PLACE_NAME);

        longitude = location.getLongitude();
        latitude = location.getLatitude();

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Toast.makeText(getApplicationContext(), addresses.get(0).getLocality(), Toast.LENGTH_SHORT).show();
            textView_location.setText(addresses.get(0).getLocality());
            mYahooWeather.queryYahooWeatherByLatLon(getApplicationContext(), latitude, longitude, ActualiteActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {
        if(weatherInfo != null){
            textView_weather.setText("weather: " + weatherInfo.getCurrentText() + "\n" +
                    "temperature in ºC: " + weatherInfo.getCurrentTemp() + "\n" +
                    "Code : " + weatherInfo.getCurrentCode() + "\n"
            );

            //Traitement de la date et des horaires de lever/coucher du soleil pour permettre une comparaison
            int currentTimeH = new Date().getHours();
            int currentTimeM = new Date().getMinutes();
            String sunrise = weatherInfo.getAstronomySunrise();
            String sunset = weatherInfo.getAstronomySunset();
            String[] sunriseSplit = sunrise.split(" ");
            String[] sunsetSplit = sunset.split(" ");
            String[] sunriseHM = sunriseSplit[0].split(":");
            String[] sunsetHM = sunsetSplit[0].split(":");
            int sunriseH = Integer.parseInt(sunriseHM[0]);
            int sunriseM = Integer.parseInt(sunriseHM[1]);
            int sunsetH = Integer.parseInt(sunsetHM[0]);
            int sunsetM = Integer.parseInt(sunsetHM[1]);
            boolean nuit = false;

            if(sunriseSplit[1].equals("pm")){
                sunriseH += 12;
            }
            if(sunsetSplit[1].equals("pm")){
                sunsetH += 12;
            }

            //Est-ce qu'il fait nuit ?
            if(currentTimeH < sunriseH || (currentTimeH == sunriseH && currentTimeM < sunriseM) || currentTimeH > sunsetH || (currentTimeH == sunsetH && currentTimeM > sunsetM)){
                nuit = true;
            }

            //selfmade switch
            int code = weatherInfo.getCurrentCode();
            if ((code >= 0 && code <= 4 )|| (code >= 37 && code <= 39) || code == 45 || code == 47) {
                imageView_weather.setImageResource(R.drawable.ic_storm);
            }
            else if((code != 9 && code != 17 && code != 11 && code != 12 && code >= 5 && code <= 18) || (code >= 41 && code <= 43) || code == 46){
                imageView_weather.setImageResource(R.drawable.ic_snow);
            }
            else if(code == 17 || code == 35){
                imageView_weather.setImageResource(R.drawable.ic_hail);
            }
            else if((code >= 19 && code <= 23 ) || (code >= 27 && code <= 30)){
                if(nuit)
                    imageView_weather.setImageResource(R.drawable.ic_night_cloud);
                else
                    imageView_weather.setImageResource(R.drawable.ic_partly_cloudy);
            }
            else if(code == 11 || code == 12 || code == 17){
                if(nuit)
                    imageView_weather.setImageResource(R.drawable.ic_night_rain);
                else
                    imageView_weather.setImageResource(R.drawable.ic_rain);
            }
            else if(code == 24 || code == 25 || code == 32 || code == 36){
                if(nuit)
                    imageView_weather.setImageResource(R.drawable.ic_moon);
                else
                    imageView_weather.setImageResource(R.drawable.ic_sun);
            }
            else if(code == 26){
                imageView_weather.setImageResource(R.drawable.ic_cloud);
            }
            else{
                //erreur : temps non connu
            }

        }
        else{
            //erreur : pas d'info
        }
    }

    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";
            //TO DO CHANGER "PARIS"
            xml = NewsFunction.excuteGet("https://newsapi.org/v2/everything?q=paris&apiKey=9757edd7c39b47b3bfc103d3245dc3bf", urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){ // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
                        map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListNewsAdapter adapter = new ListNewsAdapter(ActualiteActivity.this, dataList);
                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                    Intent i = new Intent(ActualiteActivity.this, DetailsActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }



    }

}
