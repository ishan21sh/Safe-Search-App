package com.projects.myapplication;

import java.util.ArrayList;

public class AppModel {
    String name;
    String link;
    int permissions;
    ArrayList<String> permissions_name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AppModel{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", permissions=" + permissions +
                ", permissions_name=" + permissions_name +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public ArrayList<String> getPermissions_name() {
        return permissions_name;
    }

    public void setPermissions_name(ArrayList<String> permissions_name) {
        this.permissions_name = permissions_name;
    }

    public AppModel(String name, String link, int permissions, ArrayList<String> permissions_name) {
        this.name = name;
        this.link = link;
        this.permissions = permissions;
        this.permissions_name = permissions_name;
    }

}
