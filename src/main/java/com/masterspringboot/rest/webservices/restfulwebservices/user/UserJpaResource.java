package com.masterspringboot.rest.webservices.restfulwebservices.user;

import com.masterspringboot.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.masterspringboot.rest.webservices.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    private UserDaoService service;
    private UserRepository repository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository repository, PostRepository postRepository){
        this.repository= repository;
        this.postRepository= postRepository;
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user= repository.findById(id);
        if(user==null)
            throw new UserNotFoundException("id:"+id);

        EntityModel<User> entityModel= EntityModel.of(user.get());
        WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser= repository.save(user);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        repository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id){
        Optional<User> user= repository.findById(id);
        if(user==null)
            throw new UserNotFoundException("id:"+id);
        return user.get().getPosts();
    }

    @GetMapping("/jpa/users/{id}/posts/{number}")
    public Post retrievePostForUser(@PathVariable int id,@PathVariable int number){
        List<Post> list= retrievePostsForUser(id);
        Optional<Post> post= null;
        for(Post p : list)
            if(p.getId()==number)
                post= postRepository.findById(number);
        if(post==null)
            throw new PostNotFoundException("id:"+number);
        return post.get();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user= repository.findById(id);
        if(user==null)
            throw new UserNotFoundException("id:"+id);

        post.setUser(user.get());
        Post savedPost= postRepository.save(post);

        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
