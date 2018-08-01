package com.brueschgames.budgettool.service.impl;

import com.brueschgames.budgettool.model.Payment;
import com.brueschgames.budgettool.service.PaymentService;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.*;

public class PaymentServiceImplTest {


    private PaymentService paymentService;

    @Before
    public void setUp() throws Exception {
        paymentService = PaymentServiceImpl.getInstance();
        paymentService.deleteAllEntries();
    }

    @Test
    public void testAddPayment() {
        Payment payment = buildPayment();

        boolean result = paymentService.addPayment(payment);
        assertTrue(result);
        assertEquals(1, paymentService.getAllPayments().size());
    }

    private Payment buildPayment() {
        return new Payment().setAmount(BigDecimal.valueOf(9.5)).setDate(Calendar.getInstance().getTime()).setDescription("Mittagessen").setId(0);
    }


    @Test
    public void testRemovePayment() {
        Payment payment = buildPayment();

        paymentService.addPayment(payment);
        assertEquals(1, paymentService.getAllPayments().size());


        boolean result = paymentService.removePayment(payment);

        assertTrue(result);
        assertEquals(0, paymentService.getAllPayments().size());


    }


    @Test
    public void testGetBalance() {
        Payment payment = buildPayment();
        Payment payment2 = buildPayment();

        paymentService.addPayment(payment);
        paymentService.addPayment(payment2);

        BigDecimal result = paymentService.getBalance();

        assertEquals(BigDecimal.valueOf(19.0), result);
    }



}