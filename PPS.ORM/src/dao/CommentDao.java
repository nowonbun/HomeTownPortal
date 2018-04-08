package dao;

import common.Dao;
import model.Comment;

public class CommentDao extends Dao<Comment> {

	protected CommentDao() {
		super(Comment.class);
	}
}