package com.brueschgames.budgettool.service.impl;

import com.brueschgames.budgettool.model.Payment;
import com.brueschgames.budgettool.service.PaymentService;

import java.math.BigDecimal;
import java.util.ArrayList;


public class PaymentServiceImpl implements PaymentService {


    private ArrayList<Payment> payments;

    private static PaymentServiceImpl instance;

    private PaymentServiceImpl() {
        payments = new ArrayList<>();
    }


    public static PaymentServiceImpl getInstance() {
        if(instance == null) {
            instance = new PaymentServiceImpl();
        }
        return instance;
    }


    @Override
    public boolean addPayment(Payment payment) {
        return payments.add(payment);
    }

    @Override
    public ArrayList<Payment> getAllPayments() {
        return payments;
    }

    @Override
    public boolean removePayment(Payment payment) {
        return payments.remove(payment);
    }

    @Override
    public void deleteAllEntries() {
        payments = new ArrayList<>();
    }

    @Override
    public BigDecimal getBalance() {
        BigDecimal result = BigDecimal.ZERO;
        for(Payment payment : payments) {
            result = result.add(payment.getAmount());
        }
        return result;
    }

    @Override
    public void addPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public void removePayment(int i) {
        payments.remove(i);
    }


}
