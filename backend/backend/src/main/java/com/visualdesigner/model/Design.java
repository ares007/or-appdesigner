package com.visualdesigner.model;

import java.io.Serializable;

/**
 * model class for design.
 */
public class Design implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String displayDevice;
    private int safeSpace;
    private Boolean displaySafeSpace;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayDevice() {
        return displayDevice;
    }

    public void setDisplayDevice(String displayDevice) {
        this.displayDevice = displayDevice;
    }

    public int getSafeSpace() {
        return safeSpace;
    }

    public void setSafeSpace(int safeSpace) {
        this.safeSpace = safeSpace;
    }

    public Boolean getDisplaySafeSpace() {
        return displaySafeSpace;
    }

    public void setDisplaySafeSpace(Boolean displaySafeSpace) {
        this.displaySafeSpace = displaySafeSpace;
    }
}
