package com.numbrcase.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.numbrcase.model.SocialMedia;
import com.numbrcase.model.SocialMediaImpl;

import java.util.ArrayList;
import java.util.List;

public class SocialMediaDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "social_media.db";

    public SocialMediaDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE social_media ("            +
        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
        "social_media_id          INTEGER                 ," +
        "user_id                  TEXT                    ," +
        "contact_id               INTEGER                 ," +
        "FOREIGN KEY (contact_id) REFERENCES contact (contact_id));");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS social_media");
        onCreate(db);
    }


    public Cursor getData(int id){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM social_media WHERE id = " + id + ";", null);
    }


    public List<SocialMedia> getSocialMediasByContactID(int contactID){
        List<SocialMedia> socialMedias = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM social_media WHERE contact_id = " + contactID, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            SocialMedia sm = new SocialMediaImpl();

            sm.setID       (res.getInt   (0)); // id
            sm.setMediaID  (res.getInt   (1)); // social_media_id
            sm.setUserID   (res.getString(2)); // user_id
            sm.setContactID(res.getInt   (3)); // contact_id

            socialMedias.add(sm);
            res.moveToNext();
        }

        return socialMedias;
    }

    public boolean insertSocialMedia (SocialMedia socialMedia) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("social_media_id", socialMedia.getMediaID());
        contentValues.put("user_id"        , socialMedia.getUserID());
        contentValues.put("contact_id"     , socialMedia.getContactID());

        long res = db.insert("social_media", null, contentValues);
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, "social_media");
    }

    public boolean updateSocialMedia (SocialMedia socialMedia) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id"     , socialMedia.getUserID());

        int result = db.update("social_media", contentValues, "id = ? ", new String[] { Integer.toString(socialMedia.getID()) } );
        return result == 1;
    }


    public Integer deleteSocialMedia(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("social_media", "id = ? ", new String[] { Integer.toString(id) });
    }

    public Integer deleteSocialMediaByContactID(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("social_media", "contact_id = ? ", new String[] { Integer.toString(id) });
    }

    public List<String> getAllSocialMedias() {

        List<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "select * from social_media", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("id")));
            res.moveToNext();
        }

        return array_list;
    }

}
