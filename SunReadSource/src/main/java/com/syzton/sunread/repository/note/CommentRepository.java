package com.syzton.sunread.repository.note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.note.Comment;
import com.syzton.sunread.model.note.Note;

/**
 * @author chenty
 *
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

	Page<Comment> findByNote(Note note, Pageable pageable);
}
