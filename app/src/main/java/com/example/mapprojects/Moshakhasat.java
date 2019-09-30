package com.example.mapprojects;
/*support telgram id =@javaprogrammer_eh
 * 11/05/1398
 * creted by elmira hossein zadeh*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mapprojects.dataBase.Database;
import com.example.mapprojects.model.Place;

import java.io.ByteArrayInputStream;

public class Moshakhasat extends Fragment {
    TextView txtNamePlaceM, txtPhoneNumberM, txtDetailM, txtSpaceM;
    ImageView imgViewMoshakhasat,imgCloseFragment;
    public String NAME, DETAIL,SPACE;
    private Long PHONE;
    private byte[] IMG;
    public static String spaceFragmentMashkhasat;
    private static final String ARG_PARAM1 = "placenameClick";
    private static final String ARG_PARAM2 = "detailsClick";
    private static final String ARG_PARAM3 = "phonenumberClick";
    private static final String ARG_PARAM4 = "imageClick";
    private static final String ARG_PARAM5 = "spaceClick";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moshakhasat_frg, container, false);

    }

    public static Moshakhasat newInstance(String name, String detail, Long phone, byte[] img,String space) {
        Moshakhasat fragment = new Moshakhasat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, detail);
        args.putLong(ARG_PARAM3, phone);
        args.putByteArray(ARG_PARAM4, img);
        args.putString(ARG_PARAM5, space);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNamePlaceM = view.findViewById(R.id.name_moshakasat);
        txtPhoneNumberM = view.findViewById(R.id.phone_moshakasat);
        txtDetailM = view.findViewById(R.id.bio_moshakasat);
        txtSpaceM = view.findViewById(R.id.space_moshakasat);
        imgViewMoshakhasat = view.findViewById(R.id.image_moshakasat);
        imgCloseFragment=view.findViewById(R.id.close_frg);

        imgCloseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        if (FormSabt.flagMoshakhasatShow == true) {
            showmoshakhasat();
            FormSabt.flagMoshakhasatShow = false;
        }


        if (MapsActivity.flagMapActivityShow == true) {
            showMoshakhsatStorePlace();
            MapsActivity.flagMapActivityShow = false;
        }
    }

    public void showmoshakhasat() {
        Place place = new Place();
        place = Database.getDataFinallyPlace(getContext());
        txtNamePlaceM.setText(place.getPlaceName());
        txtPhoneNumberM.setText(String.valueOf(place.getPhoneNumber()));
        txtDetailM.setText(place.getDetails());
        txtSpaceM.setText(spaceFragmentMashkhasat);
        byte[] data = place.getPic();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(data);
        Bitmap recource = BitmapFactory.decodeStream(imageStream);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(),
                Bitmap.createScaledBitmap(recource,50,50,false));
        drawable.setCircular(true);
        imgViewMoshakhasat.setImageDrawable(drawable);
    }


    public void showMoshakhsatStorePlace() {

        if (getArguments() != null) {
            NAME = getArguments().getString(ARG_PARAM1);
            DETAIL = getArguments().getString(ARG_PARAM2);
            PHONE = getArguments().getLong(ARG_PARAM3);
            IMG = getArguments().getByteArray(ARG_PARAM4);
            SPACE=getArguments().getString(ARG_PARAM5);
            txtNamePlaceM.setText(NAME);
            txtDetailM.setText(DETAIL);
            txtPhoneNumberM.setText(PHONE + "");
            txtSpaceM.setText(SPACE);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(IMG);
            Bitmap recource = BitmapFactory.decodeStream(imageStream);
            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(),
                    Bitmap.createScaledBitmap(recource,50,50,false));
            drawable.setCircular(true);
            imgViewMoshakhasat.setImageDrawable(drawable);
        }
    }


}
