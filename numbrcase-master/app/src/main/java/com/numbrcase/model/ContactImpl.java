package com.numbrcase.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.numbrcase.common.SocialMediaIDs;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactImpl implements Contact {

    private int contactID;

    private String name;
    private String phone;
    private String email;

    private String requestPlace;

    // status that indicate added or requested
    private int status;

    private List<SocialMedia> socialMedias = new ArrayList<>();

    private byte[] profilePicture;

    /*
     * Default values for a contact
     */
    public ContactImpl() {
        this.name = "User";
        this.phone = "--";
        this.email = "--";
        this.status = Contact.ADDED;

        this.socialMedias.add(new SocialMediaImpl(SocialMediaIDs.INSTAGRAM, "instaID"));
        this.socialMedias.add(new SocialMediaImpl(SocialMediaIDs.FACEBOOK , "faceID" ));
    }

    public ContactImpl(String name) {
        this.name = name;
    }

    public ContactImpl(String name, String requestPlace) {
        this.name = name;
        this.requestPlace = requestPlace;
    }

    public ContactImpl(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public ContactImpl(String name, String requestPlace, int status) {
        this.name = name;
        this.requestPlace = requestPlace;
        this.status = status;
    }

    public ContactImpl(String name, String requestPlace, int status, List<SocialMedia> socialMedias) {
        this.name = name;
        this.requestPlace = requestPlace;
        this.status = status;
        this.socialMedias = socialMedias;
    }

    public ContactImpl(String name, String requestPlace, int status, List<SocialMedia> socialMedias, String phone) {
        this.name = name;
        this.requestPlace = requestPlace;
        this.status = status;
        this.socialMedias = socialMedias;
        this.phone = phone;
    }

    public ContactImpl(String name, String requestPlace, int status, List<SocialMedia> socialMedias, String phone, byte[] profilePicture) {
        this.name = name;
        this.requestPlace = requestPlace;
        this.status = status;
        this.socialMedias = socialMedias;
        this.phone = phone;
        this.profilePicture = profilePicture;
    }


    @Override
    public int getID() {
        return contactID;
    }

    public String getName() {
        return name;
    }

    public void setJob(String name) {
        this.name = name;
    }

    @Override
    public void setName(String phone) {
        this.phone = phone;
    }

    @Override
    public void setPhone(String email) {
        this.email = email;
    }

    public String getRequestPlace() {
        return requestPlace;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setID(int ID) {
        this.contactID = ID;
    }

    public void setRequestPlace(String requestPlace) {
        this.requestPlace = requestPlace;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void setSocialMedias(List<SocialMedia> socialMedias) {
        this.socialMedias = socialMedias;
    }

    @Override
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<SocialMedia> getSocialMedias() {
        return this.socialMedias;
    }

    @Override
    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void addSocialMedia(SocialMediaImpl socialMedia) {
        this.socialMedias.add(socialMedia);
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
