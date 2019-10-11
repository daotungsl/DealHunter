package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.dto.groupVoucherDto.Vouchers;
import com.focusteam.dealhunter.entity.Store;
import com.focusteam.dealhunter.entity.Voucher;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s WHERE s.email = ?1")
    Optional<Store> findByEmail(String email);

    @Query("SELECT s FROM Store s WHERE s.nameUnAccent = ?1")
    Optional<Store> findByNameUnAccent(String name);

    @Query("SELECT s FROM Store s WHERE s.name = ?1 OR s.email = ?2 OR  s.phone = ?3")
    Optional<Store> findByName(String name, String email, String phone);
}
