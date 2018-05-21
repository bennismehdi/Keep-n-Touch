package co.example.um2.aigle.alo.Common.Commerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import co.example.um2.aigle.alo.Common.Commerce.ListItems.Item;
import co.example.um2.aigle.alo.R;

public class MultipleMaps extends AppCompatActivity implements OnMapReadyCallback {

    private List<Item> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_maps);


        Intent i = getIntent();
        items = (List<Item>) i.getSerializableExtra("LIST");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng loc = null;
        for(Item i : items){
            loc  = new LatLng(Double.parseDouble(i.getLattitude().toString()), Double.parseDouble(i.getLongitude().toString()));
            googleMap.addMarker(new MarkerOptions().position(loc).title(i.getItem() + " " + i.getTelephone()));
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10.0F));



    }
}
