package com.example.jobs.controller;

import com.example.jobs.model.Blog;
import com.example.jobs.model.User;
import com.example.jobs.service.BlogService;
import com.example.jobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class UserBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Blog blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs(
            @RequestHeader(value = "Authorization", required = false) String jwt
    ) throws Exception {
        if (jwt != null && !jwt.isEmpty()) {
            try {
                User user = userService.findUserByJwtToken(jwt);
                System.out.println("Authenticated user: " + user.getEmail());
            } catch (Exception e) {
                System.out.println("Invalid or expired JWT token");
            }
        }
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

}
