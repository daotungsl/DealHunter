package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Promotion_Time {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private long start_time;
    @NotNull
    private long end_time;
    @NotNull
    private String day_week;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id", nullable = false)
    private Voucher voucher;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getDay_week() {
        return day_week;
    }

    public void setDay_week(String day_week) {
        this.day_week = day_week;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}
