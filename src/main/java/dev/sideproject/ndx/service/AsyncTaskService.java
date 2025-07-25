package dev.sideproject.ndx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AsyncTaskService {
    @Async("executor")
    public void asyncMethod() throws InterruptedException {
        Thread.sleep(5000);
        log.info("asyncMethod is executed successfully at {}", LocalDateTime.now());
    }
}
