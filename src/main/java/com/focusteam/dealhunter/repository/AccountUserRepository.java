package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Account_User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountUserRepository extends JpaRepository<Account_User, Long> {
}
