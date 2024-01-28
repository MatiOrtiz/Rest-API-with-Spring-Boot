package com.masterspringboot.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static List<User> users= new ArrayList<User>();
    private static int usersCount= 0;

    static {
        users.add(new User(++usersCount,"John", LocalDate.now().minusYears(20)));
        users.add(new User(++usersCount,"Matt", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount,"Liz", LocalDate.now().minusYears(30)));

    }

    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
}
