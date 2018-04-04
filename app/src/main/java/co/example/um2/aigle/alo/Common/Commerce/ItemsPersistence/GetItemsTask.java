package co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence;

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

/**
 * Created by L'Albatros on 4/2/2018.
 */

public class GetItemsTask extends AsyncTask<String, String, List<Item> >{

    private List<Item> items = new ArrayList<Item>();
    public String result = "";
    public Context c;

    public GetItemsTask(Context c) {
        this.c = c;
    }

    @Override
    protected List<Item> doInBackground(String... strings) {
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        BufferedReader bufferedReader;

        result = "";

        String path = "http://bennismehdi-001-site1.gtempurl.com/getitems.php";

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
                    Log.d("Line " + i, line);
                    String[] str = line.split("&bptkce&");
                    try{
                        Item item = new Item(str[0], str[1], str[2], str[3], str[4], str[5], str[6]);
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
}
