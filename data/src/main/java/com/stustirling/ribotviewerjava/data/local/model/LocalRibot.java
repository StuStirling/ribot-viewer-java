package com.stustirling.ribotviewerjava.data.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Stu Stirling on 27/09/2017.
 */
@Entity(tableName = "ribots")
public class LocalRibot {
    public @PrimaryKey @NonNull String id;
    public @ColumnInfo(name = "first_name") String firstName;
    public @ColumnInfo(name = "last_name") String lastName;
    public String email;
    public @ColumnInfo(name = "dob") Date dateOfBirth;
    public String color;
    public String bio;
    public String avatar;
    public @ColumnInfo(name = "active") Boolean isActive;

    public LocalRibot(String id, String firstName, String lastName, String email, Date dateOfBirth, String color, String bio, String avatar, Boolean isActive) {
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
}
