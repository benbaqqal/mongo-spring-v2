package com.rac.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rac.model.Author;
import com.rac.model.Comment;
import com.rac.model.Post;
import com.rac.service.PostService;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/postSize", method = RequestMethod.GET)
    public @ResponseBody int getPostCount() {
	return postService.getPosts().size();
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public @ResponseBody Post getPost(@PathVariable("id") final String id) {
	return postService.getPost(id);
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public @ResponseBody List<Post> getPostList() {
	return postService.getPosts();
    }

    @RequestMapping(value = "/savePost", method = RequestMethod.POST)
    public @ResponseBody List<Post> savePost(@RequestBody final Post post) {
	return saveOrUpdatePost(post);
    }

    @RequestMapping(value = "/upvotePost/{up}", method = RequestMethod.POST)
    public @ResponseBody List<Post> upvotePost(@RequestBody final Post post, @PathVariable("up") final boolean isUP) {
	if (isUP) {
	    post.setUpvotes(post.getUpvotes() + 1);
	} else {
	    post.setUpvotes(post.getUpvotes() - 1);
	}
	return saveOrUpdatePost(post);
    }

    private List<Post> saveOrUpdatePost(final Post post) {
	postService.save(post);
	return postService.getPosts();
    }

    // @Secured("ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    public @ResponseBody List<Post> deletePost(@RequestBody final Post post) {
	postService.deletePost(post);
	return postService.getPosts();
    }

    // comments
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public @ResponseBody Comment getComment(@PathVariable("id") final String id) {
	return postService.getComment(id);
    }

    // @RequestMapping(value = "/comments/{postId}", method = RequestMethod.GET)
    // public @ResponseBody List<Comment> getCommentList(@PathVariable("postId")
    // final String postId) {
    // return postService.findCommentByPostId(postService.getPost(postId));
    // }

    @RequestMapping(value = "/saveComment/{postId}", method = RequestMethod.POST)
    public @ResponseBody Post saveComment(@RequestBody final Comment comment,
	    @PathVariable("postId") final String postId) {

	// Comment comment = postComment.getComment();
	Post post = postService.getPost(postId);

	comment.setDate(new Date());
	postService.save(comment);

	post.addComment(comment);
	postService.save(post);
	return post;
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public @ResponseBody List<Comment> deleteComment(@RequestBody final Comment comment) {
	postService.deleteComment(comment);
	return postService.getComments();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/upvoteComment/{isUp}/{postId}", method = RequestMethod.POST)
    public @ResponseBody Post upvoteComment(@RequestBody final Comment comment,
	    @PathVariable("isUp") final boolean isUP, @PathVariable("postId") final String postId) {
	if (isUP) {
	    comment.setUpvotes(comment.getUpvotes() + 1);
	} else {
	    comment.setUpvotes(comment.getUpvotes() - 1);
	}
	postService.save(comment);
	// return postService.findCommentByPostId(comment.getPost());

	//// TODO reload instead of returning post or comments
	return postService.getPost(postId);
    }

    // Authors
    @RequestMapping(value = "/author/{id}", method = RequestMethod.GET)
    public @ResponseBody Author getAuthor(@PathVariable("id") final String id) {
	return postService.getAuthor(id);
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody List<Author> getAuthorList() {
	return postService.getAuthors();
    }

    @RequestMapping(value = "/saveAuthor", method = RequestMethod.POST)
    public @ResponseBody List<Author> saveAuthor(@RequestBody final Author author) {
	postService.save(author);
	return postService.getAuthors();
    }

    @RequestMapping(value = "/deleteAuthor", method = RequestMethod.POST)
    public @ResponseBody List<Author> deleteAuthor(@RequestBody final Author author) {
	postService.deleteAuthor(author);
	return postService.getAuthors();
    }

}
