package ru.gpb.jpalesson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionalDemo {
    
    private final ValidationService validationService;
    private final WorkerService workerService;
    
    @Transactional
    public void executionWithError(int counter) throws Exception {
        workerService.saveNewRandomWorker();
        
        if (counter > 0) {
            throw new Exception("Проверка не прошла");
        }
        
        workerService.saveNewRandomWorker();
    }
    
    @Transactional
    public void executionTwoTransactional(int counter) {
        workerService.saveNewRandomWorker();
        
        try {
            validationService.check(counter);
        } catch (RuntimeException e) {
            counter = 2;
        }
        
        workerService.saveNewRandomWorker(counter);
    }
    
    public void wrappedTransactionalMethod() {
        executionTwoTransactional(2); //транзакция не будет работать, так как не вызывается proxy
    }
    
    @Transactional
    private void privateTransactionalMethod() {
        //транзакция не будет работать, так как приватные методы не могут вызываться через proxy
    }
}
