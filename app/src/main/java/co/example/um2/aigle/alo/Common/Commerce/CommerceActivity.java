package co.example.um2.aigle.alo.Common.Commerce;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import co.example.um2.aigle.alo.R;

public class CommerceActivity extends AppCompatActivity implements Commerce_ByList.OnFragmentInteractionListener {



    private Commerce_ByList cList = new Commerce_ByList();
    private Commerce_ByLocalisation cLocalisation = new Commerce_ByLocalisation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commerce);
        FragmentManager fragmentManager  = getFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,  cList);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //TODO
    }

    public void switchList(View v){
        FragmentManager fragmentManager  = getFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,  cList);
        fragmentTransaction.commit();
    }

    public void switchLocalisation(View v){
        FragmentManager fragmentManager  = getFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,  cLocalisation);
        fragmentTransaction.commit();
    }
}
