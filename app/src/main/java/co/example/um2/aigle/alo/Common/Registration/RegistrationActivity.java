package co.example.um2.aigle.alo.Common.Registration;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import co.example.um2.aigle.alo.Common.Registration.RegistrationTask;
import co.example.um2.aigle.alo.MainActivity;
import co.example.um2.aigle.alo.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nom, prenom, pseudo, mail, telephone, motdepasse, motdepasseconfirmation;
    RegistrationTask registrationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        pseudo = (EditText) findViewById(R.id.pseudo);
        mail = (EditText) findViewById(R.id.mail);
        telephone = (EditText) findViewById(R.id.telephone);
        motdepasse = (EditText) findViewById(R.id.motdepasse);
        motdepasseconfirmation = (EditText) findViewById(R.id.motdepasseconfirmation);

    }

    public void subscribe(View v){
        String nom = this.nom.getText().toString();
        String prenom = this.prenom.getText().toString();
        String pseudo = this.pseudo.getText().toString();
        String mail = this.mail.getText().toString();
        String telephone = this.telephone.getText().toString();
        String motdepasse = this.motdepasse.getText().toString();
        String motdepasseconfirmation = this.motdepasseconfirmation.getText().toString();

        /* CE QUI SUIT, c'est des tests sur les inputs en front end pour vérifier la validité des données */

        if( pseudo.length() < 4 ){
            AlertDialog alert =  new AlertDialog.Builder(this).create();
            alert.setTitle("Erreur ");
            alert.setMessage("Le pseudo doit avoir au moins 4 caractères");
            alert.show();
        }else if(motdepasse.length() < 6){
            AlertDialog alert =  new AlertDialog.Builder(this).create();
            alert.setTitle("Erreur ");
            alert.setMessage("Le mot de passe doit avoir au moins 6 caractères");
            alert.show();
        }else if(!motdepasse.equals(motdepasseconfirmation)){
            AlertDialog alert =  new AlertDialog.Builder(this).create();
            alert.setTitle("Erreur ");
            alert.setMessage("Les mot de passes ne sont pas pareils");
            alert.show();
        }else if(!isValidEmail(mail)){
            AlertDialog alert =  new AlertDialog.Builder(this).create();
            alert.setTitle("Erreur ");
            alert.setMessage("Email invalide");
            alert.show();
        }
        else{
            // ICI j'utilise une AsyncTask synchronisée par le get pour communiquer avec la base de données. Consulter Docu.
            registrationTask = new RegistrationTask(this);
            String r = "";

            try {
                r += registrationTask.execute(nom ,prenom, pseudo, mail, telephone, motdepasse).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if(r.equals("true")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Inscription");
                builder.setMessage("Inscription effectuée avec succès");
                builder.setPositiveButton("ok", new AlertDialog.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseContext() , MainActivity.class);
                                startActivity(intent);
                            }
                        }
                );
                AlertDialog alert = builder.create();
                alert.show();
            }else if(r.equals("false")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Inscription");
                builder.setMessage("Erreur de requête : Pseudo ou Email existent déjà !");
                builder.setPositiveButton("ok", null);
                AlertDialog alert = builder.create();
                alert.show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Inscription");
                builder.setMessage("Problème de base de données");
                builder.setPositiveButton("ok", new AlertDialog.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseContext() , MainActivity.class);
                                startActivity(intent);
                            }
                        }
                );
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    public void goLogin(View v){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }

    /* Methode utilisée pour valider les adresse email*/
    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
