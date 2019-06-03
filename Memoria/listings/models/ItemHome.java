package com.alehp.ull_navigation.Models;

import com.alehp.ull_navigation.R;

public class ItemHome {
    private String name;
    private String image;
    private boolean isWebLink;
    private String link;

    public ItemHome(String name, String image, boolean isWebLink, String link){
        this.name = name;
        this.image = "@drawable/" + image;
        this.isWebLink = isWebLink;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isWebLink() {
        return isWebLink;
    }

    public void setWebLink(boolean webLink) {
        isWebLink = webLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
