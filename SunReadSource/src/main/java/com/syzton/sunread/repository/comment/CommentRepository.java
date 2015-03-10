package com.syzton.sunread.repository.comment;
import com.syzton.sunread.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
