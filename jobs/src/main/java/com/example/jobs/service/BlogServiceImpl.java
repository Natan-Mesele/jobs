package com.example.jobs.service;

import com.example.jobs.model.Blog;
import com.example.jobs.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog updateBlog(Long id, Blog updatedBlog) {
        Blog existingBlog = getBlogById(id);
        existingBlog.setTitle(updatedBlog.getTitle());
        existingBlog.setAuthor(updatedBlog.getAuthor());
        existingBlog.setContent(updatedBlog.getContent());
        existingBlog.setImageUrl(updatedBlog.getImageUrl());
        existingBlog.setPublishedDate(updatedBlog.getPublishedDate());
        return blogRepository.save(existingBlog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
