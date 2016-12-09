package com.numbrcase.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.numbrcase.common.SocialMediaIDs;
import com.test_2.R;

import java.util.List;

/**
 * Adapter that will manipulate the content of a Listview
 */
public class MediaArrayAdapter extends ArrayAdapter<SocialMedia> {

    private final Context context;
    private final List<SocialMedia> values;

    private int layout;

    public MediaArrayAdapter(Context context, List<SocialMedia> values, int layout) {
        super(context, -1, values);
        this.context = context;

        this.values = values;
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout, parent, false);;

        SocialMedia sm = values.get(position);

        switch (layout) {

            case R.layout.row_media:
                ((TextView) rowView.findViewById(R.id.media_name)).setText(SocialMediaIDs.getName(sm.getMediaID()));
                ((TextView) rowView.findViewById(R.id.media_ID))  .setText(sm.getUserID());
                ((ImageView) rowView.findViewById(R.id.icon))     .setImageResource(sm.getMediaID());
                break;

            case R.layout.row_add_media:
                ((ImageView) rowView.findViewById(R.id.social_media_icon)).setImageResource(values.get(position).getMediaID());
                ((TextView) rowView.findViewById(R.id.user_id)).setText(sm.getUserID());
                break;
        }

        return rowView;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }

}
