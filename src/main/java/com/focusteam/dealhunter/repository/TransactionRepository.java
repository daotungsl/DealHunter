package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//    @Query("SELECT COUNT (t) FROM  Transaction t WHERE t.store_id = ?1 GROUP BY t.day")
//
//    @Query("")
//
//    List<Integer> countByStoreId(long id);

    @Query("SELECT COUNT (t) FROM Transaction t INNER JOIN t.store s WHERE s.id = ?1 group by t.day")
    List<Integer> countByStore(long id);

    @Query("SELECT COUNT (t) FROM  Transaction t INNER JOIN t.store s WHERE s.id = ?1 AND t.dayLong BETWEEN ?2 AND ?3 GROUP BY t.day")
    List<Integer> countByStoreFromTo(long id, long from, long to);
}
