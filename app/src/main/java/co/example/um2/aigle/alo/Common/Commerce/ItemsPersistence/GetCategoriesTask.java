package co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import co.example.um2.aigle.alo.Common.Commerce.ListItems.Item;

/**
 * Created by L'Albatros on 4/10/2018.
 */

public class GetCategoriesTask extends AsyncTask<String, String, List<String>>{

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
