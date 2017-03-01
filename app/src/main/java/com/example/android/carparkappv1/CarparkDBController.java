package com.example.android.carparkappv1;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import MapProjectionConverter.SVY21Coordinate;

public class CarparkDBController extends SQLiteOpenHelper {

    private static final String TAG = "CarparkDBControllerClass";

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Carpark.db";
    public static final String TABLE_CARPARKS = "Carparks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CARPARKNUM = "Carpark_num";
    public static final String COLUMN_address = "Address";
    public static final String COLUMN_Xcoord = "X_Coord";
    public static final String COLUMN_Ycoord = "Y_Coord";
    public static final String COLUMN_CPTYPE = "Carpark_type";
    public static final String COLUMN_TYPE_PARKING_SYS = "Type_Of_Parking_System";
    public static final String COLUMN_STP = "short_term_parking";
    public static final String COLUMN_FP = "free_parking";
    public static final String COLUMN_NP ="night_parking";

    private Context context;

    public CarparkDBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * This method creates database if no database exists.
     * Table for hdb carparks created
     * CSV file containing hdb information is populated into table TABLE_CARPARK
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate database!");
        String query = "CREATE TABLE " + TABLE_CARPARKS + " ("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_CARPARKNUM + " TEXT, "
                        + COLUMN_address + " TEXT, "
                        + COLUMN_Xcoord + " DOUBLE, "
                        + COLUMN_Ycoord + " DOUBLE, "
                        + COLUMN_CPTYPE + " TEXT, "
                        + COLUMN_TYPE_PARKING_SYS + " TEXT, "
                        + COLUMN_STP + " TEXT, "
                        + COLUMN_FP + " TEXT, "
                        + COLUMN_NP + " TEXT "
                        + ");";

        sqLiteDatabase.execSQL(query);
        Log.i(TAG, "created table_carparks");
        try {
            addCSVintoDB(sqLiteDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i(TAG, "onOpen database!");

    }

    //load values from csv file.
    public void addCSVintoDB(SQLiteDatabase db) throws IOException {
        Log.i(TAG,  "addCSVintoDB method");
        String filename = "hdb-carpark-information.csv";
        AssetManager am = context.getAssets();
        InputStream inputStream = null;
        inputStream = am.open(filename);
        String line = "";
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

        //INSERT INTO Carparks (COLUMN_CARPARKNUM, COLUMN_Xcoord, COLUMN_Ycoord) VALUES (... ... ...)
        while((line = buffer.readLine()) != null){
            Log.i(TAG, "inside while loop");
            String [] columns = line.split(",");
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CARPARKNUM, columns[0]);
            cv.put(COLUMN_address, columns[1]);
            cv.put(COLUMN_Xcoord, columns[2]);
            cv.put(COLUMN_Ycoord, columns[3]);
            cv.put(COLUMN_CPTYPE, columns[4]);
            cv.put(COLUMN_TYPE_PARKING_SYS, columns[5]);
            cv.put(COLUMN_STP, columns[6]);
            cv.put(COLUMN_FP, columns[7]);
            cv.put(COLUMN_NP, columns[8]);
            db.insert(TABLE_CARPARKS, null, cv);
        }
    }


    public String dbToString(){
        Log.i(TAG, "enter dbToString");
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CARPARKS + " WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()!=0){
            dbString += c.getString(c.getColumnIndex(COLUMN_CARPARKNUM));
            dbString += c.getString(c.getColumnIndex(COLUMN_address));
            dbString += c.getString(c.getColumnIndex(COLUMN_Xcoord));
            dbString += c.getString(c.getColumnIndex(COLUMN_Ycoord));
        }
        db.close();
        c.close();
        Log.i(TAG, "dbString: " + dbString);
        return dbString;

    }


    //This method queries the database to get carparks with coordinates within vicinity of destination
    public ArrayList<Cursor> queryRetrieveNearbyCarparks(SVY21Coordinate svy21C){
        double easting = svy21C.getEasting();
        double northing = svy21C.getNorthing();
        ArrayList<Cursor> cpListInfo = new ArrayList<Cursor>();

        return cpListInfo;
    }


}
