package ru.gpb.jpalesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gpb.jpalesson.entity.one2one.Worker;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    
    Optional<Worker> findByName(String name);
}