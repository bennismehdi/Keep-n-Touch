package co.example.um2.aigle.alo.Common.Commerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import co.example.um2.aigle.alo.R;

public class ItemsDetail extends AppCompatActivity implements OnMapReadyCallback {

    TextView itemName, itemTelephone, itemCity, itemDescription, owner, prix;
    double longitude, lattitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_detail);

        Bundle b = getIntent().getExtras();

        itemName = (TextView) findViewById(R.id.itemName);
        itemCity = (TextView) findViewById(R.id.itemCity);
        itemTelephone = (TextView) findViewById(R.id.itemTelephone);
        itemDescription = (TextView) findViewById(R.id.itemDescription);
        owner = (TextView) findViewById(R.id.owner);
        prix = (TextView) findViewById(R.id.prix);
        longitude = Double.parseDouble(b.getString("longitude"));
        lattitude = Double.parseDouble(b.getString("lattitude"));

        owner.setText(b.getString("owner"));
        itemDescription.setText(b.getString("description"));
        itemName.setText(b.getString("name"));
        itemCity.setText(b.getString("city"));
        itemTelephone.setText(b.getString("telephone"));
        prix.setText(b.getString("prix"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng loc = new LatLng(lattitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(loc)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0F));
    }
}
