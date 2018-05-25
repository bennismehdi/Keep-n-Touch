package co.example.um2.aigle.alo.Common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import co.example.um2.aigle.alo.Common.Social.ChatActivity;
import co.example.um2.aigle.alo.Common.Commerce.CommerceActivity;
import co.example.um2.aigle.alo.Common.News.NewsActivity;
import co.example.um2.aigle.alo.Common.Gadgets.ActualiteActivity;
import co.example.um2.aigle.alo.R;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_service);
    }

    public void goGadgets(View v){
        Intent intent = new Intent(v.getContext(), ActualiteActivity.class);
        startActivity(intent);
    }

    public void goNews(View v){
        Intent intent = new Intent(v.getContext(), NewsActivity.class);
        startActivity(intent);
    }

    public void goCommerce(View v){
        Intent intent = new Intent(v.getContext(), CommerceActivity.class);
        startActivity(intent);
    }

    public void goChat(View v){
        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        String[] datas = {};
        //TO DO//
        //rendre dynamique : TEST//
        intent.putExtra("group", "1");
        startActivity(intent);
    }
}
