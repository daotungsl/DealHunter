package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Voucher;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("SELECT v FROM  Voucher v WHERE v.nameUnAccent = ?1")
    Optional<Voucher> findByNameUA(String name);

    @Query("SELECT v FROM  Voucher v WHERE v.nameUnAccent = ?1 AND v.status = 1")
    Optional<Voucher> findByNameUAAndStatus(String name);

    @Query("SELECT v FROM  Voucher v WHERE v.id = ?1 AND v.status = 1")
    Optional<Voucher> findByIdAndStatus(long id);

    @Query("SELECT v FROM  Voucher v WHERE v.status = 1")
    List<Voucher> getAllByStatus();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Voucher v SET v.status = ?1 WHERE v.id = ?2" , nativeQuery = true)
    int updateVoucher(int status, long id);
}
