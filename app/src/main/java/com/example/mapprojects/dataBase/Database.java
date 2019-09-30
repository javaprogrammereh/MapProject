package com.example.mapprojects.dataBase;

/*support telgram id =@javaprogrammer_eh
 * 11/05/1398
 * creted by elmira hossein zadeh*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mapprojects.model.Place;

import java.io.IOException;
import java.util.ArrayList;


public class Database {

    private static DatabaseHelper databaseHelper;

    private static final String guilds_table = "guilds";
    private static final String place_table = "place";
    public static boolean flagPlace, flagGuild;
    private static final String _latitude = "latitude";
    private static final String _longitud = "longitud";
    private static final String _NamePlace = "placename";
    private static final String _GuidsPlace = "guilds";

    public Database() {
    }

    public static SQLiteDatabase getInstance(Context context) {
        if (databaseHelper == null) {
            try {
                databaseHelper = new DatabaseHelper(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return databaseHelper.getWritableDatabase();
    }

    public static SQLiteDatabase getInstance2(Context context) {
        if (databaseHelper == null) {
            try {
                databaseHelper = new DatabaseHelper(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return databaseHelper.getReadableDatabase();
    }

    public static void addPlaceData(String placeName, Long phoneNumber, String details, byte[] pic, Double latitude, Double longitud, String guilds, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("placename", placeName);
        contentValues.put("phonenumber", phoneNumber);
        contentValues.put("details", details);
        contentValues.put("latitude", latitude);
        contentValues.put("longitud", longitud);
        contentValues.put("guilds", guilds);
        contentValues.put("image", pic);
        // Inserting Row
        Long value = getInstance(context).insert(place_table, null, contentValues);
        if (String.valueOf(value).equals("-1")) {
            flagPlace = false;
            getInstance(context).close(); // Closing database connection
        } else if (!(String.valueOf(value).equals("-1"))) {
            flagPlace = true;
        }
        getInstance(context).close(); // Closing database connection
    }

    public static void addGuildsData(String guildType, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("guildtype", guildType);
        // Inserting Row
        Long value = getInstance(context).insert(guilds_table, null, contentValues);
        if (String.valueOf(value).equals("-1")) {
            flagGuild = false;
            getInstance(context).close(); // Closing database connection
        } else if (!(String.valueOf(value).equals("-1"))) {
            flagGuild = true;
        }
        getInstance(context).close(); // Closing database connection
    }

    ////////////////////get
    public static String[] getguidsData(Context context) {
        String selectQuery = "SELECT  * FROM " + guilds_table;
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndexOrThrow("guildtype"));
                spinnerContent.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);
        return allSpinner;
    }

    public static Place getDataPlace(Context context) {
        String selectQuery = "SELECT * FROM " + place_table;
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        Place placeList = new Place();
        while (cursor.moveToNext()) {
            placeList = (new Place(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("placename")),
                    cursor.getLong(cursor.getColumnIndex("phonenumber")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longitud")),
                    cursor.getString(cursor.getColumnIndex("guilds"))));

//            Log.i("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
//            Log.i("placename", cursor.getString(cursor.getColumnIndex("placename")));
//            Log.i("phonenumber", String.valueOf(cursor.getLong(cursor.getColumnIndex("phonenumber"))));
//            Log.i("details", cursor.getString(cursor.getColumnIndex("details")));
//            Log.i("image", String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))));
//            Log.i("latitude", String.valueOf(cursor.getDouble(cursor.getColumnIndex("latitude"))));
//            Log.i("longitud", String.valueOf(cursor.getDouble(cursor.getColumnIndex("longitud"))));
//            Log.i("guilds", cursor.getString(cursor.getColumnIndex("guilds")));

        }
        cursor.close();
        getInstance2(context).close();
        return placeList;
    }

    public static Place getDataFinallyPlace(Context context) {
        String selectQuery = "SELECT * FROM " + place_table + "  ORDER BY id desc limit 1";
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        Place placeList = new Place();
        while (cursor.moveToNext()) {
            placeList = (new Place(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("placename")),
                    cursor.getLong(cursor.getColumnIndex("phonenumber")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longitud")),
                    cursor.getString(cursor.getColumnIndex("guilds"))));

//            Log.i("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
//            Log.i("placename", cursor.getString(cursor.getColumnIndex("placename")));
//            Log.i("phonenumber", String.valueOf(cursor.getLong(cursor.getColumnIndex("phonenumber"))));
//            Log.i("details", cursor.getString(cursor.getColumnIndex("details")));
//            Log.i("image", String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))));
//            Log.i("latitude", String.valueOf(cursor.getDouble(cursor.getColumnIndex("latitude"))));
//            Log.i("longitud", String.valueOf(cursor.getDouble(cursor.getColumnIndex("longitud"))));
//            Log.i("guilds", cursor.getString(cursor.getColumnIndex("guilds")));

        }
        cursor.close();
        getInstance2(context).close();
        return placeList;
    }


    public static Place getDataStorePlace(Double latitude, Context context) {
        String selectQuery = "SELECT * FROM " + place_table + " WHERE " + _latitude + " = " + latitude;
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        Place placeList = new Place();
        while (cursor.moveToNext()) {
            placeList = (new Place(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("placename")),
                    cursor.getLong(cursor.getColumnIndex("phonenumber")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longitud")),
                    cursor.getString(cursor.getColumnIndex("guilds"))));

//            Log.i("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
//            Log.i("placename", cursor.getString(cursor.getColumnIndex("placename")));
//            Log.i("phonenumber", String.valueOf(cursor.getLong(cursor.getColumnIndex("phonenumber"))));
//            Log.i("details", cursor.getString(cursor.getColumnIndex("details")));
//            Log.i("image", String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))));
//            Log.i("latitude", String.valueOf(cursor.getDouble(cursor.getColumnIndex("latitude"))));
//            Log.i("longitud", String.valueOf(cursor.getDouble(cursor.getColumnIndex("longitud"))));
//            Log.i("guilds", cursor.getString(cursor.getColumnIndex("guilds")));
        }
        cursor.close();
        getInstance2(context).close();
        return placeList;
    }

    public static ArrayList getIndex(Context context) {
        String selectQuery = "SELECT  * FROM " + place_table;
        ArrayList<Place> list = new ArrayList<Place>();
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        while (cursor.moveToNext()) {
            list.add(new Place(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("placename")),
                    cursor.getLong(cursor.getColumnIndex("phonenumber")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longitud")),
                    cursor.getString(cursor.getColumnIndex("guilds"))));

            Log.i("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            Log.i("placename", cursor.getString(cursor.getColumnIndex("placename")) );
            Log.i("phonenumber", String.valueOf(cursor.getLong(cursor.getColumnIndex("phonenumber"))));
            Log.i("details",cursor.getString(cursor.getColumnIndex("details")));
            Log.i("image", String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))));
            Log.i("latitude", String.valueOf(cursor.getDouble(cursor.getColumnIndex("latitude"))));
            Log.i("longitud", String.valueOf( cursor.getDouble(cursor.getColumnIndex("longitud"))));
            Log.i("guilds", cursor.getString(cursor.getColumnIndex("guilds")) );
        }
        cursor.close();
        getInstance2(context).close();
        return list;
    }

    public static ArrayList getPlaceListMontakhab(Context context) {
        String selectQuery = "SELECT  * FROM " + place_table + " ORDER BY id desc limit 0,5";
        ArrayList<Place> list = new ArrayList<Place>();
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        while (cursor.moveToNext()) {
            list.add(new Place(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("placename")),
                    cursor.getLong(cursor.getColumnIndex("phonenumber")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longitud")),
                    cursor.getString(cursor.getColumnIndex("guilds"))));

//            Log.i("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
//            Log.i("placename", cursor.getString(cursor.getColumnIndex("placename")));
//            Log.i("phonenumber", String.valueOf(cursor.getLong(cursor.getColumnIndex("phonenumber"))));
//            Log.i("details", cursor.getString(cursor.getColumnIndex("details")));
//            Log.i("image", String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))));
//            Log.i("latitude", String.valueOf(cursor.getDouble(cursor.getColumnIndex("latitude"))));
//            Log.i("longitud", String.valueOf(cursor.getDouble(cursor.getColumnIndex("longitud"))));
//            Log.i("guilds", cursor.getString(cursor.getColumnIndex("guilds")));
        }
        cursor.close();
        getInstance2(context).close();
        return list;
    }

    public static ArrayList getPlaceListSearch(String name, Context context) {
        String selectQuery = "SELECT  * FROM " + place_table + " WHERE " + _NamePlace + " LIKE '%" + name + "%' ";
        ArrayList<Place> list = new ArrayList<Place>();
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        while (cursor.moveToNext()) {
            list.add(new Place(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("placename")),
                    cursor.getLong(cursor.getColumnIndex("phonenumber")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longitud")),
                    cursor.getString(cursor.getColumnIndex("guilds"))));

//            Log.i("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
//            Log.i("placename", cursor.getString(cursor.getColumnIndex("placename")));
//            Log.i("phonenumber", String.valueOf(cursor.getLong(cursor.getColumnIndex("phonenumber"))));
//            Log.i("details", cursor.getString(cursor.getColumnIndex("details")));
//            Log.i("image", String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))));
//            Log.i("latitude", String.valueOf(cursor.getDouble(cursor.getColumnIndex("latitude"))));
//            Log.i("longitud", String.valueOf(cursor.getDouble(cursor.getColumnIndex("longitud"))));
//            Log.i("guilds", cursor.getString(cursor.getColumnIndex("guilds")));
        }
        cursor.close();
        getInstance2(context).close();
        return list;
    }
    public static ArrayList getIndexGuids(String guid,Context context) {
        String selectQuery = "SELECT * FROM " + place_table + " WHERE " + _GuidsPlace + " = '" + guid+"'";
        ArrayList<Place> list = new ArrayList<Place>();
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        while (cursor.moveToNext()) {
            list.add(new Place(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("placename")),
                    cursor.getLong(cursor.getColumnIndex("phonenumber")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longitud")),
                    cursor.getString(cursor.getColumnIndex("guilds"))));

            Log.i("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            Log.i("placename", cursor.getString(cursor.getColumnIndex("placename")) );
            Log.i("phonenumber", String.valueOf(cursor.getLong(cursor.getColumnIndex("phonenumber"))));
            Log.i("details",cursor.getString(cursor.getColumnIndex("details")));
            Log.i("image", String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))));
            Log.i("latitude", String.valueOf(cursor.getDouble(cursor.getColumnIndex("latitude"))));
            Log.i("longitud", String.valueOf( cursor.getDouble(cursor.getColumnIndex("longitud"))));
            Log.i("guilds", cursor.getString(cursor.getColumnIndex("guilds")) );
        }
        cursor.close();
        getInstance2(context).close();
        return list;
    }
    public static Place getDataGuidsPlace(String guid, Context context) {
        String selectQuery = "SELECT * FROM " + place_table + " WHERE " + _GuidsPlace + " = '" + guid+"'";
        Cursor cursor = getInstance2(context).rawQuery(selectQuery, null, null);
        Place placeList = new Place();
        while (cursor.moveToNext()) {
            placeList = (new Place(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("placename")),
                    cursor.getLong(cursor.getColumnIndex("phonenumber")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longitud")),
                    cursor.getString(cursor.getColumnIndex("guilds"))));

            Log.i("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            Log.i("placename", cursor.getString(cursor.getColumnIndex("placename")));
            Log.i("phonenumber", String.valueOf(cursor.getLong(cursor.getColumnIndex("phonenumber"))));
            Log.i("details", cursor.getString(cursor.getColumnIndex("details")));
            Log.i("image", String.valueOf(cursor.getBlob(cursor.getColumnIndex("image"))));
            Log.i("latitude", String.valueOf(cursor.getDouble(cursor.getColumnIndex("latitude"))));
            Log.i("longitud", String.valueOf(cursor.getDouble(cursor.getColumnIndex("longitud"))));
            Log.i("guilds", cursor.getString(cursor.getColumnIndex("guilds")));
        }
        cursor.close();
        getInstance2(context).close();
        return placeList;
    }
}