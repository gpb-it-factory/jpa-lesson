package ru.gpb.jpalesson.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValidationService {
    
    @Transactional
    public void check(int counter) {
        if (counter > 2) {
            throw new RuntimeException("Не прошла проверка");
        }
    }
}
