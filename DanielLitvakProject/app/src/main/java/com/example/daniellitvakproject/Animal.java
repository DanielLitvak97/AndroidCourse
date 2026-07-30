package com.example.daniellitvakproject;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Animal {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("preferred_common_name")
    private String preferredCommonName;

    @SerializedName("default_photo")
    private Photo defaultPhoto;

    @SerializedName("ancestors")
    private List<Ancestor> ancestors;

    public Animal(){
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreferredCommonName() {
        return preferredCommonName;
    }

    public String getImageUrl(){
        if(defaultPhoto != null){
            return defaultPhoto.getMedium_url();
        }

        return null;
    }

    public String getDescription(){
        StringBuilder text = new StringBuilder();

        if(name != null){
            text.append("Scientific name: ").append(name).append("\n");
        }

        if(preferredCommonName != null){
            text.append("Common name: ").append(preferredCommonName).append("\n");
        }

        if(ancestors != null){
            for(Ancestor ancestor : ancestors){
                text.append(ancestor.getRank())
                        .append(": ")
                        .append(ancestor.getName())
                        .append("\n");
            }
        }

        return text.toString();
    }

    public class Photo {
        private String medium_url;
        public String getMedium_url() {
            return medium_url;
        }
    }
}