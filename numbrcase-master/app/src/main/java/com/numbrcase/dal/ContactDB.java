package com.numbrcase.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.numbrcase.model.Contact;
import com.numbrcase.model.ContactImpl;
import com.numbrcase.model.SocialMedia;

import java.util.ArrayList;
import java.util.List;

public class ContactDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contact.db";

    public static final String CONTACTS_TABLE_NAME   = "contact";

    private Context context;

    public ContactDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contact  (" +
        "contact_id    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
        "name               TEXT,               " +
        "phone              TEXT,               " +
        "email              TEXT,               " +
        "request_place      TEXT,               " +
        "status             INTEGER,            " +
        "profile_pic        BLOB);              ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db);
    }


    public Contact getData(int id){
        Contact contact = new ContactImpl();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM contact WHERE contact_id = " + id + ";", null);

        c.moveToFirst();
        contact.setID            (c.getInt   (0)); // contact_id
        contact.setJob(c.getString(1)); // name
        contact.setName(c.getString(2)); // phone
        contact.setPhone(c.getString(3)); // email
        contact.setRequestPlace  (c.getString(4)); // request_place
        contact.setStatus        (c.getInt   (5)); // status
        contact.setProfilePicture(c.getBlob  (6)); // profile_pic

        SocialMediaDB smDB = new SocialMediaDB(this.context);
        contact.setSocialMedias(smDB.getSocialMediasByContactID(contact.getID()));

        return contact;

    }


    public boolean insertContact (Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name"         , contact.getName());
        contentValues.put("phone"        , contact.getPhone());
        contentValues.put("email"        , contact.getEmail());
        contentValues.put("request_place", contact.getRequestPlace());
        contentValues.put("status"       , contact.getStatus());
        contentValues.put("profile_pic"  , contact.getProfilePicture());

        int id = (int) db.insert("contact", null, contentValues);

        // Add Social Medias
        SocialMediaDB smDB = new SocialMediaDB(context);
        for (SocialMedia sm : contact.getSocialMedias()) {
            sm.setContactID(id);
            smDB.insertSocialMedia(sm);
        }

        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
    }

    public boolean updateContact (Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name"         , contact.getName());
        contentValues.put("phone"        , contact.getPhone());
        contentValues.put("email"        , contact.getEmail());
        contentValues.put("request_place", contact.getRequestPlace());
        contentValues.put("status"       , contact.getStatus());
        contentValues.put("profile_pic"  , contact.getProfilePicture());

        db.update("contact", contentValues, "contact_id = ? ", new String[] { Integer.toString(contact.getID()) } );
        return true;
    }


    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("contact", "contact_id = ? ", new String[] { Integer.toString(id) });

        SocialMediaDB smDB = new SocialMediaDB(context);
        smDB.deleteSocialMediaByContactID(id);

        return result;
    }


    public List<Contact> getAllContactsByStatus(int status) {

        List<Contact> contacts = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "select * from contact where status = " + status + " order by name"
                +" collate nocase;", null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            Contact contact = new ContactImpl();

            contact.setID            (res.getInt   (0)); // contact_id
            contact.setJob(res.getString(1)); // name
            contact.setName(res.getString(2)); // phone
            contact.setPhone(res.getString(3)); // email
            contact.setRequestPlace  (res.getString(4)); // request_place
            contact.setStatus        (res.getInt   (5)); // status
            contact.setProfilePicture(res.getBlob  (6)); // profile_pic

            SocialMediaDB smDB = new SocialMediaDB(this.context);
            contact.setSocialMedias(smDB.getSocialMediasByContactID(contact.getID()));

            contacts.add(contact);
            res.moveToNext();
        }

        return contacts;
    }

}
