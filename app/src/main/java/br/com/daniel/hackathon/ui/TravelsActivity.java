package br.com.daniel.hackathon.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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

import br.com.daniel.hackathon.R;
import br.com.daniel.hackathon.network.api.RetrofitClientInstance;
import br.com.daniel.hackathon.network.models.Usuario;
import br.com.daniel.hackathon.network.response.generateSpot.ResponseGenerateSpot;
import br.com.daniel.hackathon.network.service.searchForNextSpotService;
import br.com.daniel.hackathon.utils.Constants;
import br.com.daniel.hackathon.utils.ObtainGPS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TravelsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ObtainGPS gps;
    private ImageView back;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GoogleMap mMap;
    private LatLng localPassenger;
    public static final String TAG = "ACTIVITY TAG";
    private Call<ResponseGenerateSpot> call;
    private TextView where;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);
        initComponent();
        openTheMainScreen();
        nextSpot();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("WrongViewCast")
    public void initComponent() {
        back = findViewById(R.id.imgBack);
        where = findViewById(R.id.txtWhere);
    }

    public void openTheMainScreen() {

        back.setOnClickListener(new View.OnClickListener() {
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
        recuperarLocalizacaoUsuario();
    }

    private void recuperarLocalizacaoUsuario() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Prefs.putString(Constants.LATITUDE, String.valueOf(latitude));
                Prefs.putString(Constants.LONGITUDE, String.valueOf(longitude));

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

        public void searchForNextSpot() {
            String latitude = Prefs.getString(Constants.LATITUDE, "");
            String longitude = Prefs.getString(Constants.LONGITUDE, "");

            Usuario usuario = new Usuario();
            usuario.setLatitude(latitude);
            usuario.setLongitude(longitude);

            searchForNextSpotService forNextSpotService = RetrofitClientInstance.getRetrofitInstance(searchForNextSpotService.class);
            call = forNextSpotService.searchSpot(usuario);

            call.enqueue(new Callback<ResponseGenerateSpot>() {
                @Override
                public void onResponse(Call<ResponseGenerateSpot> call, Response<ResponseGenerateSpot> response) {

                }

                @Override
                public void onFailure(Call<ResponseGenerateSpot> call, Throwable t) {

                    Timber.tag(TAG).e("onFailure ResponseClientes, requisicao falhou" +t);

                }
            });

        }

        public void nextSpot() {
            where.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchForNextSpot();
                    Intent intent = new Intent(getApplicationContext(), RunningActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
        }

    }
