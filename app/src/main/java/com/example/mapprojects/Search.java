package com.example.mapprojects;
/*support telgram id =@javaprogrammer_eh
 * 11/05/1398
 * creted by elmira hossein zadeh*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mapprojects.adapter.AdapterMontahkhab;
import com.example.mapprojects.adapter.AdapterSearch;
import com.example.mapprojects.dataBase.Database;
import com.example.mapprojects.model.Place;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Search extends AppCompatActivity {

    EditText edtWriteNamePlace;
    TextView txtShowText;
    ImageView imageViewClose, imageViewBack;
    ListView listViewSearch;
    ArrayList<Place> placeArrayList1, placeArrayList2;
    AdapterSearch searchAdapter;
    AdapterMontahkhab adapterMontahkhab;
    public static String edtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listViewSearch = findViewById(R.id.listView_search);
        imageViewBack = findViewById(R.id.back);
        edtWriteNamePlace = findViewById(R.id.edt_name_search);
        txtShowText = findViewById(R.id.result_search);
        imageViewClose = findViewById(R.id.clear);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtWriteNamePlace.setText("");
            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search.this.finish();
            }
        });

        edtWriteNamePlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String text = edtWriteNamePlace.getText().toString().trim();
                if (text.length() >= 3) {
                    edtValue=text;
                    setItemListSearch();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = edtWriteNamePlace.getText().toString().trim();
                if(text.equals("")){
                    setItemListMontakhab();
                }
            }
        });
        setItemListMontakhab();
    }

    //======================change font===============================
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void setItemListMontakhab() {

        placeArrayList1 = new ArrayList<>();
        placeArrayList1 = Database.getPlaceListMontakhab(getApplicationContext());
        adapterMontahkhab = new AdapterMontahkhab(Search.this, R.layout.item_montakhab, placeArrayList1);
        listViewSearch.setAdapter(adapterMontahkhab);
        txtShowText.setText("آدرس های منتخب");
    }

    public void setItemListSearch() {
        placeArrayList2 = new ArrayList<>();
        placeArrayList2 = Database.getPlaceListSearch(edtValue, getApplicationContext());
        searchAdapter = new AdapterSearch(Search.this, R.layout.item_search, placeArrayList2);
        listViewSearch.setAdapter(searchAdapter);
        txtShowText.setText("نتایج جستجو");
    }


}
