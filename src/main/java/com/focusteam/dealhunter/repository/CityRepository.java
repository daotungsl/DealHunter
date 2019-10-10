package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.City;
import com.focusteam.dealhunter.entity.TypeStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT  c FROM City c WHERE c.name = ?1")
    Optional<City> findByName(String name);

    @Query("SELECT c FROM City c WHERE c.nameUnAccent = ?1")
    Optional<City> findByNameUnAccent(String name);
}
