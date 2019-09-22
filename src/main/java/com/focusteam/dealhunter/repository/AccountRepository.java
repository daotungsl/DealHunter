package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.dto.AccountLoginDto;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Override
    Optional<Account> findById(Long aLong);

    @Query(value = "SELECT a FROM Account a WHERE a.username = ?1 and a.password = ?2")
    Optional<Account> login(String username, String password);
    Optional<Account> findByToken(String token);

    @Query("SELECT a FROM Account a WHERE a.username = ?1")
    Optional<Account> findByName(String name);

}
