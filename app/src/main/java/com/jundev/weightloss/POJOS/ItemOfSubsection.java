package com.jundev.weightloss.POJOS;

import java.io.Serializable;

public class ItemOfSubsection implements Serializable {
    private String urlOfImage;
    private String descriptionOfImage;
    private String bodyOfText;

    public ItemOfSubsection() {
    }

    public ItemOfSubsection(String urlOfImage, String descriptionOfImage, String bodyOfText) {
        this.urlOfImage = urlOfImage;
        this.descriptionOfImage = descriptionOfImage;
        this.bodyOfText = bodyOfText;
    }

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }

    public String getDescriptionOfImage() {
        return descriptionOfImage;
    }

    public void setDescriptionOfImage(String descriptionOfImage) {
        this.descriptionOfImage = descriptionOfImage;
    }

    public String getBodyOfText() {
        return bodyOfText;
    }

    public void setBodyOfText(String bodyOfText) {
        this.bodyOfText = bodyOfText;
    }
}
