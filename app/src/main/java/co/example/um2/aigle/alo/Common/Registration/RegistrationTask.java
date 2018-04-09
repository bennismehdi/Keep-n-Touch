package co.example.um2.aigle.alo.Common.Registration;

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
 * Created by L'Albatros on 3/7/2018.
 */

public class RegistrationTask extends AsyncTask<String, Void, String> {


    private Context c;
    private String result;

    public RegistrationTask(Context c) {
        this.c = c;
    }

    @Override
    protected String doInBackground(String... strings) {

        String nom = strings[0];
        String prenom = strings[1];
        String pseudo = strings[2];
        String mail = strings[3];
        String telephone = strings[4];
        String motdepasse = strings[5];

        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        BufferedWriter bufferedWriter;
        InputStream inputStream;
        BufferedReader bufferedReader;

        result = "";

        String path = "https://quickandfresh.000webhostapp.com/Registration.php";

        try {
            URL url = new URL(path);

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                String post_data = URLEncoder.encode("nom", "utf-8");
                post_data += "="+URLEncoder.encode(nom, "utf-8");
                post_data += "&"+ URLEncoder.encode("prenom", "utf-8");
                post_data += "="+URLEncoder.encode(prenom, "utf-8");
                post_data += "&"+URLEncoder.encode("pseudo", "utf-8");
                post_data += "="+URLEncoder.encode(pseudo, "utf-8");
                post_data += "&"+URLEncoder.encode("mail", "utf-8");
                post_data += "="+URLEncoder.encode(mail, "utf-8");
                post_data += "&"+URLEncoder.encode("telephone", "utf-8");
                post_data += "="+URLEncoder.encode(telephone, "utf-8");
                post_data += "&"+URLEncoder.encode("motdepasse", "utf-8");
                post_data += "="+URLEncoder.encode(motdepasse, "utf-8");

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

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String s) {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
