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

    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository= postRepository;
    }

    //--------------------USER Mappings--------------------//

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user= userRepository.findById(id);
        if(user==null)
            throw new UserNotFoundException("id:"+id);

        EntityModel<User> entityModel= EntityModel.of(user.get());
        WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser= userRepository.save(user);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/jpa/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @Valid @RequestBody User user){
        User savedUser= retrieveUser(id).getContent();
        savedUser.setName(user.getName());
        savedUser.setBirthDate(user.getBirthDate());
        savedUser.setPosts(savedUser.getPosts());
        userRepository.save(savedUser);

        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }


    //--------------------POST Mappings--------------------//

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id){
        Optional<User> user= userRepository.findById(id);
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
        Optional<User> user= userRepository.findById(id);
        if(user==null)
            throw new UserNotFoundException("id:"+id);

        post.setUser(user.get());
        Post savedPost= postRepository.save(post);

        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/jpa/users/{id}/posts/{number}")
    public ResponseEntity<Object> updatePostForUser(@PathVariable int id, @PathVariable int number, @Valid @RequestBody Post post){
        Post savedPost= retrievePostForUser(id, number);
        User user= userRepository.findById(id).get();
        if(user==null)
            throw new UserNotFoundException("id:"+id);

        savedPost.setUser(user);
        savedPost.setDescription(post.getDescription());
        postRepository.save(savedPost);

        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}/posts/{number}")
    public void deletePost(@PathVariable int number){
        postRepository.deleteById(number);
    }

}
