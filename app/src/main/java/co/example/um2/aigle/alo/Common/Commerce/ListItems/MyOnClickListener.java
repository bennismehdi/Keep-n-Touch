package co.example.um2.aigle.alo.Common.Commerce.ListItems;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import co.example.um2.aigle.alo.Common.Commerce.ItemsDetail;
import co.example.um2.aigle.alo.R;


/**
 * Created by L'Albatros on 4/22/2018.
 */

public class MyOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {

        TextView telephone = (TextView) v.findViewById(R.id.telephone);
        TextView city = (TextView) v.findViewById(R.id.itemCity);
        TextView description = (TextView) v.findViewById(R.id.itemDescription);
        TextView name = (TextView) v.findViewById(R.id.itemNom);
        TextView owner = (TextView) v.findViewById(R.id.itemOwner);
        TextView prix = (TextView) v.findViewById(R.id.itemPrix);

        String str = telephone.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Clicked ? ");
        builder.setMessage(str);
        builder.setPositiveButton("ok", null);
        AlertDialog a = builder.create();
        a.show();

        Intent intent = new Intent(v.getContext(), ItemsDetail.class);

        intent.putExtra("telephone", telephone.getText().toString());
        intent.putExtra("description", description.getText().toString());
        intent.putExtra("city", city.getText().toString());
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("owner", owner.getText().toString());
        intent.putExtra("prix", prix.getText().toString());

        v.getContext().startActivity(intent);
    }
}
