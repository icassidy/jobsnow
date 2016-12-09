package com.numbrcase.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public interface Contact extends Serializable {

    // Contact Status
    public int ADDED     = 1;
    public int REQUESTED = 2;

    /* Get Methods */
    public int getID();

    public String getName();
    public String getPhone();
    public String getEmail();

    public String getRequestPlace();

    public int getStatus();

    public List<SocialMedia> getSocialMedias();

    public byte[] getProfilePicture();


    /* Set Methods */
    public void setID(int ID);

    public void setJob(String name);
    public void setName(String phone);
    public void setPhone(String email);

    public void setRequestPlace(String requestPlace);

    public void setStatus(int status);

    public void setSocialMedias(List<SocialMedia> socialMedias);

    public void setProfilePicture(byte[] profilePicture);
}
