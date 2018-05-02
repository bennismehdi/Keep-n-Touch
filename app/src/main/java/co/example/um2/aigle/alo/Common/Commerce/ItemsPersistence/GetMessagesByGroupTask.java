package co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import co.example.um2.aigle.alo.Common.Commerce.ListItems.Item;
import co.example.um2.aigle.alo.Common.Commerce.ListItems.ItemAdapter;

/**
 * Created by dewispelaere on 30/04/18.
 */

public class GetMessagesByGroupTask extends AsyncTask<String, String, List<Item>> {

    private ProgressDialog dialog;
    private ItemAdapter itemAdapter;
    private Context c;
    private String url;
    List<Item> items;

    public GetMessagesByGroupTask(Context c, List<Item> items, ItemAdapter itemAdapter, String url) {
        this.url = url;
        this.c = c;
        this.dialog = new ProgressDialog(c);
        this.itemAdapter = itemAdapter;
        this.items = items;
        items.clear();
    }

    protected void onPostExecute(List<Item> items) {
        this.itemAdapter.setItems(this.items);
        this.itemAdapter.notifyDataSetChanged();
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected List<Item> doInBackground(String... strings) {
        return null;
    }
}
