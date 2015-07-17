package com.syzton.sunread.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.message.Action;

public interface ActionRepository extends JpaRepository<Action,Long> {

}
