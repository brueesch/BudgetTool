package com.brueschgames.budgettool.model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Payment {

    private long id;
    private BigDecimal amount;
    private String description;
    private Date date;


    public long getId() {
        return id;
    }

    public Payment setId(long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Payment setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Payment setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Payment setDate(Date date) {
        this.date = date;
        return this;
    }
}
