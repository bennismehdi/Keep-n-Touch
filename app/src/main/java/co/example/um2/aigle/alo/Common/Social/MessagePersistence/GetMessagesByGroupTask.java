package co.example.um2.aigle.alo.Common.Social.MessagePersistence;

import android.app.ProgressDialog;
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
import java.util.List;

import co.example.um2.aigle.alo.Common.Social.ChatActivity.ListMessagesAdapter;
import co.example.um2.aigle.alo.Common.Social.ChatMessage;

/**
 * Created by dewispelaere on 30/04/18.
 */

public class GetMessagesByGroupTask extends AsyncTask<String, String, List<ChatMessage>> {

    private ProgressDialog dialog;
    private ListMessagesAdapter messagesAdapter;
    private Context c;
    List<ChatMessage> messages;

    public GetMessagesByGroupTask(Context c, List<ChatMessage> messages, ListMessagesAdapter messagesAdapter) {
        this.c = c;
        this.dialog = new ProgressDialog(c);
        this.messagesAdapter = messagesAdapter;
        this.messages = messages;
        messages.clear();
    }

    protected void onPostExecute(List<ChatMessage> messages) {
        Log.d("POST EXE ?", "onPostExecute: reached");
        this.messagesAdapter.setListMessages(this.messages);
        if(dialog.isShowing()){
            Log.d("progress", "progress bar: ");
            dialog.dismiss();
        }
        this.messagesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Fetch Items");
        dialog.show();
    }

    @Override
    protected List<ChatMessage> doInBackground(String... strings) {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        BufferedWriter bufferedWriter;
        InputStream inputStream;
        BufferedReader bufferedReader;

        //result = "";

        String path = "https://quickandfresh.000webhostapp.com/getmessagesbygroup.php";

        try {
            URL url = new URL(path);

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                String post_data = URLEncoder.encode("group_id", "utf-8");
                post_data += "="+URLEncoder.encode(strings[0], "utf-8");

                bufferedWriter.write(post_data);
                Log.d("data posted", post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

                String line;
                int i = 0;
                while((line = bufferedReader.readLine()) != null){
                    Log.d("Tracing creating items", line);
                    //result += line;
                    Log.d("Line " + i, line);
                    String[] str = line.split("&bptkce&");
                    try{
                        ChatMessage m = new ChatMessage(str[2], str[0]);
                        this.messages.add(m);
                    }catch (Exception e){
                        Log.d("Error", "This line is empty or false");
                    }
                    //i++;
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

        Log.d("error? ", messages.size() + "");
        return this.messages;
    }
}
