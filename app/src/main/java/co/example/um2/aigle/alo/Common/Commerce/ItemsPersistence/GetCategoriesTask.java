package co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import co.example.um2.aigle.alo.Common.Commerce.ListItems.Item;
import co.example.um2.aigle.alo.Common.Commerce.ListItems.ItemAdapter;

/**
 * Created by L'Albatros on 4/10/2018.
 */

public class GetCategoriesTask extends AsyncTask<String, String, List<String>>{

    private ProgressDialog dialog;
    private List<String> categories;
    private Context c;
    ArrayAdapter<String> adapter; /* = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, categories);*/
    private Spinner spinner;
    private RecyclerView itemsRV;
    private List<Item> items;
    private ItemAdapter itemAdapter;

public GetCategoriesTask(Context c, Spinner spinner, RecyclerView itemsRV, List<String> categories, ArrayAdapter<String> adapter, ItemAdapter itemAdapter, List<Item> items) {
        this.c = c;
        this.spinner = spinner;
        this.itemsRV = itemsRV;
        dialog = new ProgressDialog(c);
        this.categories = categories;
        this.adapter = adapter;
        this.items = items;
        this.itemAdapter = itemAdapter;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Fetch categories");
        dialog.show();
    }

    @Override
    protected void onPostExecute(List<String> strings) {

        adapter.addAll(strings);


        if(itemsRV != null){
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str[]  = spinner.getSelectedItem().toString().split(" : ");
                    GetItemsByCategorieTask getItemsByCategorieTask = new GetItemsByCategorieTask(c, items , itemAdapter);
                    getItemsByCategorieTask.execute(str[1]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        String result = "";
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        BufferedReader bufferedReader;
        ArrayList<String> results = new ArrayList<String>();


        String path = "https://quickandfresh.000webhostapp.com/getcategories.php";

        try {
            URL url = new URL(path);

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
                    String[] str = line.split("&bptkce&");
                    try{
                        results.add(str[0] + " : " +str[1]);
                    }catch (Exception e){
                        Log.d("Error", "This line is empty or false");
                    }
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

        return results;
    }

}
