package com.c2.agent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView statusText;
    private Button startButton;
    private boolean serviceRunning = false;

    private int id(String name) {
        return getResources().getIdentifier(name, "id", getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResources().getIdentifier("activity_main", "layout", getPackageName()));
        statusText = findViewById(id("statusText"));
        startButton = findViewById(id("startButton"));
        startButton.setOnClickListener(v -> {
            if (!serviceRunning) {
                startService(new Intent(this, C2Service.class));
                serviceRunning = true;
                statusText.setText("Agent Status: ACTIVE - C2 connected");
                startButton.setText("STOP AGENT");
            } else {
                stopService(new Intent(this, C2Service.class));
                serviceRunning = false;
                statusText.setText("Agent Status: STOPPED");
                startButton.setText("START AGENT");
            }
        });
    }
}
