package com.rac;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rac.config.AppConfig;
import com.rac.dao.AuthorRepository;
import com.rac.dao.CommentRepository;
import com.rac.dao.PostRepository;
import com.rac.dao.UserRepository;
import com.rac.model.Comment;
import com.rac.model.Post;

@SpringApplicationConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void getAll() {
	System.err.println(userRepository.findAll().size());
    }

    @Test
    public void savePost() {
	Post post = postRepository.findAll().get(1);
	System.err.println(post.getId());
	// post.setAuthor(authorRepository.findAll().get(0));
	// post.setBody("body");
	// post.setTitle("title");

	Comment comment = new Comment();
	comment.setComment("comment2");
	comment.setDate(new Date());
	comment.setRating(5);
	// comment.setPost(post);
	commentRepository.save(comment);
	// List<Comment> c = post.getComments();
	// c.add(comment);
	// post.setComments(c);

	post.addComment(comment);

	postRepository.save(post);
    }

    @Test
    public void findComment() {
	Post post = postRepository.findAll().get(1);
	// List<Comment> comments =
	// commentRepository.findCommentByPostId(post.getId());
	// System.err.println(comments.get(0));

    }

}
