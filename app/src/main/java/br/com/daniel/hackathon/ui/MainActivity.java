package br.com.daniel.hackathon.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import br.com.daniel.hackathon.R;

public class MainActivity extends AppCompatActivity {
    private ImageView trip;
    private TextView tripRunning;
    private ImageView mapTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        openTheTravelScreen();
        pedirPermissaoAoUsuario();
    }


    private void pedirPermissaoAoUsuario() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    @SuppressLint("WrongViewCast")
    public void initComponent() {
        trip = findViewById(R.id.imgViagem);
        mapTrip = findViewById(R.id.imgMapViagem);
        tripRunning = findViewById(R.id.txtViagem);

    }

    public void openTheTravelScreen() {

        tripRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TravelsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TravelsActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mapTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TravelsActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

}