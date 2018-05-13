package dao;

import common.TransactionDao;
import model.Comment;

public class CommentDao extends TransactionDao<Comment> {

	protected CommentDao() {
		super(Comment.class);
	}
}