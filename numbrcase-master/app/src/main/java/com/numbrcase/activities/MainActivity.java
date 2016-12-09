package com.numbrcase.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.numbrcase.common.SocialMediaIDs;
import com.numbrcase.dal.ContactDB;
import com.numbrcase.model.Contact;
import com.numbrcase.model.ContactImpl;
import com.numbrcase.model.ContactArrayAdapter;
import com.numbrcase.model.SocialMedia;
import com.numbrcase.model.SocialMediaImpl;
import com.test_2.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView contactLV;
    private ListView requestLV;


    private final int MY_READ_PHONE_STATE = 1;
    private final int MY_BLUETOOTH_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Add and Search Nearby buttons
        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.add)
                    startActivity(new Intent(getApplicationContext(), AddContactActivity.class));

                else if (menuItem.getItemId() == R.id.search_nearby)
                    startActivity(new Intent(getApplicationContext(), SearchNearbyActivity.class));

                return false;
            }
        });

        insertMyAccount();
    }

    @Override
    protected void onStart() {
        super.onStart();

        configureRequestListView();
        configureContactListView();

    }

    /** Insert my own contact into the database */
    private void insertMyAccount() {
        ContactDB contactDB = new ContactDB(this);
        if (contactDB.numberOfRows() == 0)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_READ_PHONE_STATE);
    }

    /** Contacts inserted to test the application */
    private void insertContactsForTesting() {
        ContactDB contactDB = new ContactDB(this);

        // ********* REQUESTS ********* //
        List<SocialMedia> reqSMedias = new ArrayList<>();
        reqSMedias.add(new SocialMediaImpl(SocialMediaIDs.FACEBOOK , "faceID"));
        reqSMedias.add(new SocialMediaImpl(SocialMediaIDs.INSTAGRAM, "instaID"));
        reqSMedias.add(new SocialMediaImpl(SocialMediaIDs.LINKEDIN , "linkID"));
        reqSMedias.add(new SocialMediaImpl(SocialMediaIDs.TWITTER  , "twitterID"));

        List<Contact> reqContacts = new ArrayList<>();
        reqContacts.add(new ContactImpl("Bill Gates", "Requested in Roger Parks, IL on 10/1/2015", Contact.REQUESTED, reqSMedias, "+1 773 987 1921", getProfilePic(R.drawable.pp_2)));
        reqContacts.add(new ContactImpl("Muhammad Ali", "Requested in Roger Parks, IL on 10/1/2015", Contact.REQUESTED, reqSMedias, "+1 773 987 1922", getProfilePic(R.drawable.pp_3)));
        reqContacts.add(new ContactImpl("Charles Darwin", "Requested in Roger Parks, IL on 10/1/2015", Contact.REQUESTED, reqSMedias, "+1 773 987 1923", getProfilePic(R.drawable.pp_4)));
        reqContacts.add(new ContactImpl("Elvis Presley", "Requested in Roger Parks, IL on 10/1/2015", Contact.REQUESTED, reqSMedias, "+1 773 987 1924", getProfilePic(R.drawable.pp_5)));

        for (Contact c : reqContacts)
            contactDB.insertContact(c);

        // ********* CONTACTS ********* //

        List<SocialMedia> contSMedias1 = new ArrayList<>();
        contSMedias1.add(new SocialMediaImpl(SocialMediaIDs.TWITTER  , "twitterID"));
        contSMedias1.add(new SocialMediaImpl(SocialMediaIDs.FACEBOOK , "faceID"));
        contSMedias1.add(new SocialMediaImpl(SocialMediaIDs.INSTAGRAM, "instaID"));
        contSMedias1.add(new SocialMediaImpl(SocialMediaIDs.LINKEDIN , "jpcqseventos"));

        List<SocialMedia> contSMedias2 = new ArrayList<>();
        //contSMedias2.add(new SocialMediaImpl(SocialMediaIDs.FACEBOOK , "faceID"));
        //contSMedias2.add(new SocialMediaImpl(SocialMediaIDs.INSTAGRAM, "instaID"));

        List<SocialMedia> contSMedias3 = new ArrayList<>();
        //contSMedias3.add(new SocialMediaImpl(SocialMediaIDs.FACEBOOK , "faceID"));

        List<SocialMedia> contSMedias4 = new ArrayList<>();
        //contSMedias4.add(new SocialMediaImpl(SocialMediaIDs.INSTAGRAM, "instaID"));
        //contSMedias4.add(new SocialMediaImpl(SocialMediaIDs.TWITTER  , "gkthiruvathukal"));
        //contSMedias4.add(new SocialMediaImpl(SocialMediaIDs.FACEBOOK , "faceID"));

        List<Contact> myContacts = new ArrayList<>();
        myContacts.add(new ContactImpl("George Thiruvathukal", "Requested in Roger Parks, IL on 10/1/2015", Contact.ADDED, contSMedias4, "+1 773 987 1921", getProfilePic(R.drawable.pp_6)));
        myContacts.add(new ContactImpl("Albert Einstein", "Requested in Roger Parks, IL on 10/1/2015", Contact.ADDED, contSMedias4, "+1 773 987 1922", getProfilePic(R.drawable.pp_7)));
        myContacts.add(new ContactImpl("Paul McCartney", "Requested in Roger Parks, IL on 10/1/2015", Contact.ADDED, contSMedias2, "+1 773 987 1923", getProfilePic(R.drawable.pp_8)));
        myContacts.add(new ContactImpl("Leonardo da Vinci", "Requested in Roger Parks, IL on 10/1/2015", Contact.ADDED, contSMedias3, "+1 773 987 1924", getProfilePic(R.drawable.pp_9)));
        myContacts.add(new ContactImpl("Dalai Lama", "Requested in Roger Parks, IL on 10/1/2015", Contact.ADDED, contSMedias1, "+1 773 987 1925", getProfilePic(R.drawable.pp_10)));

        for (Contact c : myContacts)
            contactDB.insertContact(c);
    }

    private void configureRequestListView() {
        ContactDB contactDB = new ContactDB(this);

        List<Contact> requests = contactDB.getAllContactsByStatus(Contact.REQUESTED);
        ContactArrayAdapter adapter = new ContactArrayAdapter(this, requests, R.layout.row_request);

        requestLV = (ListView) findViewById(R.id.requestlistview);
        requestLV.setAdapter(adapter);
        requestLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                Contact contact = ((Contact) requestLV.getAdapter().getItem(position));

                Intent intent = new Intent(getApplicationContext(), AcceptRequestActivity.class);
                intent.putExtra("contact", contact);

                startActivity(intent);
            }
        });

        updateListViewSize(requestLV);
    }


    private void configureContactListView() {
        ContactDB contactDB = new ContactDB(this);

        List<Contact> myContacts = contactDB.getAllContactsByStatus(Contact.ADDED);

        // Remove the User Account
        for (Contact c : myContacts) {
            if (c.getID() == 1) {
                myContacts.remove(c);
                break;
            }
        }

        ContactArrayAdapter adapter = new ContactArrayAdapter(this, myContacts, R.layout.row_contact);

        contactLV = (ListView) findViewById(R.id.contactlistview);
        contactLV.setAdapter(adapter);
        contactLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                ContactImpl contact = ((ContactImpl) contactLV.getAdapter().getItem(position));

                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                intent.putExtra("contact", contact);

                startActivity(intent);
            }
        });

        updateListViewSize(contactLV);
    }

    /**
     * Method required to expand a ListView since Android do not support a ListView inside
     * a ScrollView
     */
    private void updateListViewSize(ListView listview) {
        int totalHeight = 0;
        for (int i = 0; i < listview.getAdapter().getCount(); i++) {
            View listItem = listview.getAdapter().getView(i, null, listview);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight + (listview.getDividerHeight() * (listview.getCount() - 1));
        listview.setLayoutParams(params);
        listview.requestLayout();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SwitchCompat toggleBluetooth = (SwitchCompat) menu.findItem(R.id.menu_bluetooth).getActionView();
        toggleBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bluetoothOnOFF();;
            }
        });

        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
            Intent intent = new Intent(getApplicationContext(), MyAccountActivity.class);
            Contact myself = new ContactDB(this).getData(1); // My account is the first row in the db

            intent.putExtra("contact", myself);
            startActivity(intent);
        } else if (id == R.id.settings) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        boolean permission = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        switch (requestCode) {

            case MY_READ_PHONE_STATE: {
                addContacts(permission);
                return;
            }

            case MY_BLUETOOTH_PERMISSION: {
                updateBluetoothToggleButton(permission);
                return;
            }

        }
    }

    private void addContacts(boolean permission){
        ContactDB contactDB = new ContactDB(this);
        Contact myself = new ContactImpl();

        if (permission) {
            TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            myself.setName(tMgr.getLine1Number());
        } else {
            myself.setName("--");
        }


        myself.setProfilePicture(getProfilePic(R.drawable.pp_1));

        contactDB.insertContact(myself);
        insertContactsForTesting();

        // Update view
        onStart();
    }

    // FIXME: For some reason, bluetooth is not requiring user permission to work
    public void bluetoothOnOFF() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH}, MY_BLUETOOTH_PERMISSION);
        else
            updateBluetoothToggleButton(true);

    }


    private void updateBluetoothToggleButton(boolean permission) {
        SwitchCompat toggle = (SwitchCompat) findViewById(R.id.menu_bluetooth);

        if (!permission) {
            Toast.makeText(this, "Permission not provided", Toast.LENGTH_SHORT).show();
            return;
        }

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (toggle.isChecked()) {
            if (bluetoothAdapter != null)
                bluetoothAdapter.enable();
            Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
        }
        else {
            if (bluetoothAdapter != null)
                bluetoothAdapter.disable();
            Toast.makeText(this, "Bluetooth disabled", Toast.LENGTH_SHORT).show();
        }

    }

    private byte[] getProfilePic(int drawableID) {
        Bitmap image = BitmapFactory.decodeResource(getResources(),
                drawableID);

        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.WEBP, 50, stream);

        return stream.toByteArray();
    }

}
