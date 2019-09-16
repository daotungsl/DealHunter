package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.PromotionTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionTimeRepository extends JpaRepository<PromotionTime, Long> {
}
