package com.numbrcase.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.numbrcase.dal.ContactDB;
import com.numbrcase.model.Contact;
import com.numbrcase.model.MediaArrayAdapter;
import com.numbrcase.model.SocialMedia;
import com.numbrcase.model.SocialMediaImpl;
import com.test_2.R;

import java.util.List;

public class AcceptRequestActivity extends AppCompatActivity {

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        List<SocialMedia> socialMedias = contact.getSocialMedias();

        showContactInformation(contact);
        showMediasInformation(socialMedias);
    }

    private void showContactInformation(Contact contact) {
        ((TextView)findViewById(R.id.job_name)).setText(contact.getName());
        ((TextView)findViewById(R.id.contact_name)).setText(contact.getPhone());

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
     * Method called whenever the button "Accept" is pressed
     */
    public void acceptRequest(View view) {
        ContactDB contactDB = new ContactDB(this);

        contact.setStatus(Contact.ADDED);
        contactDB.updateContact(contact);

        Toast.makeText(this, "Contact Accepted", Toast.LENGTH_SHORT).show();
        this.onBackPressed(); //Back to MainActivity
    }

    /**
     * Method called whenever the button "Deny" is pressed
     */
    public void denyRequest(View view) {
        ContactDB contactDB = new ContactDB(this);
        contactDB.deleteContact(contact.getID());

        Toast.makeText(this, "Contact Denied", Toast.LENGTH_SHORT).show();
        this.onBackPressed(); //Back to MainActivity
    }


}
