package com.jundev.weightloss.POJOS;

import java.io.Serializable;
import java.util.List;

public class Subsection implements Serializable {
    private String description;
    private String urlOfImage;
    private List<ItemOfSubsection> arrayOfItemOfSubsection;

    public Subsection() {
    }

    public Subsection(String description, String urlOfImage, List<ItemOfSubsection> arrayOfItemOfSubsection) {
        this.description = description;
        this.urlOfImage = urlOfImage;
        this.arrayOfItemOfSubsection = arrayOfItemOfSubsection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }

    public List<ItemOfSubsection> getArrayOfItemOfSubsection() {
        return arrayOfItemOfSubsection;
    }

    public void setArrayOfItemOfSubsection(List<ItemOfSubsection> arrayOfItemOfSubsection) {
        this.arrayOfItemOfSubsection = arrayOfItemOfSubsection;
    }
}
