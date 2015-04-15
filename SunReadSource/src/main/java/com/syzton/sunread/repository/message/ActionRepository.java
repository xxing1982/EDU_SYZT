package com.syzton.sunread.repository.message;

import com.syzton.sunread.model.message.Action;
import com.syzton.sunread.model.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action,Long> {

}
