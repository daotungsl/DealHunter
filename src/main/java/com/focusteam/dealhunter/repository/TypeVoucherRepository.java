package com.focusteam.dealhunter.repository;

import com.focusteam.dealhunter.entity.TypeVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeVoucherRepository extends JpaRepository<TypeVoucher, Long> {
}
