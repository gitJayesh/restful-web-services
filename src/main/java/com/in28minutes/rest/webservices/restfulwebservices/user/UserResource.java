package com.in28minutes.rest.webservices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService service;
    public UserResource(UserDaoService service){
        this.service = service;
    }

    //GET USERS
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //GET USERS BY ID
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id )
    {
        User user = service.findOne(id);

        if(user==null){
            throw new UserNotFoundException("id:"+id);
        }
        return user;
    }

    //POST USERS
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    //DELETE USERS BY ID
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id )
    {
        service.deleteById(id);
    }
}