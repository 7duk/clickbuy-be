package dev.sideproject.ndx2.repository;

import dev.sideproject.ndx2.entity.Account;
import dev.sideproject.ndx2.constant.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends RepositoryInterface<Account, Integer> {
    Optional<Account> findByUsername(String username);

    @Modifying
    @Query(value = "UDPATE account SET role = :role WHERE id = :id", nativeQuery = true)
    void updateRoleById(@Param("id") Integer id, @Param("role") Role role);
}
