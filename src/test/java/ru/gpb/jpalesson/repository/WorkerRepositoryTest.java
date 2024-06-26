package ru.gpb.jpalesson.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gpb.jpalesson.config.BaseTestConfiguration;
import ru.gpb.jpalesson.entity.one2one.Image;
import ru.gpb.jpalesson.entity.one2one.Worker;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для worker репозитория")
class WorkerRepositoryTest extends BaseTestConfiguration {
    
    @Autowired
    private WorkerRepository workerRepository;
    
    @Test
    @DisplayName("Сохранение нового worker")
    void saveNewWorker() {
        Worker worker = Worker.builder()
                .name("Peter")
                .build();
    
        workerRepository.save(worker);
        
        Optional<Worker> peter = workerRepository.findByName("Peter");
        
        assertTrue(peter.isPresent());
        assertNotEquals(0, peter.get().getId());
    }
    
    @Test
    @DisplayName("Сохранение двух worker с одинаковым именем")
    @Transactional(propagation = Propagation.NEVER)
    void saveTwoWorkerException() {
        Worker peter = Worker.builder()
                .name("Peter")
                .build();
        
        assertDoesNotThrow(() -> workerRepository.save(peter));
        
        assertThrows(DataIntegrityViolationException.class, () -> workerRepository.save(peter));
        
        workerRepository.deleteAll();
    }
    
    @Test
    @DisplayName("Сохранение аватара вместе с user в одной транзакции")
    void saveAvatarWithUser() {
        Worker worker = Worker.builder()
                .name("Peter")
                .image(Image.builder().value("123").build())
                .build();
    
        final var peter = workerRepository.save(worker);
        
        assertTrue(workerRepository.findByName("Peter").isPresent());
    
        Worker referenceById = workerRepository.getReferenceById(peter.getId());
        assertEquals("123", referenceById.getImage().getValue());
    }
}