package com.jundev.weightloss.POJOS;

import java.io.Serializable;
import java.util.List;

public class Section implements Serializable {
    private String urlOfImage;
    private String description;
    private List<Subsection> arrayOfSubSections;

    public Section() {
    }

    public Section(String urlOfImage, String description, List<Subsection> arrayOfSubSections) {
        this.urlOfImage = urlOfImage;
        this.description = description;
        this.arrayOfSubSections = arrayOfSubSections;
    }

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Subsection> getArrayOfSubSections() {
        return arrayOfSubSections;
    }

    public void setArrayOfSubSections(List<Subsection> arrayOfSubSections) {
        this.arrayOfSubSections = arrayOfSubSections;
    }
}

