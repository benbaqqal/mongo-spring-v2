package com.rac.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {

    @Id
    private String id;

    private String comment;

    private int rating;

    private Date date;

    private int upvotes;

    // @DBRef
    // private Post post;

    public Comment() {
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public int getRating() {
	return rating;
    }

    public void setRating(int rating) {
	this.rating = rating;
    }

    // public Post getPost() {
    // return post;
    // }
    //
    // public void setPost(Post post) {
    // this.post = post;
    // }

    public int getUpvotes() {
	return upvotes;
    }

    public void setUpvotes(int upvotes) {
	this.upvotes = upvotes;
    }

    @Override
    public String toString() {
	return "Comment [id=" + id + ", comment=" + comment + ", rating=" + rating + ", date=" + date + "]";
    }

}
