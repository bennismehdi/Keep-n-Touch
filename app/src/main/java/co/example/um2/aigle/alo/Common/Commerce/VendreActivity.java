package co.example.um2.aigle.alo.Common.Commerce;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.GetCategoriesTask;
import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.PostItemTask;
import co.example.um2.aigle.alo.Common.ServiceActivity;
import co.example.um2.aigle.alo.R;

public class VendreActivity extends AppCompatActivity implements LocationListener {

    private EditText itemNom, itemDescription, itemPrix;
    private TextView longitude, lattitude;
    private LocationManager locationManager;
    private String id;
    private String city;
    private Spinner spinner;
    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendre);

        Geocoder geocoder = new Geocoder(VendreActivity.this, Locale.getDefault());


        try{
            SharedPreferences sharedPreferences = getSharedPreferences("AloAloPreferences", MODE_PRIVATE);
            id = sharedPreferences.getString("id", null);
        }catch (Exception e){
            Log.d("Error", "Not able to open shared preferences");
        }

        itemNom = (EditText) findViewById(R.id.itemNom);
        itemDescription = (EditText) findViewById(R.id.itemDescription);
        itemPrix = (EditText) findViewById(R.id.itemPrix);
        longitude = (TextView) findViewById(R.id.longitude);
        lattitude = (TextView) findViewById(R.id.lattitude);
        spinner = (Spinner) findViewById(R.id.spinner);

        GetCategoriesTask getCategoriesTask = new GetCategoriesTask();
        try {
            List<String> categories = getCategoriesTask.execute().get();
            ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(VendreActivity.this, android.R.layout.simple_list_item_1, categories);
            spinner.setAdapter(categoriesAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        locationManager = (LocationManager) this.getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling ce truc retourne quoi ?? Ajouter un test ici
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    11);
            //    ActivityCompat#requestPermissions
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        if(locationManager == null){
            Log.d("ERREUR DE NULLISSISME", "NULL");
        }
        try{
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude.setText(location.getLongitude()+"");
            lattitude.setText(location.getLatitude()+"");
            this.addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                this.city = this.addresses.get(0).getLocality();

            }
            else {
                Log.d("Erreur", "Vous n'êtes pas localisé correctement, vérifiez votre GPS");
                Intent intent = new Intent(VendreActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        }catch (Exception e){
            this.finish();
            Log.d("Error ?!!!!!!!!!!!!!", e.getMessage());
        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void miseEnVente(View v){
        if(itemNom.getText().toString().length() < 3){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("Pas moins de 4 caractères pour le nom de votre objet");
            builder.setPositiveButton("ok", null);
            AlertDialog a = builder.create();
            a.show();
        }else if(itemNom.getText().toString().length() > 60){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("Pas plus de 60 caractères possibles");
            builder.setPositiveButton("ok", null);
            AlertDialog a = builder.create();
            a.show();
        }else if(itemDescription.getText().toString().length() < 20){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("Pas moins de 50 caractères pour la description de votre objet");
            builder.setPositiveButton("ok", null);
            AlertDialog a = builder.create();
            a.show();
        }else if( Double.parseDouble(itemPrix.getText().toString()) < 0 ){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("le prix ne peut être négatif");
            builder.setPositiveButton("ok", null);
            AlertDialog a = builder.create();
            a.show();
        }else{
            String[] str = this.spinner.getSelectedItem().toString().split(" : ");
            PostItemTask postItemTask = new PostItemTask(this.getApplicationContext());
            postItemTask.execute(this.id,
                    str[0],
                    this.itemNom.getText().toString(),
                    this.itemDescription.getText().toString(),
                    this.itemPrix.getText().toString(),
                    this.longitude.getText().toString(),
                    this.lattitude.getText().toString(),
                    this.city);
        }
        Intent intent = new Intent(VendreActivity.this, ServiceActivity.class);
        startActivity(intent);
    }
}
