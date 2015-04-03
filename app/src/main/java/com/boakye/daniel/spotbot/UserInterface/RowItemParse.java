package com.boakye.daniel.spotbot.UserInterface;

import com.parse.ParseFile;

public class RowItemParse {
    // private int imageId;
    private String title;
   // private Bitmap bitImage;
    private String description;
    private String lastSeen;
    private ParseFile image;

    public RowItemParse(ParseFile imageId, String title, String desc, String lastSeen) {
        this.image = imageId;
        this.title = title;
        this.description = desc;
        this.lastSeen = lastSeen;
    }
    public ParseFile getImageId() {
        return image;
    }
    public void setImageId(ParseFile ImageId) {
        this.image = ImageId;
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
