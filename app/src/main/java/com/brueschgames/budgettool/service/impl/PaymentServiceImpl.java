package com.brueschgames.budgettool.service.impl;

import com.brueschgames.budgettool.model.Payment;
import com.brueschgames.budgettool.service.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;


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

    @Override
    public BigDecimal getAmountPerDay() {
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int dayEndOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        int divider = dayEndOfMonth - dayOfMonth + 1;

        return getBalance().divide(BigDecimal.valueOf(divider), BigDecimal.ROUND_UP);
    }


}
