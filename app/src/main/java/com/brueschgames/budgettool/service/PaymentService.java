package com.brueschgames.budgettool.service;

import com.brueschgames.budgettool.model.Payment;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface PaymentService {


    boolean addPayment(Payment payment);

    ArrayList<Payment> getAllPayments();

    boolean removePayment(Payment payment);

    void deleteAllEntries();

    BigDecimal getBalance();

    void addPayments(ArrayList<Payment> payments);

    void removePayment(int i);

    BigDecimal getAmountPerDay();
}
