package dev.sideproject.ndx2.repository;

import dev.sideproject.ndx2.entity.Account;
import dev.sideproject.ndx2.constant.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends RepositoryInterface<Account, Long> {
    Optional<Account> findByUsername(String username);

    void updateRoleById(Long id, Role role);

    boolean existByIdAndEmail(String id, String email);
}
