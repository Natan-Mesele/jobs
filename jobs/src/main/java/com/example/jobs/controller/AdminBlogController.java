package com.example.jobs.controller;

import com.example.jobs.model.Blog;
import com.example.jobs.model.User;
import com.example.jobs.service.BlogService;
import com.example.jobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/blog")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Blog> createBlog(
            @RequestBody Blog blog,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Blog createdBlog = blogService.createBlog(blog);
        return ResponseEntity.ok(createdBlog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable Long id,
            @RequestBody Blog updatedBlog,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Blog blog = blogService.updateBlog(id, updatedBlog);
        return ResponseEntity.ok(blog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}
