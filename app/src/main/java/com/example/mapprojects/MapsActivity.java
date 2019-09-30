package com.example.mapprojects;
/*support telgram id =@javaprogrammer_eh
 * 11/05/1398
 * creted by elmira hossein zadeh*/

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapprojects.adapter.AdapterMontahkhab;
import com.example.mapprojects.adapter.AdapterSearch;
import com.example.mapprojects.dataBase.Database;
import com.example.mapprojects.model.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    DrawerLayout myDraw;
    ImageView search, menu, currentPlace;
    View mapView;
    TextView txt1Menu, txt2Menu, txt3Menu, txt4Menu, txtOffMenu,txtaboutMenu;
    public static LatLng pointStatic;
    public static boolean enabled, flagMapActivityShow, sabtShodShow,
            flagSearchLatLong, flagMontakhablatLong,flagdoubleClick,flagFragmentShow;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static Double latitudeStatic, longitudeStatic;
    public static String nameMarkerSelect;
    public static Double latMarkerSelect, longMarkerSelect;
    public static LatLng latLngCurrentUser;
    public String[] listGuids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
        myDraw = findViewById(R.id.myDraw);
        menu = findViewById(R.id.menu);
        search = findViewById(R.id.img_search);
        currentPlace = findViewById(R.id.img_currentPlace);
        txt1Menu = findViewById(R.id.txt1_menu);
        txt2Menu = findViewById(R.id.txt2_menu);
        txt3Menu = findViewById(R.id.txt3_menu);
        txt4Menu = findViewById(R.id.txt4_menu);
        txtOffMenu = findViewById(R.id.off_menu);
        flagdoubleClick=false;
        /*Menu*/
        txt1Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addMarkerMenuItem(txt1Menu.getText().toString().trim());
                    myDraw.closeDrawers();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        txt2Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addMarkerMenuItem(txt2Menu.getText().toString().trim());
                    myDraw.closeDrawers();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }            }
        });
        txt3Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addMarkerMenuItem(txt3Menu.getText().toString().trim());
                    myDraw.closeDrawers();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }}
        });
        txt4Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addMarkerMenuItem(txt4Menu.getText().toString().trim());
                    myDraw.closeDrawers();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }            }
        });
        txtOffMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.this.finish();
            }
        });
        listGuids = Database.getguidsData(getApplicationContext());
        arrayGuideToTxt(listGuids);
        /*Menu*/

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng latLngCurrent = getLocation();
                latLngCurrentUser = latLngCurrent;
                Intent intent = new Intent(MapsActivity.this, Search.class);
                startActivity(intent);
            }
        });

//
//        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
//                locationButton.getLayoutParams();
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//        layoutParams.setMargins(0, 0, 0, 200);
        currentPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flagdoubleClick == false){
                    mMap.clear();
                    LatLng ll = getLocation();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(ll.latitude, ll.longitude));
                    markerOptions.title("My Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(ll.latitude, ll.longitude)));
                    mMap.addMarker(markerOptions);
//                    mMap.setMinZoomPreference(20);
                    flagdoubleClick=true;
                }
                else {
                    mMap.clear();
                    addMarkerLoadMap();
                    flagdoubleClick=false;
                }
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDraw.openDrawer(Gravity.RIGHT);

            }
        });

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setCancelable(false).setMessage(" روشن کردن مکان نما").
                    setPositiveButton(
                            "روشن؟ ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);
                                }
                            }
                    ).setNegativeButton("انصراف!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog _alert = builder.create();
            _alert.setTitle(" ");
            _alert.setIcon(R.drawable.ic_my_location);
            _alert.show();
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        showDefaultLocation();
//        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
//        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();
//        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(10);
        addMarkerLoadMap();
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                pointStatic = point;
                latitudeStatic = point.latitude;
                longitudeStatic = point.longitude;
                double latitude, longitud;
                latitude = point.latitude;
                longitud = point.longitude;
                flagMapActivityShow = false;
                LatLng latLngCurrent = getLocation();
                String distanceLocations = getDistance(latLngCurrent, pointStatic);
                FormSabt.distanceLocationFormsabt = distanceLocations;
                if (latitude > 0 && longitud > 0) {
                    Intent intent = new Intent(MapsActivity.this, FormSabt.class);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitud);
                    startActivity(intent);
                }
            }
        });
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng latlng = new LatLng(36.565952, 53.058601);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }
//    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
//            new GoogleMap.OnMyLocationButtonClickListener() {
//                @Override
//                public boolean onMyLocationButtonClick() {
//                    mMap.setMinZoomPreference(15);
//                    return false;
//                }
//            };
//    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
//            new GoogleMap.OnMyLocationClickListener() {
//                @Override
//                public void onMyLocationClick(Location location) {
//
//                    mMap.setMinZoomPreference(12);
//
//                    CircleOptions circleOptions = new CircleOptions();
//                    circleOptions.center(new LatLng(location.getLatitude(),
//                            location.getLongitude()));
//
//                    circleOptions.radius(200);
//                    circleOptions.fillColor(Color.RED);
//                    circleOptions.strokeWidth(6);
//
//                    mMap.addCircle(circleOptions);
//                }
//            };

    public void addMarkerToMap() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(pointStatic);
        markerOptions.title(FormSabt.nameStaticPlace);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        LatLng testPos = markerOptions.getPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(pointStatic));
        mMap.addMarker(markerOptions);
    }

    public void addMarkerLoadMap() {
        ArrayList<Place> placeArrayList = new ArrayList<>();
        placeArrayList = Database.getIndex(getApplicationContext());
        for (int i = 0; i < placeArrayList.size(); i++) {
            latMarkerSelect = placeArrayList.get(i).getLatitude();
            longMarkerSelect = placeArrayList.get(i).getLongitud();
            nameMarkerSelect = placeArrayList.get(i).getPlaceName();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(latMarkerSelect, longMarkerSelect));
            markerOptions.title(nameMarkerSelect);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latMarkerSelect, longMarkerSelect)));
            mMap.addMarker(markerOptions);
        }
    }

    public void addMarkerMenuItem(String name) {
        mMap.clear();
        ArrayList<Place> placeArrayList = new ArrayList<>();
        placeArrayList = Database.getIndexGuids(name, getApplicationContext());
        for (int i = 0; i < placeArrayList.size(); i++) {
            latMarkerSelect = placeArrayList.get(i).getLatitude();
            longMarkerSelect = placeArrayList.get(i).getLongitud();
            nameMarkerSelect = placeArrayList.get(i).getPlaceName();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(latMarkerSelect, longMarkerSelect));
            markerOptions.title(nameMarkerSelect);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latMarkerSelect, longMarkerSelect)));
            mMap.addMarker(markerOptions);
        }
    }

    public void addMarkerItemSend(LatLng latLng, String name) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(latLng.latitude, latLng.longitude));
        markerOptions.title(name);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latLng.latitude, latLng.longitude)));
        mMap.addMarker(markerOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(flagFragmentShow==true){
            removeFragment(fragment);
            flagFragmentShow=false;
        }

        if (FormSabt.flagPoint == true) {
            addMarkerToMap();
            FormSabt.flagPoint = false;
        }

        if (flagSearchLatLong == true) {
            mMap.clear();
            addMarkerLoadMap();
            addMarkerItemSend(AdapterSearch.latLngDatabase, AdapterSearch.nameSelect);
            flagSearchLatLong = false;
        }
        if (flagMontakhablatLong == true) {
            mMap.clear();
            addMarkerLoadMap();
            addMarkerItemSend(AdapterMontahkhab.latLngDatabaseMontakhab, AdapterMontahkhab.nameMontakhab);
            flagMontakhablatLong = false;
        }
    }
    Moshakhasat fragment;
    @Override
    public boolean onMarkerClick(final Marker marker) {
        if(flagFragmentShow==true){
            removeFragment(fragment);
            flagFragmentShow=false;
        }
        LatLng latLngCurrent = getLocation();
        LatLng latLngDestination = marker.getPosition();
        Double lat = latLngDestination.latitude;
        Double lng = latLngDestination.longitude;
        String distanceLocations = getDistance(latLngCurrent, latLngDestination);
        //////////////////
//        https://stackoverflow.com/questions/28295199/android-how-to-show-route-between-markers-on-googlemaps
//        ArrayList<LatLng> points = null;
//        PolylineOptions lineOptions = null;
//        lineOptions = new PolylineOptions();
//        points.add(latLngCurrent);
//        points.add(latLngDestination);
//        lineOptions.addAll(points);
//        lineOptions.width(8);
//        lineOptions.color(Color.GREEN);
//        Polyline polyline = mMap.addPolyline((new PolylineOptions())
//                .clickable(true)
//                .add(new LatLng(latLngCurrent.latitude,latLngCurrent.longitude),
//                        new LatLng(lat,lng)));
        ///////////////////

        try {
            Place place = new Place();
            place = Database.getDataStorePlace(lat, getApplicationContext());
            flagMapActivityShow = true;
            fragment = Moshakhasat.newInstance(place.getPlaceName(), place.getDetails(), place.getPhoneNumber(), place.getPic(), distanceLocations);
            replaceFragment(fragment);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    public void removeFragment(Fragment fragment){
       FragmentManager fragmentManager = getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
    public void replaceFragment(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_moshakhasat, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            flagFragmentShow=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (sabtShodShow == true) {
            fragment= new Moshakhasat();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_moshakhasat, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            sabtShodShow = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public LatLng getLocation() {
        mMap.setMyLocationEnabled(true);
        Location myLocation = mMap.getMyLocation();
        LatLng myLatLng = new LatLng(myLocation.getLatitude(),
                myLocation.getLongitude());
        return myLatLng;
    }

    public String getDistance(LatLng my_latlong, LatLng frnd_latlong) {
        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        float distance = l1.distanceTo(l2);
//        String dist = distance + " M";
        String dist1 = String.format("%.02f", distance);
        String dist = dist1 + " M";
        if (distance > 1000.0f) {
            distance = distance / 1000.0f;
//            dist = distance + " KM";
            String dist2 = String.format("%.02f", distance);
            dist = dist2 + " KM";

        }
        return dist;
    }

    public void arrayGuideToTxt(String[] guid) {
        txt1Menu.setText(guid[0]);
        txt2Menu.setText(guid[1]);
        txt3Menu.setText(guid[2]);
        txt4Menu.setText(guid[3]);
    }
}
