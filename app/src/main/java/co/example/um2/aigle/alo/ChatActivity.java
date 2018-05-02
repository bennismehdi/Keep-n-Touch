package co.example.um2.aigle.alo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.PostMessageTask;
import co.example.um2.aigle.alo.Common.ServiceActivity;

public class ChatActivity extends AppCompatActivity {

    private static int CODE_ACTIVITE = 1;

    private Button buttonBack;
    private Button buttonSetting;
    private Button buttonSend;
    private EditText editTextMessage;

    private String user;
    private String group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Context context = this;
        final Activity activity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        buttonBack = findViewById(R.id.Button_retour);
        buttonSetting = findViewById(R.id.Button_options);
        buttonSend = findViewById(R.id.Button_envoi);
        editTextMessage = findViewById(R.id.EditText_message);

        //A verifier
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("AloAloPreferences", MODE_PRIVATE);
            user = sharedPreferences.getString("user", null);
            group = sharedPreferences.getString("group", null);
        }catch (Exception e){

        }


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editTextMessage.getText().toString();
                if(!message.isEmpty()){
                    PostMessageTask postMessageTask = new PostMessageTask(context.getApplicationContext());
                    postMessageTask.execute(user, group, message);
                    Intent intent = new Intent(ChatActivity.this, ServiceActivity.class);
                    startActivity(intent);
                }
            }
        });

        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    class ListMessagesAdapter extends BaseAdapter {
        private Activity activity;
        private ArrayList<HashMap<String, String>> data;

        public ListMessagesAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
            activity = a;
            data=d;
        }
        public int getCount() {
            return data.size();
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ListMessagesViewHolder holder = null;
            /*if (convertView == null) {
                holder = new ListMessagesViewHolder();
                convertView = LayoutInflater.from(activity).inflate(
                        R.layout.list_row, parent, false);
                holder.galleryImage = (ImageView) convertView.findViewById(R.id.ImageView_news);
                holder.title = (TextView) convertView.findViewById(R.id.TextView_newsT);
                holder.sdetails = (TextView) convertView.findViewById(R.id.TextView_newsD);
                convertView.setTag(holder);
            } else {
                holder = (ListMessagesViewHolder) convertView.getTag();
            }
            holder.galleryImage.setId(position);
            holder.title.setId(position);
            holder.sdetails.setId(position);

            HashMap<String, String> song = new HashMap<String, String>();
            song = data.get(position);

            try{
                holder.title.setText(song.get(ActualiteActivity.KEY_TITLE));
                holder.sdetails.setText(song.get(ActualiteActivity.KEY_DESCRIPTION));

                if(song.get(ActualiteActivity.KEY_URLTOIMAGE).toString().length() < 5)
                {
                    holder.galleryImage.setVisibility(View.GONE);
                }else{
                    Picasso.with(activity)
                            .load(song.get(ActualiteActivity.KEY_URLTOIMAGE).toString())
                            .resize(300, 200)
                            .into(holder.galleryImage);
                }
            }catch(Exception e) {}*/
            return convertView;
        }
    }

    class ListMessagesViewHolder {
        ImageView galleryImage;
        TextView title, sdetails;
    }

}

