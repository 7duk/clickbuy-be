package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.repository.RepositoryInterface;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public abstract class BaseService<T, ID> {

    RepositoryInterface<T, ID> repository;

    @Transactional(readOnly = true)
    public Page<T> getWithPagination(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Optional<T> save(T t) {
        return Optional.of(repository.save(t));
    }
}
