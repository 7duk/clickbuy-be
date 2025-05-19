package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.repository.RepositoryInterface;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public abstract class BaseService<T, ID> {

    RepositoryInterface<T, ID> repository;

    @Transactional(readOnly = true)
    Page<T> getWithPagination(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    T save(T t) {
        try{
            return repository.save(t);
        }
        catch(Exception e){
            log.error("Can't stored instance of {} because of {}", t.getClass(), e.getMessage());
            return null;
        }
    }

    @Transactional
    boolean delete(T t) {
        try{
            repository.delete(t);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}
