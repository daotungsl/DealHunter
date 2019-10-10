package com.focusteam.dealhunter.repository;

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

    @Query("SELECT v FROM Store s INNER JOIN s.vouchers v WHERE s.nameUnAccent = ?1")
    List<Optional<Voucher>> findVoucherByStoreNameUA(String name);
}
