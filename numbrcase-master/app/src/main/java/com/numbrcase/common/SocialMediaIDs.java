package com.numbrcase.common;

import com.test_2.R;

import java.util.ArrayList;
import java.util.List;

public class SocialMediaIDs {

    public static final int FACEBOOK    = R.drawable.my_ic_facebook;
    public static final int INSTAGRAM   = R.drawable.my_ic_instagram;
    public static final int LINKEDIN    = R.drawable.my_ic_linkedin;
    public static final int TWITTER     = R.drawable.my_ic_twitter;


    public static String getName(int id) {
        if (FACEBOOK    == id)return "Facebook";
        if (INSTAGRAM   == id)return "Instagram";
        if (LINKEDIN    == id)return "LinkedIn";
        if (TWITTER     == id)return "Twitter";

        return null;
    }

    public static String getLink(int id, String userID) {

        String link = "https://www.";

        if (FACEBOOK    == id)return link + "facebook.com/"    + userID;
        if (INSTAGRAM   == id)return link + "instagram.com/"   + userID;
        if (LINKEDIN    == id)return link + "linkedin.com/in/" + userID;
        if (TWITTER     == id)return link + "twitter.com/"     + userID;

        return null;
    }

    public static List<Integer> getSocialMediaIDs() {
        List<Integer> mediaIDs = new ArrayList();
        mediaIDs.add(FACEBOOK);
        mediaIDs.add(INSTAGRAM);
        mediaIDs.add(LINKEDIN);
        mediaIDs.add(TWITTER);

        return mediaIDs;
    }

}
