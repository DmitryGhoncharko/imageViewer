package com.example.shopshoesspring.repository;

import com.example.shopshoesspring.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long> {
    List<Stamp> findByStampTypeId(Long id);
}
