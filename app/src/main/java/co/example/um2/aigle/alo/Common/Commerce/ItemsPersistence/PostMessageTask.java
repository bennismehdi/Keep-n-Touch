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

/**
 * Created by dewispelaere on 30/04/18.
 */

public class PostMessageTask extends AsyncTask<String, String, String> {

    private Context c;
    private String result ="";

    public PostMessageTask(Context c){
        this.c = c;
    }
    @Override
    protected String doInBackground(String... strings) {
        Log.d("Received",strings[0] + " " +strings[1] + " " +strings[2]);

        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        BufferedWriter bufferedWriter;
        InputStream inputStream;
        BufferedReader bufferedReader;

        //TO DO créer fichier !!//
        String path = "https://quickandfresh.000webhostapp.com/postmessage.php";

        try {
            URL url = new URL(path);

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                String post_data = URLEncoder.encode("user_id", "utf-8");
                post_data += "="+URLEncoder.encode(strings[0], "utf-8");
                post_data += "&"+ URLEncoder.encode("group_id", "utf-8");
                post_data += "="+URLEncoder.encode(strings[1], "utf-8");
                post_data += "&"+URLEncoder.encode("content", "utf-8");
                post_data += "="+URLEncoder.encode(strings[2], "utf-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

                String line;

                while((line = bufferedReader.readLine()) != null){
                    result += line;
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

        Log.d("error? ", result);
        return result;
    }
}
