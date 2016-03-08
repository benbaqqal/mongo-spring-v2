package com.rac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rac.dao.AuthorRepository;
import com.rac.dao.CommentRepository;
import com.rac.dao.PostRepository;
import com.rac.model.Author;
import com.rac.model.Comment;
import com.rac.model.Post;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    // posts
    public List<Post> getPosts() {
	return postRepository.findAll();
    }

    public Post getPost(final String id) {
	return postRepository.findOne(id);
    }

    public void save(final Post post) {
	postRepository.save(post);
    }

    public void deleteAllPosts() {
	postRepository.deleteAll();
    }

    public void deletePost(final Post post) {
	postRepository.delete(post);
    }

    // Comments
    public List<Comment> getComments() {
	return commentRepository.findAll();
    }

    // public List<Comment> findCommentByPostId(Post post) {
    // return commentRepository.findCommentByPostId(post.getId());
    // }

    public Comment getComment(final String id) {
	return commentRepository.findOne(id);
    }

    public void save(final Comment comment) {
	commentRepository.save(comment);
    }

    public void deleteAllComments() {
	commentRepository.deleteAll();
    }

    public void deleteComment(final Comment comment) {
	commentRepository.delete(comment);
    }

    // authors
    public List<Author> getAuthors() {
	return authorRepository.findAll();
    }

    public Author getAuthor(final String id) {
	return authorRepository.findOne(id);
    }

    public void save(final Author author) {
	authorRepository.save(author);
    }

    public void deleteAllAuthors() {
	authorRepository.deleteAll();
    }

    public void deleteAuthor(final Author author) {
	authorRepository.delete(author);
    }

}
