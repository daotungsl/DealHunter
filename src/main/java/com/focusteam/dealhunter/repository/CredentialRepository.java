package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findByToken(String token);
}
