package com.masterspringboot.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static List<User> users= new ArrayList<User>();

    static {
        users.add(new User(1,"John", LocalDate.now().minusYears(20)));
        users.add(new User(2,"Matt", LocalDate.now().minusYears(25)));
        users.add(new User(3,"Liz", LocalDate.now().minusYears(30)));

    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().get();
    }
}
