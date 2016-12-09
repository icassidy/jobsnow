package com.numbrcase.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.numbrcase.model.Contact;
import com.numbrcase.model.MediaArrayAdapter;
import com.numbrcase.model.SocialMedia;
import com.numbrcase.model.SocialMediaImpl;
import com.test_2.R;

import java.util.List;

public class MyAccountActivity extends AppCompatActivity {

    private Contact myself;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        myself = (Contact) getIntent().getSerializableExtra("contact");

        showContactInformation(myself);
        showMediasInformation(myself.getSocialMedias());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_account, menu);
        return true;
    }

    private void showContactInformation(Contact contact) {
        ((TextView)findViewById(R.id.job_name)).setText(contact.getName());
        ((TextView)findViewById(R.id.contact_name)).setText(contact.getPhone());
        ((TextView)findViewById(R.id.phone)).setText(contact.getEmail());

        byte[] blob = contact.getProfilePicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        ((ImageView)findViewById(R.id.profile_pic)).setImageBitmap(bitmap);
    }

    private void showMediasInformation(List<SocialMedia> socialMedias) {

        final ListView listview = (ListView) findViewById(R.id.profilelistview);

        MediaArrayAdapter adapter = new MediaArrayAdapter(this, socialMedias, R.layout.row_media);
        listview.setAdapter(adapter);
        updateListViewSize(listview);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                SocialMediaImpl socialMedia = ((SocialMediaImpl) listview.getAdapter().getItem(position));

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialMedia.getLink()));
                startActivity(browserIntent);
            }
        });

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
        listview.setFocusable(false);
    }

    /**
     * Method called whenever the button "Edit" is pressed
     */
    public void editContact(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), EditMyAccountActivity.class);
        intent.putExtra("contact", myself);
        startActivity(intent);
    }

}
