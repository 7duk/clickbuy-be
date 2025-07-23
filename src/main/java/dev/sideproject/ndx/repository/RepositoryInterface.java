package dev.sideproject.ndx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositoryInterface<T,D> extends JpaRepository<T, D>, JpaSpecificationExecutor<T> {
}
