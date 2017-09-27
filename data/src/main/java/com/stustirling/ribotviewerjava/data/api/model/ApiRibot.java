package com.stustirling.ribotviewerjava.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class ApiRibot {
    public @SerializedName("profile") ApiRibotProfile profile;

    public class ApiRibotProfile {
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public ApiRibotName name;
        @SerializedName("email")
        public String email;
        @SerializedName("hexColor")
        public String color;
        @SerializedName("avatar")
        public String avatar;
        @SerializedName("dateOfBirth")
        public Date dob;
        @SerializedName("bio")
        public String bio;
        @SerializedName("isActive")
        public Boolean isActive;

        public class ApiRibotName {
            public @SerializedName("first") String first;
            public @SerializedName("last") String last;
        }

    }

}
