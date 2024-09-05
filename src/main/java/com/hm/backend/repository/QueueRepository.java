package com.hm.backend.repository;

import com.hm.backend.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository  extends JpaRepository<Queue,Long> {
}
