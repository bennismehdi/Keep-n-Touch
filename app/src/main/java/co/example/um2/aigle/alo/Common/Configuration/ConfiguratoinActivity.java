package co.example.um2.aigle.alo.Common.Configuration;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import co.example.um2.aigle.alo.Common.ServiceActivity;
import co.example.um2.aigle.alo.Common.Welcome.WelcomeActivity;
import co.example.um2.aigle.alo.R;

public class ConfiguratoinActivity extends AppCompatActivity {

    private List<String> preferences;
    private AutoCompleteTextView preference;
    private TextView listepreference;
    private Button ajouterpreference;
    private ArrayAdapter<String> adapter;
    private List<String> strings;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuratoin);

        extras = getIntent().getExtras();

        strings = new ArrayList<String>();

        preferences =  new ArrayList<String>();
        preferences.add("sport");
        preferences.add("musique");
        preferences.add("people");
        preferences.add("art");
        preferences.add("mystère");
        preferences.add("voyage");
        preferences.add("crime");
        preferences.add("informatique");
        preferences.add("science");
        preferences.add("médecine");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, preferences);
        preference = (AutoCompleteTextView) findViewById(R.id.preference);
        preference.setAdapter(adapter);
        listepreference = (TextView) findViewById(R.id.listepreference);
        ajouterpreference = (Button) findViewById(R.id.ajouterpreference);

        preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.showDropDown();
            }
        });

        preference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(preferences.contains(preference.getText().toString())){
                    ajouterpreference.setClickable(true);
                }else{
                    ajouterpreference.setClickable(false);
                }
            }
        });
    }

    public void ajouterPreference(View v){
        String choice = preference.getText().toString();
        strings.add(choice);
        preferences.remove(choice);
        listepreference.setText(listepreference.getText().toString() + " " + choice);
        preference.setText("");
        adapter.notifyDataSetChanged();
    }

    public void valider(View v){
        SharedPreferences sharedPreferences = getSharedPreferences("AloAloPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putStringSet("preferences", new HashSet<String>(strings));
        editor.putString("id", extras.getString("id"));
        editor.putString("nom", extras.getString("nom"));
        editor.putString("prenom", extras.getString("prenom"));
        editor.putString("pseudo", extras.getString("pseudo"));
        editor.putString("mail", extras.getString("mail"));
        editor.putString("telephone", extras.getString("telephone"));
        editor.putString("motdepasse", extras.getString("motdepasse"));
        editor.commit();

        Intent intent = new Intent(v.getContext(), ServiceActivity.class);
        startActivity(intent);
    }
}
