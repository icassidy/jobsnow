package com.numbrcase.model;

import java.io.Serializable;

public interface SocialMedia extends Serializable {

    public int getID();
    public void setID(int ID);

    public int getMediaID();
    public void setMediaID(int socialMediaID);

    public String getUserID();
    public void setUserID(String userID);

    public int getContactID();
    public void setContactID(int contactID);

}
