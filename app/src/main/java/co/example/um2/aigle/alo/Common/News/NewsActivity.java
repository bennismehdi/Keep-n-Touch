package co.example.um2.aigle.alo.Common.News;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import co.example.um2.aigle.alo.Common.News.Handler.HTTPDataHandler;
import co.example.um2.aigle.alo.Common.News.Model.Item;
import co.example.um2.aigle.alo.Common.News.Model.RSSObject;
import co.example.um2.aigle.alo.R;

public class NewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RSSObject rssObject;

    private final  String RSS_LINK_HOME = "http://www.lemonde.fr/rss/une.xml";
    private final  String RSS_TO_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url=";
    //

    private StringBuilder stringBuilder = new StringBuilder(RSS_TO_JSON_API);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("News");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mnews);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        this.stringBuilder.append(RSS_LINK_HOME);
        loadRss(this.stringBuilder.toString());
    }

    private void loadRss(String the_url) {
        AsyncTask<String , String , String > loadRSSAsync = new AsyncTask<String, String, String>() {

            ProgressDialog mDialog = new ProgressDialog(NewsActivity.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please wait");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler httpDataHandler = new HTTPDataHandler();
                result = httpDataHandler.getHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s, RSSObject.class);
                FeedAdapter feedAdapter = new FeedAdapter(rssObject, getBaseContext());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        loadRSSAsync.execute(the_url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        if(item.getItemId() == R.id.menu_refresh)
            loadRss(this.stringBuilder.toString());
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.vingtminutes :
                this.stringBuilder = new StringBuilder(RSS_TO_JSON_API);
                this.stringBuilder.append("https://www.20minutes.fr/feeds/rss-actu-france.xml");
                loadRss(stringBuilder.toString());
                drawerLayout.closeDrawers();
                return true;
            case R.id.lemonde :
                this.stringBuilder = new StringBuilder(RSS_TO_JSON_API);
                this.stringBuilder.append("http://www.lemonde.fr/rss/une.xml");
                loadRss(stringBuilder.toString());
                drawerLayout.closeDrawers();
                return true;
            case R.id.azurever :
                this.stringBuilder = new StringBuilder(RSS_TO_JSON_API);
                this.stringBuilder.append("https://www.azurever.com/rss.xml");
                loadRss(stringBuilder.toString());
                drawerLayout.closeDrawers();
                return true;
            case R.id.yashinotraduction :
                this.stringBuilder = new StringBuilder(RSS_TO_JSON_API);
                this.stringBuilder.append("http://www.yoshinotrad.fr/blog/feed");
                loadRss(stringBuilder.toString());
                drawerLayout.closeDrawers();
                return true;
            case R.id.abimeconcept :
                this.stringBuilder = new StringBuilder(RSS_TO_JSON_API);
                this.stringBuilder.append("http://www.abime-concept.com/blog/feed/");
                loadRss(stringBuilder.toString());
                drawerLayout.closeDrawers();
                return true;
            case R.id.lachainemeteo :
                this.stringBuilder = new StringBuilder(RSS_TO_JSON_API);
                this.stringBuilder.append("http://actualite.lachainemeteo.com/meteo-rss/toute-l-actualite-meteo.xml");
                loadRss(stringBuilder.toString());
                drawerLayout.closeDrawers();
                return true;
            case R.id.commerce:
                this.stringBuilder = new StringBuilder(RSS_TO_JSON_API);
                this.stringBuilder.append("https://lbc2rss.superfetatoire.com/rss/74293-test-annonces-languedoc-roussillon");
                loadRss(stringBuilder.toString());
                drawerLayout.closeDrawers();
                return true;
        }
        return false;
    }
}
