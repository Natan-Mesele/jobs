package com.example.jobs.service;

import com.example.jobs.model.Blog;

import java.util.List;

public interface BlogService {
    Blog createBlog(Blog blog);
    Blog getBlogById(Long id);
    List<Blog> getAllBlogs();
    Blog updateBlog(Long id, Blog updatedBlog);
    void deleteBlog(Long id);
}
