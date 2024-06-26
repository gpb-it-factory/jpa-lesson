package ru.gpb.jpalesson.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gpb.jpalesson.repository.WorkerRepository;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Задача этого класса - продемонстрировать работу с транзакциями в Spring.
 * Необходимо поправить код (а не тесты), чтобы тесты стали проходить корректно.
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Класс для демонстрации работы @Transactional")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionalDemoTest {
    
    @Autowired
    private TransactionalDemo transactionalDemo;
    @Autowired
    private WorkerRepository workerRepository;
    
    @BeforeEach
    void clear() {
        workerRepository.deleteAll();
    }
    
    @Test
    void executionWithError() {
        assertThrows(Exception.class, () -> transactionalDemo.executionWithError(3));
        
        assertEquals(0, workerRepository.findAll().size());
    }
    
    @Test
    void executionTwoTransactional() {
        assertDoesNotThrow(() -> transactionalDemo.executionTwoTransactional(3));
        
        assertEquals(2, workerRepository.findAll().size());
    }
}