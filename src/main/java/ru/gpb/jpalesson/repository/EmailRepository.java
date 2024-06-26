package ru.gpb.jpalesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gpb.jpalesson.entity.simple.Email;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    
    /**
     * Поиск всех Email, содержащих указанный домен.
     *
     * @param domain домен для поиска.
     * @return список Email, содержащих указанный домен.
     */
    @Query("SELECT e FROM Email e WHERE e.address LIKE %:domain")
    List<Email> findAllByDomain(@Param("domain") String domain);
    
    /**
     * Поиск всех Email, начинающихся с указанного префикса.
     *
     * @param prefix префикс для поиска.
     * @return список Email, начинающихся с указанного префикса.
     */
    @Query("SELECT e FROM Email e WHERE e.address LIKE :prefix%")
    List<Email> findAllByPrefix(@Param("prefix") String prefix);
}