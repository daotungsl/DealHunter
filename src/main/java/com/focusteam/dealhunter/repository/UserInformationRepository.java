package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, String> {

    @Query("SELECT u FROM UserInformation u WHERE u.phone = ?1")
    Optional<UserInformation> findByPhone(String phone);

    @Query("SELECT u FROM UserInformation u WHERE u.email = ?1")
    Optional<UserInformation> findByEmail(String email);
}
