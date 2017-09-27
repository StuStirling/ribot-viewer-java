package com.stustirling.ribotviewerjava.shared.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.stustirling.ribotviewjava.domain.model.Ribot;

import java.util.Date;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class RibotModel implements Parcelable {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private String color;
    private String bio;
    private String avatar;
    private Boolean isActive;

    public RibotModel(String id, String firstName, String lastName, String email, Date dateOfBirth, String color, String bio, String avatar, Boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.color = color;
        this.bio = bio;
        this.avatar = avatar;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    public static RibotModel from(Ribot ribot) {
        return new RibotModel(
                ribot.getId(),
                ribot.getFirstName(),
                ribot.getLastName(),
                ribot.getEmail(),
                ribot.getDateOfBirth(),
                ribot.getColor(),
                ribot.getBio(),
                ribot.getAvatar(),
                ribot.getActive());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeLong(this.dateOfBirth != null ? this.dateOfBirth.getTime() : -1);
        dest.writeString(this.color);
        dest.writeString(this.bio);
        dest.writeString(this.avatar);
        dest.writeValue(this.isActive);
    }

    protected RibotModel(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        long tmpDateOfBirth = in.readLong();
        this.dateOfBirth = tmpDateOfBirth == -1 ? null : new Date(tmpDateOfBirth);
        this.color = in.readString();
        this.bio = in.readString();
        this.avatar = in.readString();
        this.isActive = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<RibotModel> CREATOR = new Creator<RibotModel>() {
        @Override
        public RibotModel createFromParcel(Parcel source) {
            return new RibotModel(source);
        }

        @Override
        public RibotModel[] newArray(int size) {
            return new RibotModel[size];
        }
    };
}
