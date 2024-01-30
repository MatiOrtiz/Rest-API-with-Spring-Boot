package com.masterspringboot.rest.webservices.restfulwebservices.version;

public class PersonV1 {
    private String name;

    public PersonV1(String name){
        this.name= name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PersonV1{" +
                "name='" + name + '\'' +
                '}';
    }

}
