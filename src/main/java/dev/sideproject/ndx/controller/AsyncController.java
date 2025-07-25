package dev.sideproject.ndx.controller;

import dev.sideproject.ndx.service.AsyncTaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AsyncController {
    AsyncTaskService asyncTaskService;
    @GetMapping("/async")
    public void asyncDemo() throws InterruptedException {
        log.info("asyncDemo at controller is started at {}", LocalDateTime.now());
        asyncTaskService.asyncMethod();
        log.info("asyncDemo at controller is finished at {}", LocalDateTime.now());
    }
}
