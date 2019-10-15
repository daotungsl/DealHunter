package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.City;
import com.focusteam.dealhunter.entity.TypeStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT  c FROM City c WHERE c.name = ?1")
    Optional<City> findByName(String name);

    @Query("SELECT c FROM City c WHERE c.nameUnAccent = ?1")
    Optional<City> findByNameUnAccent(String name);

    // FOR END USER CAN SELECT CITY IS ACTIVATED

    @Query("SELECT c FROM City c WHERE c.name = ?1 AND c.status = 1")
    Optional<City> findByNameAndStatus(String name);

    @Query("SELECT c FROM City c WHERE c.id = ?1 AND c.status = 1")
    Optional<City> findByIdAndStatus(long name);

    @Query("SELECT c FROM City c WHERE c.nameUnAccent = ?1 AND c.status = 1")
    Optional<City> findByNameUnAccentAndStatus(String name);

    @Query("SELECT c FROM City c WHERE c.status = 1")
    List<City> getAllByStatus();

    @Query("SELECT c FROM City c")
    List<City> getAll();

    @Transactional
    @Modifying
    @Query(value = "UPDATE City ct SET ct.status = ?1 WHERE ct.id = ?2" , nativeQuery = true)
    int updateCity(int status, long id);
}
