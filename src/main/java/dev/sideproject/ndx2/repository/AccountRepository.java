package dev.sideproject.ndx2.repository;

import dev.sideproject.ndx2.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends RepositoryInterface<Account, Integer> {
    Account findByUsername(String username);
}
