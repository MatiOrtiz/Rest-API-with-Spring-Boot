package com.masterspringboot.rest.webservices.restfulwebservices.version;

public class Name {
    private String firstName, lastName;

    public Name(String firstName, String lastName){
        this.firstName= firstName;
        this.lastName= lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
