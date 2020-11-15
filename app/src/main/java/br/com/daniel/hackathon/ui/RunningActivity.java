package br.com.daniel.hackathon.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import br.com.daniel.hackathon.R;

public class RunningActivity extends AppCompatActivity {
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        initComponent();
        openTheTravelScreen();
    }

    @SuppressLint("WrongViewCast")
    public void initComponent() {
        back = findViewById(R.id.imgBackRunning);

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
}