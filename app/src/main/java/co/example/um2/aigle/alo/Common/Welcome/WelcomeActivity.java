package co.example.um2.aigle.alo.Common.Welcome;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import co.example.um2.aigle.alo.R;

public class WelcomeActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textView = (TextView) findViewById(R.id.textview);
        String message;

        SharedPreferences sharedPreferences = getSharedPreferences("AloAloPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


            message = "Welcome ";
            if(!sharedPreferences.getString("nom", "").toString().equals("empty")){
                message += sharedPreferences.getString("nom", "???");
                if(!sharedPreferences.getString("prenom", "").toString().equals("empty")){
                    message += " " +sharedPreferences.getString("prenom", "???");
                }
            }else if(!sharedPreferences.getString("prenom", "").toString().equals("empty")){
                message += sharedPreferences.getString("prenom", "???");
            }else{
                message += sharedPreferences.getString("pseudo", "???");
            }

        textView.setText(message);
    }
}
