package br.com.daniel.hackathon.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pixplicity.easyprefs.library.Prefs;
import com.shashank.sony.fancytoastlib.FancyToast;

import br.com.daniel.hackathon.R;
import br.com.daniel.hackathon.network.api.RetrofitClientInstance;
import br.com.daniel.hackathon.network.response.generateSpot.ResponseGenerateSpot;
import br.com.daniel.hackathon.network.response.nextSpot.ResponseNextSpot;
import br.com.daniel.hackathon.network.service.getSearchForNextSpotService;
import br.com.daniel.hackathon.network.service.searchForNextSpotService;
import br.com.daniel.hackathon.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NewSpotActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView back;
    private TextView suggest;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GoogleMap mMap;
    private LatLng localPassenger;
    public static final String TAG = "ACTIVITY TAG";
    private Call<ResponseNextSpot> call;
    private TextView where;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_spot);
        initComponent();
        searchNextSpot();
        openTheTravelScreen();
        openTheMainScreen();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapSpot);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("WrongViewCast")
    public void initComponent() {
        back = findViewById(R.id.imgBackNewSopt);
        suggest = findViewById(R.id.txtSpot);
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

    public void openTheMainScreen() {

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        recoverLocationUser();
    }

    private void recoverLocationUser() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                //double latitude = Prefs.getDouble(Constants.LATITUDENEXTSPOT, 0);
               // double longitude = Prefs.getDouble(Constants.LONGITUDENEXTSOPT, 0);
                localPassenger = new LatLng(latitude, longitude);

                mMap.clear();
                mMap.addMarker(
                        new MarkerOptions()
                                .position(localPassenger)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.uber_sopt))
                );
                mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(localPassenger, 15)
                );

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //Solicitar atualizações de localização
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000,
                    10,
                    locationListener
            );
            return;
        }
    }

    public void searchNextSpot() {

        getSearchForNextSpotService getSearchForNextSpotService = RetrofitClientInstance.getRetrofitInstance(getSearchForNextSpotService.class);
        call = getSearchForNextSpotService.searchSpot();

        call.enqueue(new Callback<ResponseNextSpot>() {
            @Override
            public void onResponse(Call<ResponseNextSpot> call, Response<ResponseNextSpot> response) {

                try {

                    if (response.isSuccessful()) {
                        Prefs.putDouble(Constants.LATITUDENEXTSPOT, response.body().getLatitude());
                        Prefs.putDouble(Constants.LONGITUDENEXTSOPT, response.body().getLongitude());
                    }

                } catch (NullPointerException ignore) {
                    ignore.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<ResponseNextSpot> call, Throwable t) {

                Timber.tag(TAG).d("onFailure buscarTodosClientes, requisicao falhou" +t);

            }
        });

    }
}