package br.com.daniel.hackathon.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import br.com.daniel.hackathon.R;

public class RunningActivity extends AppCompatActivity {
    private ImageView back;
    private Button destiny;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        initComponent();
        openTheTravelScreen();
        openTheNewSpotScreen();
    }

    @SuppressLint("WrongViewCast")
    public void initComponent() {
        back = findViewById(R.id.imgBackRunning);
        destiny  = findViewById(R.id.btSpot);
    }

    public void openTheTravelScreen() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TravelsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void openTheNewSpotScreen() {

        destiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewSpotActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}