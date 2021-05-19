package com.diets.dietplans.model;

import com.diets.dietplans.model.interactive.AllDiets;
import com.diets.dietplans.model.schema.Schema;

import java.io.Serializable;
import java.util.List;

public class Global implements Serializable {
    private List<Section> sectionsArray;
    private String name;
    private AllDiets allDiets;
    private List<Schema> schemas;
    private Boolean plan;

    public Global() {
    }

    public Global(List<Section> sectionsArray, String name, AllDiets allDiets, List<Schema> schemas) {
        this.sectionsArray = sectionsArray;
        this.name = name;
        this.allDiets = allDiets;
        this.schemas = schemas;
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

    public AllDiets getAllDiets() {
        return allDiets;
    }

    public void setAllDiets(AllDiets allDiets) {
        this.allDiets = allDiets;
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
    }
}
