package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Override
    Optional<Account> findById(Long aLong);

    @Query("SELECT a FROM Account a WHERE a.name = ?1")
    Optional<Account> findByName(String name);

}
