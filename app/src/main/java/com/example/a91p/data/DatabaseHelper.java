package com.example.a91p.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a91p.model.Advert;
import com.example.a91p.util.Util;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ADVERT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.NAME + " TEXT , " + Util.PHONE + " TEXT , " + Util.DESCRIPTION + " TEXT , " + Util.DATE + " TEXT , "
                + Util.LOCATION + " TEXT , " + Util.POST_TYPE + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_ADVERT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS" ;
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);
    }

    public long InsertAdvert (Advert advert)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NAME, advert.getAdvertName());
        contentValues.put(Util.PHONE, advert.getAdvertPhone());
        contentValues.put(Util.DESCRIPTION, advert.getAdvertDescription());
        contentValues.put(Util.DATE, advert.getAdvertDate());
        contentValues.put(Util.LOCATION, advert.getAdvertLocation());
        contentValues.put(Util.POST_TYPE, advert.getAdvertPostType());

        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public boolean fetchAdvert(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.ID}, Util.NAME + "=?", new String[]{name}, null, null, null);

        int numberOfRows = cursor.getCount();

        db.close();

        if(numberOfRows > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public ArrayList<Advert> GetAdverts(){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Advert> adverts = new ArrayList<Advert>();
        while(cursor.moveToNext()){
            Advert advert = new Advert(
                    cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),cursor.getString(6)
            );
            adverts.add(advert);
        }
        cursor.close();
        db.close();
        return adverts;
    }

    public void DeleteAdvert(int advertId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.ID+" = ?",new String[]{String.valueOf(advertId)});
        db.close();
    }
}
