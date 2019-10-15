package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.StoreAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreAddressRepository extends JpaRepository<StoreAddress, Long> {
    @Query("SELECT sa FROM StoreAddress sa WHERE sa.id = ?1 AND sa.status = 1")
    Optional<StoreAddress> findByIdAndStatus (long status);

    @Query("SELECT sa FROM StoreAddress sa WHERE sa.status = 1")
    List<StoreAddress> getAllByStatus();
}
