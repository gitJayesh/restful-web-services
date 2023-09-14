package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount,"Jayesh", LocalDate.now().minusYears(20)));
        users.add(new User(++usersCount,"kunal", LocalDate.now().minusYears(24)));
        users.add(new User(++usersCount,"Rahul", LocalDate.now().minusYears(22)));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().get();
    }

    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }
}