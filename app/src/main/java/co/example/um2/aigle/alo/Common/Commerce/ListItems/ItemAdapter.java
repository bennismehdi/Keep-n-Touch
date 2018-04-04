package co.example.um2.aigle.alo.Common.Commerce.ListItems;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.example.um2.aigle.alo.R;

/**
 * Created by L'Albatros on 4/2/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_cel, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.display(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
