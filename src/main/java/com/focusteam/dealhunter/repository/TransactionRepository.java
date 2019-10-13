package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT COUNT (t) FROM  Transaction t WHERE t.store_id = ?1 GROUP BY t.day")
    List<Integer> countByStoreId(long id);
}
