package com.example.ac_ecommerce.repository;

import com.example.ac_ecommerce.model.AC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ACRepository extends JpaRepository<AC, Long> {
}
