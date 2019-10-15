package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.TypeStore;
import com.focusteam.dealhunter.entity.TypeVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeStoreRepository extends JpaRepository<TypeStore, Long> {
    @Query("SELECT  ts FROM TypeStore ts WHERE ts.name = ?1")
    Optional<TypeStore> findByName(String name);

    @Query("select ts FROM TypeStore ts WHERE ts.nameUnAccent = ?1")
    Optional<TypeStore> findByNameUnAccent(String name);

    @Query("SELECT  ts FROM TypeStore ts WHERE ts.name = ?1 AND ts.status = 1")
    Optional<TypeStore> findByNameAndStatus(String name);

    @Query("select ts FROM TypeStore ts WHERE ts.nameUnAccent = ?1 AND ts.status = 1")
    Optional<TypeStore> findByNameUnAccentAndStatus(String name);

    @Query("SELECT  ts FROM TypeStore ts WHERE ts.id = ?1 AND ts.status = 1")
    Optional<TypeStore> findByIdAndStatus(long id);

    @Query("SELECT  ts FROM TypeStore ts WHERE ts.status = 1")
    List<TypeStore> getAllByStatus();

    @Transactional
    @Modifying
    @Query(value = "UPDATE TypeStore ts SET ts.status = ?1 WHERE ts.id = ?2" , nativeQuery = true)
    int updateTS(int status, long id);
}
