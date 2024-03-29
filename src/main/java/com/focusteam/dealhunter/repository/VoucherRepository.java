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

    @Query("SELECT v FROM Voucher v INNER JOIN v.store s WHERE s.nameUnAccent = ?1 AND v.status = 1")
    List<Voucher> getAllByStoreNameUA(String name);

    @Query("SELECT v FROM Voucher v INNER JOIN v.typeVoucher tv WHERE tv.nameUnAccent = ?1 AND v.status = 1")
    List<Voucher> getAllByTypeVoucher(String name);

    @Query("SELECT v FROM Voucher v INNER JOIN v.store s WHERE s.id = ?1")
    List<Voucher> getAllByStoreIdNoStatus(long id);

    @Query("SELECT COUNT (v) FROM Voucher v")
    Integer countAll();
}
