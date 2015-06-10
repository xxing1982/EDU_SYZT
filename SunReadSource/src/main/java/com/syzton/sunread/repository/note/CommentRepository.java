package com.syzton.sunread.repository.note;
import com.syzton.sunread.dto.note.CommentDTO;
import com.syzton.sunread.model.note.Comment;
import com.syzton.sunread.model.note.Note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

	Page<Comment> findByNote(Note note, Pageable pageable);
}
