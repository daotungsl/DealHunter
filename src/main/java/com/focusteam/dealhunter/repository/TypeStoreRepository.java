package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.TypeStore;
import com.focusteam.dealhunter.entity.TypeVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeStoreRepository extends JpaRepository<TypeStore, Long> {
    @Query("SELECT  ts FROM TypeStore ts WHERE ts.name = ?1")
    Optional<TypeStore> findByName(String name);

    @Query("select ts FROM TypeStore ts WHERE ts.nameUnAccent = ?1")
    Optional<TypeStore> findByNameUnAccent(String name);
}
