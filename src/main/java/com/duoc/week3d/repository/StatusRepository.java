package com.duoc.week3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.duoc.week3d.model.Status;

// Repository layer where the data is managed
@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
}
