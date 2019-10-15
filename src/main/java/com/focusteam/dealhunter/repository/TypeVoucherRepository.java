package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.TypeVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeVoucherRepository extends JpaRepository<TypeVoucher, Long> {
    @Query("SELECT  t FROM TypeVoucher t WHERE t.name = ?1")
    Optional<TypeVoucher> findByName(String name);

    @Query("SELECT tv FROM TypeVoucher tv WHERE tv.nameUnAccent = ?1")
    Optional<TypeVoucher> findByNameUnAccent(String name);

    @Query("SELECT  t FROM TypeVoucher t WHERE t.name = ?1 AND t.status = 1")
    Optional<TypeVoucher> findByNameAAndStatus(String name);

    @Query("SELECT tv FROM TypeVoucher tv WHERE tv.nameUnAccent = ?1 AND tv.status = 1")
    Optional<TypeVoucher> findByNameUnAccentAndStatus(String name);

    @Query("SELECT  t FROM TypeVoucher t WHERE t.id = ?1 AND t.status = 1")
    Optional<TypeVoucher> findByIdAndStatus(long id);

    @Query("SELECT  t FROM TypeVoucher t WHERE t.status = 1")
    List<TypeVoucher> getAllByStatus();
}
