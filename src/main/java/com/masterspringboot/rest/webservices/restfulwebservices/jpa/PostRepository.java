package com.masterspringboot.rest.webservices.restfulwebservices.jpa;

import com.masterspringboot.rest.webservices.restfulwebservices.user.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
