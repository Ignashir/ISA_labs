package com.example.lab_4;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface ParentRepository extends JpaRepository<Parent, UUID> {
}