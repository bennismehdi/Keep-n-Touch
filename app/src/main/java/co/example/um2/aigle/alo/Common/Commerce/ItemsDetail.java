package co.example.um2.aigle.alo.Common.Commerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.example.um2.aigle.alo.R;

public class ItemsDetail extends AppCompatActivity {

    TextView itemName, itemTelephone, itemCity, itemDescription, owner, prix;

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


        owner.setText(b.getString("owner"));
        itemDescription.setText(b.getString("description"));
        itemName.setText(b.getString("name"));
        itemCity.setText(b.getString("city"));
        itemTelephone.setText(b.getString("telephone"));
        prix.setText(b.getString("prix"));
    }
}
