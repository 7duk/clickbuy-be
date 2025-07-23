package dev.sideproject.ndx.repository;

import dev.sideproject.ndx.common.Role;
import dev.sideproject.ndx.entity.Account;
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

    @Query(value = "select fullname from account where id = :id", nativeQuery = true)
    String findFullNameById(@Param("id") Integer id);
}
