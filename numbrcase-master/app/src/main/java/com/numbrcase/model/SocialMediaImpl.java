package com.numbrcase.model;

import com.numbrcase.common.SocialMediaIDs;

public class SocialMediaImpl implements SocialMedia {

    private int id;

    private int socialMediaID;

    private String link;
    private String userID;

    // foreign key
    private int contactID;

    public SocialMediaImpl() {

    }

    public SocialMediaImpl(int socialMediaID) {
        this.socialMediaID = socialMediaID;
    }

    public SocialMediaImpl(int socialMediaID, String userID) {
        this.socialMediaID = socialMediaID;
        this.userID = userID;
        this.link = SocialMediaIDs.getLink(socialMediaID, userID);
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }


    public int getMediaID() {
        return socialMediaID;
    }

    @Override
    public void setMediaID(int socialMediaID) {
        this.socialMediaID = socialMediaID;
    }


    public String getUserID() {
        return userID;
    }

    public String getLink() {
        if (link == null)
            link = SocialMediaIDs.getLink(socialMediaID, userID);

        return link;
    }

    public void setSocialMediaID(int socialMediaID) {
        this.socialMediaID = socialMediaID;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int getContactID() {
        return contactID;
    }

    @Override
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
