package com.tim.wang;

import java.io.File;

/**
 * Created by Malcolm on 2016/7/13.
 */
public class Person {
    private String name;
    private File photo;

    public Person(){
    }
    public Person(String name,File photo){
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }
}
