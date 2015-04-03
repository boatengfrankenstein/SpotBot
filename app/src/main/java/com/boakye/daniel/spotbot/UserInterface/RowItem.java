package com.boakye.daniel.spotbot.UserInterface;

import android.graphics.Bitmap;

import com.facebook.widget.ProfilePictureView;

public class RowItem {
    // private int imageId;
    private String title;
    private String profilePictureView;
    private String description;
    private String lastSeen;

    public RowItem(String imageId, String title, String desc, String lastSeen) {
        this.profilePictureView = imageId;
        this.title = title;
        this.description = desc;
        this.lastSeen = lastSeen;
    }
    public String getImageId() {
        return profilePictureView;
    }
    public void setImageId(String profileImageId) {
        this.profilePictureView = profileImageId;
    }
    public String getDesc() {
        return description;
    }
    public void setDesc(String desc) {
        this.description = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setlastSeen(String lastSeen){
        this.lastSeen = lastSeen;
    }
    public String getLastSeen(){
        return lastSeen;
    }
    @Override
    public String toString() {
        return title + "\n" + description;
    }
}
