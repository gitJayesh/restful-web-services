package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {

//    private UserDaoService service;

    private UserRepository userRepository;

    private PostRepository postRepository;
    public UserJpaResource(UserRepository userRepository, PostRepository postRepository){

        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    //GET USERS
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    //GET USERS BY ID
    @GetMapping("/jpa/users/{id}")
    public Optional<User> retrieveUser(@PathVariable int id )
    {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("id:"+id);
        }
        return user;
    }

    //POST USERS
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    //DELETE USERS BY ID
    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id )
    {
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id )
    {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);
        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post )
    {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}