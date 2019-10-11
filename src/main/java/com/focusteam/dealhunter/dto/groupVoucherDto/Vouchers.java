package com.focusteam.dealhunter.dto.groupVoucherDto;

import com.focusteam.dealhunter.entity.Voucher;

import javax.validation.constraints.Max;
import java.util.List;

public class Vouchers {
    private List<Voucher> vouchers;

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
}
