package ru.gpb.jpalesson.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import ru.gpb.jpalesson.entity.one2one.Image;
import ru.gpb.jpalesson.entity.one2one.Worker;
import ru.gpb.jpalesson.repository.WorkerRepository;

@Component
@RequiredArgsConstructor
public class WorkerService {
    
    private final WorkerRepository workerRepository;
    
    public void saveNewRandomWorker() {
        saveNewRandomWorker(5);
    }
    
    public void saveNewRandomWorker(int maxLengthName) {
        Worker worker = Worker.builder()
                .name(RandomStringUtils.randomAlphabetic(1, maxLengthName))
                .image(Image.builder().value(RandomStringUtils.randomAlphabetic(1, 3)).build())
                .build();
        
        workerRepository.save(worker);
    }
}
