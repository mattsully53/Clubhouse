package com.softwareengineering.clubhouseapp;

import java.util.ArrayList;

/**
 * Created by Matt on 11/26/17.
 */

public class Group {
    private String name, description;
    private int imageResourceId, administratorId;

    Group(String groupName, String groupDescription, int imageId, int adminId) {
        name = groupName;
        description = groupDescription;
        imageResourceId = imageId;
        administratorId = adminId;
    }

    public void setName(String groupName) {
        name = groupName;
    }

    public void setDescription(String groupDescription) {
        description = groupDescription;
    }

    public void setImageResourceId(int imageId) {
        imageResourceId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void addAdmin(int userId) {

    }

    public void deleteAdmin(int userId) {

    }
}
