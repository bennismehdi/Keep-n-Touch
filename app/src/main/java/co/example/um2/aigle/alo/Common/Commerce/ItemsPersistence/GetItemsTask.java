package co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import co.example.um2.aigle.alo.Common.Commerce.ListItems.Item;
import co.example.um2.aigle.alo.Common.Commerce.ListItems.ItemAdapter;

/**
 * Created by L'Albatros on 4/2/2018.
 */

public class GetItemsTask extends AsyncTask<String, String, List<Item> >{

    private List<Item> items;
    private ItemAdapter itemAdapter;
    public String result = "";
    private Context c;
    private ProgressDialog dialog;
    private String url;

    public GetItemsTask(Context c, List<Item> items, ItemAdapter itemAdapter, String url) {
        this.url = url;
        this.c = c;
        dialog = new ProgressDialog(c);
        this.itemAdapter = itemAdapter;
        this.items = items;
        items.clear();
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Fetch Items");
        dialog.show();
    }

    @Override
    protected List<Item> doInBackground(String... strings) {
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        BufferedReader bufferedReader;

        result = "";

        try {
            URL url = new URL(this.url);

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

                String line;
                int i = 0;
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                    Log.d("Line " + i, line);
                    String[] str = line.split("&bptkce&");
                    try{
                        Item item = new Item(str[0], str[1], str[2], str[3], str[4], str[5], str[6], str[7]);
                        this.items.add(item);
                    }catch (Exception e){
                        Log.d("Error", "This line is empty or false");
                    }
                    //i++;
                }
                inputStream.close();
                bufferedReader.close();
                httpURLConnection.disconnect();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("error? ", items.size() + "");
        return this.items;
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        this.itemAdapter.setItems(items);
        this.itemAdapter.notifyDataSetChanged();
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
