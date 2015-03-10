package com.syzton.sunread.repository.note;

import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.model.tag.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface NoteRepository extends JpaRepository<Note,Long> {
}
