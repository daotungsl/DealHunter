package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.TypeStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeStoreRepository extends JpaRepository<TypeStore, Long> {
}
