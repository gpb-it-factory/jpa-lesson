package ru.gpb.jpalesson.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gpb.jpalesson.entity.one2many.Owner;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    
    @EntityGraph(attributePaths = {"animals"})
    Optional<Owner> findByName(String name);
    
    Optional<Owner> getByName(String name);
    
}