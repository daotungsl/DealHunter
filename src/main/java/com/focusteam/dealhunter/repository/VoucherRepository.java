package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Voucher;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("SELECT v FROM  Voucher v WHERE v.nameUnAccent = ?1")
    Optional<Voucher> findByNameUA(String name);
}
