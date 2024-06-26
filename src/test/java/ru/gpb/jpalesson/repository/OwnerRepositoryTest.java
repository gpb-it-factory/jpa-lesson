package ru.gpb.jpalesson.repository;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.gpb.jpalesson.config.BaseTestConfiguration;
import ru.gpb.jpalesson.entity.one2many.Animal;
import ru.gpb.jpalesson.entity.one2many.Owner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс для тестирования репозитория owner")
class OwnerRepositoryTest extends BaseTestConfiguration {

    @Autowired
    private OwnerRepository repository;

    @Autowired
    private TransactionTemplate transactionTemplate;
    
    @BeforeEach
    void clear() {
        repository.deleteAll();
    }
    
    @Test
    @DisplayName("Добавление новых животных для владельца")
    void addNewAnimalsForOwner() {
        repository.save(Owner.builder().name("Vasya").build());

        var ownerFromdb = repository.findByName("Vasya").orElseThrow();

        assertTrue(ownerFromdb.getAnimals().isEmpty());
    
        List<Animal> animals = List.of(
                Animal.builder().name("Shanti").build(),
                Animal.builder().name("Jack").build());
    
        ownerFromdb.getAnimals().addAll(animals);

        repository.save(ownerFromdb);

        ownerFromdb = repository.findById(ownerFromdb.getId()).orElseThrow();

        assertEquals(2, ownerFromdb.getAnimals().size());
    }

    @Test
    @DisplayName("Ошибка с ленивой инициализацией")
    @Transactional(propagation = Propagation.NEVER)
    void throwLazyInitializationExceptionWithoutTransaction() {
        List<Animal> animals = List.of(
                Animal.builder().name("Shanti").build(),
                Animal.builder().name("Jack").build());
    
        Owner owner = Owner.builder()
                .name("Vasya")
                .animals(animals)
                .build();
    
        repository.save(owner);

        var ownerFromdb = repository.getByName("Vasya").orElseThrow();
        
        assertThrows(LazyInitializationException.class, () -> ownerFromdb.getAnimals().size());
    }

    @Test
    @DisplayName("Демонстрация проблемы n+1")
    @Transactional(propagation = Propagation.NEVER)
    void nPlusOneProblem() {
        List<Animal> animals = List.of(
                Animal.builder().name("Shanti").build(),
                Animal.builder().name("Jack").build());
    
        Owner owner = Owner.builder()
                .name("Vasya")
                .animals(animals)
                .build();
        
        transactionTemplate.executeWithoutResult(ts -> repository.save(owner));

        transactionTemplate.executeWithoutResult(
                ts -> {
                    final var vasya = repository.getByName("Vasya").orElseThrow();
                    vasya.getAnimals().forEach(animal -> System.out.println(animal.getName()));
                }
        );
    }

    @Test
    @DisplayName("Исправление проблемы n+1 через EntityGraph")
    @Transactional(propagation = Propagation.NEVER)
    void nPlusOneFixes() {
        List<Animal> animals = List.of(
                Animal.builder().name("Shanti").build(),
                Animal.builder().name("Jack").build());
    
        Owner owner = Owner.builder()
                .name("Vasya")
                .animals(animals)
                .build();
    
        transactionTemplate.executeWithoutResult(ts -> repository.save(owner));

        transactionTemplate.executeWithoutResult(
                ts -> {
                    final var vasya = repository.findByName("Vasya").orElseThrow();

                    vasya.getAnimals().forEach(animal -> System.out.println(animal.getName()));
                }
        );
    }
}