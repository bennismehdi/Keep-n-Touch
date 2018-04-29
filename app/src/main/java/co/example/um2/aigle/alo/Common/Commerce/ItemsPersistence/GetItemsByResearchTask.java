package co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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


public class GetItemsByResearchTask extends AsyncTask <String, String, List<Item>> {

    private List<Item> items;
    private ItemAdapter itemAdapter;
    private Context c;
    private ProgressDialog dialog;
    private String url;

    public GetItemsByResearchTask(List<Item> items, ItemAdapter itemAdapter, Context c, String url) {
        this.url = url;
        this.items = items;
        this.itemAdapter = itemAdapter;
        this.c = c;
        dialog = new ProgressDialog(c);
        items.clear();
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Fetch Items");
        dialog.show();
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        this.itemAdapter.setItems(items);
        this.itemAdapter.notifyDataSetChanged();
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected List<Item> doInBackground(String... strings) {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        BufferedWriter bufferedWriter;
        InputStream inputStream;
        BufferedReader bufferedReader;

        try {
            URL url = new URL(this.url);

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                String post_data = URLEncoder.encode("research", "utf-8");
                post_data += "="+URLEncoder.encode(strings[0], "utf-8");
                post_data += "&"+URLEncoder.encode("categorie", "utf-8");
                post_data += "="+URLEncoder.encode(strings[1], "utf-8");

                Log.d("data receiverd", strings[0] +" and "+ strings[1]);

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

                String line;
                int i = 0;
                while((line = bufferedReader.readLine()) != null){
                    Log.d("Line " + i, line);
                    String[] str = line.split("&bptkce&");
                    try{
                        Item item = new Item(str[0], str[1], str[2], str[3], str[4], str[5], str[6], str[7], str[8]);
                        items.add(item);
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
        return items;
    }
}
