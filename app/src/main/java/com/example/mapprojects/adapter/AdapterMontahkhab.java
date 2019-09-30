package com.example.mapprojects.adapter;
/*support telgram id =@javaprogrammer_eh
 * 11/05/1398
 * creted by elmira hossein zadeh*/

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mapprojects.MapsActivity;
import com.example.mapprojects.model.Place;
import com.example.mapprojects.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class AdapterMontahkhab extends ArrayAdapter {
    public int resourceId;
    public Activity activity;
    public ArrayList<Place> data;
    public static LatLng latLngDatabaseMontakhab;
    TextView txtNamePlace, txtAddressPlace;
    public static String nameMontakhab;
    MapsActivity mapsActivity = new MapsActivity();
    public AdapterMontahkhab(Activity activity, int resourceId, ArrayList<Place> object) {
        super(activity, resourceId, object);
        this.resourceId = resourceId;
        this.activity = activity;
        this.data = object;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = this.activity.getLayoutInflater().inflate(this.resourceId, null);
        txtNamePlace = view.findViewById(R.id.title_montakhab_search);
        txtAddressPlace = view.findViewById(R.id.content_montakhab_search);
        Place place = new Place();
        place = data.get(position);
        txtNamePlace.setText(place.getPlaceName());
        txtAddressPlace.setText(place.getDetails());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.flagMontakhablatLong = true;
                MapsActivity.flagSearchLatLong = false;
                Double latItem = data.get(position).getLatitude();
                Double LongItem = data.get(position).getLongitud();
                latLngDatabaseMontakhab = new LatLng(latItem, LongItem);
                nameMontakhab = data.get(position).getPlaceName();
                activity.finish();
            }
        });
        return view;
    }
}
