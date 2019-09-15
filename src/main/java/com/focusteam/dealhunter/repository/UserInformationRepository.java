package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<User_Information, String> {
}
