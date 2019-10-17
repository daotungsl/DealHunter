package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Override
    Optional<Account> findById(Long aLong);

    @Query(value = "SELECT a FROM Account a WHERE a.username = ?1 and a.password = ?2")
    Optional<Account> login(String username, String password);
    Optional<Account> findByToken(String token);

    @Query("SELECT a FROM Account a WHERE a.username = ?1")
    Optional<Account> findByUsername(String name);

    @Query("SELECT a FROM Account a WHERE a.token = ?1")
    Optional<Account> findByTokenAccount (String token);

    @Query("SELECT a FROM Account a WHERE a.typeAccount = ?1")
    List<Account> countAccountByType(int type);

    @Query("SELECT a FROM Account a WHERE a.typeAccount = 0 and a.typeAccount = 1")
    List<Account> getAllSoAndUser();

}
