package co.example.um2.aigle.alo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
import java.util.concurrent.ExecutionException;

import co.example.um2.aigle.alo.Common.Configuration.ConfiguratoinActivity;
import co.example.um2.aigle.alo.Common.Registration.RegistrationActivity;
import co.example.um2.aigle.alo.Common.Welcome.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    private EditText pseudo, motdepasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pseudo = (EditText) findViewById(R.id.pseudo);
        motdepasse = (EditText) findViewById(R.id.motdepasse);
    }

    public void regBtn(View v){
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
    }

    public void login(final View v) throws ExecutionException, InterruptedException {
            String pseudo = this.pseudo.getText().toString();
            String motdepasse = this.motdepasse.getText().toString();

            /*Définition de l'aynctask pour la requete HTTP de login*/
            AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            private String path = "http://bennismehdi-001-site1.gtempurl.com/Login.php";
            private String pseudo = "";
            private String motdepasse = "";

            private String result = "";
            ProgressDialog progressDialog = new ProgressDialog(v.getContext());


            @Override
            protected void onPreExecute() {

                progressDialog.setMessage("Fetching server");
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                String line = "";
                pseudo = strings[0];
                motdepasse = strings[1];

                try {
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();


                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                    String data_post = URLEncoder.encode("pseudo", "utf-8") + "=" + URLEncoder.encode(pseudo, "utf-8");
                    data_post += "&"+URLEncoder.encode("motdepasse", "utf-8") + "=" + URLEncoder.encode(motdepasse, "utf-8");

                    bufferedWriter.write(data_post);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

                    while((line = bufferedReader.readLine()) != null){
                        result += line + "\n";
                    }


                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    result += e.getMessage();
                    e.printStackTrace();
                } catch (IOException e) {
                    result += e.getMessage();
                    e.printStackTrace();
                }

                if(result.equals("")){
                    result = "empty?";
                }

                return this.result;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.setMessage("Got response : " + result);
                progressDialog.dismiss();
            }
        };

        String response = asyncTask.execute(pseudo, motdepasse).get();
        //Utilisation inutile : que pour le test des données reçues au login
        //AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        if(response.equals("empty?")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
            builder1.setMessage("Ce pseudo ou ce mot de passe est erroné");
            builder1.setPositiveButton("ok", null);
            AlertDialog a = builder1.create();
            a.show();
        }else{
            String[] datas = {};

            try{
                ///Les données ici sont reçues ligne par ligne du fichier php où chaque ligne représente une donnée est suivie par \n
                datas = response.split("\n");
                //Suite des tests
        /*
        builder.setMessage("Response : \ndata 0 : " + datas[0] + "\ndata 1 : " + datas[1]+ "\ndata 2 : " + datas[2] + "\ndata 3 : " + datas[3]
                + "\ndata 4 : " + datas[4] +  "\ndata 5 : " + datas[5] +  "\ndata 6 : " + datas[6]);

        builder.setPositiveButton("Ok", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/

                Intent intent = new Intent(v.getContext(), ConfiguratoinActivity.class);
                intent.putExtra("id", datas[0]);
                intent.putExtra("nom", datas[1]);
                intent.putExtra("prenom", datas[2]);
                intent.putExtra("pseudo", datas[3]);
                intent.putExtra("mail", datas[4]);
                intent.putExtra("telephone", datas[5]);
                intent.putExtra("motdepasse", datas[6]);

                startActivity(intent);
            }catch (Exception e){
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setMessage("Something goes wrong with your datas.");
                builder1.setPositiveButton("ok", null);
                AlertDialog a = builder1.create();
                a.show();
            }



        }
    }
}
