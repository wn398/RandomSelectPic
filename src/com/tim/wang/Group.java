package com.tim.wang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malcolm Wang on 2016/7/13.
 * Copyright 2016 Malcolm Inc.
 * ALL RIGHTS RESERVED.
 */
public class Group {
    private String name;
    private List<Person> persons = new ArrayList<Person>();

    public Group(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
