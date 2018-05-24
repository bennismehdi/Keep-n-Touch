package co.example.um2.aigle.alo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.GetMessagesByGroupTask;
import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.PostMessageTask;
import co.example.um2.aigle.alo.Common.News.Model.ChatMessage;
import co.example.um2.aigle.alo.Common.ServiceActivity;

public class ChatActivity extends AppCompatActivity {

    private static int CODE_ACTIVITE = 1;

    private Button buttonBack;
    private Button buttonSetting;
    private Button buttonSend;
    private EditText editTextMessage;
    private RecyclerView listViewMessages;

    private String user;
    private String group;
    private List<ChatMessage> messages;
    private ListMessagesAdapter listMessagesAdapter;

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
        listViewMessages = findViewById(R.id.Listview_chat);

        //A verifier
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("AloAloPreferences", MODE_PRIVATE);
            user = sharedPreferences.getString("user", null);
            group = getIntent().getStringExtra("group");
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
                    postMessageTask.execute("14", "1", message);
//                    Intent intent = new Intent(ChatActivity.this, ServiceActivity.class);
//                    startActivity(intent);
                    editTextMessage.setText("");
                }
            }
        });

        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        messages = new ArrayList<ChatMessage>();
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        listMessagesAdapter = new ListMessagesAdapter(messages);
        listViewMessages.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        listViewMessages.setAdapter(listMessagesAdapter);
        GetMessagesByGroupTask getMessagesByGroupTask = new GetMessagesByGroupTask(context, messages, listMessagesAdapter);
        getMessagesByGroupTask.execute("1");
    }

    public class ListMessagesAdapter extends RecyclerView.Adapter<ListMessagesViewHolder> {
        //private Activity activity;
        private List<ChatMessage> listMessages;

        public ListMessagesAdapter(List<ChatMessage> listM) {
            //activity = a;
            listMessages = listM;
        }
        public Object getItem(int position) {
            return position;
        }

        @Override
        public ListMessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(R.layout.chat_send, parent, false);
            return new ListMessagesViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ListMessagesViewHolder holder, int position) {
            holder.display(listMessages.get(position));
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() { return listMessages.size(); }

        public void setListMessages(List<ChatMessage> listM) { listMessages = listM; notifyDataSetChanged();}
        /*public View getView(int position, View convertView, ViewGroup parent) {
            ListMessagesViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(
                        R.layout.chat_send, parent, false);
                holder = new ListMessagesViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ListMessagesViewHolder) convertView.getTag();
            }
            holder.mContent.setId(position);
            holder.mAuthor.setId(position);

            try{
                holder.mContent.setText(listMessages.get(position).getContent());
                holder.mAuthor.setText(listMessages.get(position).getAuthor());

            }catch(Exception e) {}
            return convertView;
        }*/
    }

    class ListMessagesViewHolder extends RecyclerView.ViewHolder{
        public TextView mContent;
        public TextView mAuthor;

        public ListMessagesViewHolder(View v){
            super(v);

            mContent = v.findViewById(R.id.TextView_messageSend);
            mAuthor = v.findViewById(R.id.TextView_idSend);
        }

        public void display(ChatMessage m){
            mContent.setText(m.getContent());
            mAuthor.setText(m.getAuthor());
        }
    }

}

