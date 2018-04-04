package co.example.um2.aigle.alo.Common.Commerce.ListItems;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.example.um2.aigle.alo.R;

/**
 * Created by L'Albatros on 4/2/2018.
 */

public class ItemHolder extends RecyclerView.ViewHolder {
    private TextView itemOwner;
    private TextView itemNom;
    private TextView itemDescription;
    private TextView itemPrix;

    public ItemHolder(View itemView) {
        super(itemView);

        itemNom = (TextView) itemView.findViewById(R.id.itemNom);
        itemOwner = (TextView) itemView.findViewById(R.id.itemOwner);
        itemDescription = (TextView) itemView.findViewById(R.id.itemDescription);
        itemPrix = (TextView) itemView.findViewById(R.id.itemPrix);
    }

    public void display(Item item){
        itemOwner.setText(item.getNom());
        itemNom.setText(item.getItem());
        itemDescription.setText(item.getDescription());
        itemPrix.setText(item.getPrix());
    }
}
