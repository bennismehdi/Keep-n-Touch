package co.example.um2.aigle.alo;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChatActivity extends AppCompatActivity {

    private static int CODE_ACTIVITE = 1;

    private Button buttonBack;
    private Button buttonSetting;
    private Button buttonSend;
    private EditText editTextMessage;

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

            }
        });
    }
}
