package com.rac.dao.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;

import com.rac.model.Comment;
import com.rac.model.Post;

public class CommentCascadeDeleteMongoEventListener extends AbstractMongoEventListener<Post> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Post> event) {
	Post post = mongoOperations.findById(event.getSource().get("id"), Post.class);
	if (post.getComments() != null) {
	    for (Comment comment : post.getComments()) {
		if (comment != null) {
		    mongoOperations.remove(comment);
		}
	    }
	}
    }

}
