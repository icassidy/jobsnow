package com.numbrcase.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.R.*;

import com.numbrcase.common.SocialMediaIDs;
import com.test_2.R;

import java.util.List;

/**
 * Adapter that will manipulate the content of a Listview
 */
public class ContactArrayAdapter extends ArrayAdapter<Contact> {

    private final Context context;
    private final List<Contact> values;

    private int layout;

    public ContactArrayAdapter(Context context, List<Contact> values, int layout) {
        super(context, -1, values);
        this.context = context;

        this.values = values;
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout, parent, false);

        byte[] blob = values.get(position).getProfilePicture();
        if (blob != null)
            ((ImageView) rowView.findViewById(R.id.icon)).setImageBitmap(BitmapFactory.decodeByteArray(blob, 0, blob.length));

        switch (layout) {

            case R.layout.row_contact:
                ((TextView)  rowView.findViewById(R.id.job_name)).setText(values.get(position).getName());
                addSocialMediaViews(rowView, position);
                break;

            case R.layout.row_request:
                ((TextView) rowView.findViewById(R.id.request_name)).setText(values.get(position).getName());
                break;

        }

        return rowView;
    }

    private void addSocialMediaViews(View rowView, int position) {
        int size = values.get(position).getSocialMedias().size();
        ImageView imageView;

        for (int i = 0; i < size; i++) {

            // More than 3 social media will present the "+" icon
            if (i > 2) {
                imageView = (ImageView) rowView.findViewById(R.id.social_img_2);
                imageView.setImageResource(drawable.ic_menu_add);
                break;
            }

            int viewID = rowView.getResources().getIdentifier("social_img_" + i, "id", context.getPackageName());
            imageView = (ImageView) rowView.findViewById(viewID);

            String drawableName = "circle_" + SocialMediaIDs.getName(values.get(position).getSocialMedias().get(i).getMediaID()).toLowerCase();
            int drawableID = rowView.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
            imageView.setImageResource(drawableID);

        }


    }
}
