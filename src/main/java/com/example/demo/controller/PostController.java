package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Post;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @PostMapping("/users/{user_id}/post")
    public ResponseEntity<Post> addPost(@RequestBody Post post, @PathVariable("user_id") String user_id) throws NullPointerException {
        try {
            Post _post = postRepository.save( new Post(user_id, post.getContent()) );

            return new ResponseEntity<>(_post, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/users/{user_id}/post")
    public ResponseEntity<List<Post>> findByUserId(@PathVariable("user_id") String user_id) {
        try {
            List<Post> posts = new ArrayList<Post>();
            postRepository.findByUserId(user_id).forEach(posts::add);

            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/users/{user_id}/post")
    public ResponseEntity<HttpStatus> deleteAllPosts(@PathVariable("user_id") String user_id) {
        try {

            postRepository.findByUserId(user_id).forEach((p) -> { deletePost(p.getId(), user_id);});

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{user_id}/post/{post_id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("post_id") String post_id, @PathVariable String user_id) {
        try {
            postRepository.deleteById(post_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
