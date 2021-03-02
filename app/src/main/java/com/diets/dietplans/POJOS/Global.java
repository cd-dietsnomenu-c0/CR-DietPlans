package com.diets.dietplans.POJOS;

import java.io.Serializable;
import java.util.List;

public class Global implements Serializable {
    private List<Section> sectionsArray;
    private String name;

    public Global() {
    }

    public Global(List<Section> sectionsArray, String name) {
        this.sectionsArray = sectionsArray;
        this.name = name;
    }

    public List<Section> getSectionsArray() {
        return sectionsArray;
    }

    public void setSectionsArray(List<Section> sectionsArray) {
        this.sectionsArray = sectionsArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
