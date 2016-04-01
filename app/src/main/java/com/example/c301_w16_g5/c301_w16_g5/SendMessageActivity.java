package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageActivity extends ChickBidActivity {
    private EditText usernameEditText;
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameEditText = (EditText) findViewById(R.id.username);
        messageEditText = (EditText) findViewById(R.id.messageEditText);

        Button sendButton = (Button) findViewById(R.id.buttonSend);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();

                if (ChickBidsApplication.getUserController().usernameInUse(username)) {
                    ChickBidsApplication.getChickenController().addNotification(username,
                            messageEditText.getText().toString());
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Username not in use",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
