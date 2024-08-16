package com.example.attendanceapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocationActivity extends AppCompatActivity implements AMapLocationListener {
    private static final String TAG = "LocationActivity";
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addressTextView = findViewById(R.id.address_text);

        // Initialize the map
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        // Request location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            try {
                initializeLocationClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    initializeLocationClient();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                addressTextView.setText("Permission denied");
            }
        }
    }

    private void initializeLocationClient() throws Exception {
        // Initialize privacy compliance
        AMapLocationClient.updatePrivacyShow(this, true, true);
        AMapLocationClient.updatePrivacyAgree(this, true);

        // Initialize location client
        locationClient = new AMapLocationClient(this);
        locationOption = new AMapLocationClientOption();
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setNeedAddress(true);
        locationOption.setOnceLocation(true);
        locationClient.setLocationOption(locationOption);
        locationClient.setLocationListener(this);
        locationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            Log.d(TAG, "Location changed: " + aMapLocation.toString());
            if (aMapLocation.getErrorCode() == 0) {
                LatLng currentLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.addMarker(new MarkerOptions().position(currentLatLng).title("You are here"));
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));

                String address = aMapLocation.getAddress();
                addressTextView.setText(address);
            } else {
                Log.e(TAG, "Location error, code: " + aMapLocation.getErrorCode() + ", message: " + aMapLocation.getErrorInfo());
                addressTextView.setText("Location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        } else {
            Log.e(TAG, "Location result is null");
            addressTextView.setText("Unable to get location");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationClient != null) {
            locationClient.onDestroy();
        }
    }
}
