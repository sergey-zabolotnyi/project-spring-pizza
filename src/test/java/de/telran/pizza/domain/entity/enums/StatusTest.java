package de.telran.pizza.domain.entity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {

    @Test
    void next_NewStatus_ReturnsPayed() {
        Status newStatus = Status.NEW;
        assertEquals(Status.PAYED, newStatus.next());
    }

    @Test
    void next_PayedStatus_ReturnsPaymentConfirm() {
        Status payedStatus = Status.PAYED;
        assertEquals(Status.PAYMENT_CONFIRM, payedStatus.next());
    }

    @Test
    void next_PaymentConfirmStatus_ReturnsCooking() {
        Status paymentConfirmStatus = Status.PAYMENT_CONFIRM;
        assertEquals(Status.COOKING, paymentConfirmStatus.next());
    }

    @Test
    void next_CookingStatus_ReturnsDelivery() {
        Status cookingStatus = Status.COOKING;
        assertEquals(Status.DELIVERY, cookingStatus.next());
    }

    @Test
    void next_DeliveryStatus_ReturnsDone() {
        Status deliveryStatus = Status.DELIVERY;
        assertEquals(Status.DONE, deliveryStatus.next());
    }

    @Test
    void next_DoneStatus_ReturnsNew() {
        Status doneStatus = Status.DONE;
        assertEquals(Status.NEW, doneStatus.next());
    }
}