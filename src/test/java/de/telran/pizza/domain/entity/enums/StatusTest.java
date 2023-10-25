package de.telran.pizza.domain.entity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {

    @Test
    void statusEnumValues_AreCorrect() {
        assertEquals("NEW", Status.NEW.name());
        assertEquals("PAYED", Status.PAYED.name());
        assertEquals("PAYMENT_CONFIRM", Status.PAYMENT_CONFIRM.name());
        assertEquals("COOKING", Status.COOKING.name());
        assertEquals("DELIVERY", Status.DELIVERY.name());
        assertEquals("DONE", Status.DONE.name());
    }
    @Test
    void testNext() {
        // Получаем массив всех статусов
        Status[] statuses = Status.values();

        // Проходим по всем статусам
        for (int i = 0; i < statuses.length; i++) {
            // Текущий статус
            Status currentStatus = statuses[i];

            // Получаем следующий статус
            Status nextStatus = currentStatus.next();

            // Следующий статус не должен быть null
            assertNotNull(nextStatus);

            // Следующий статус должен быть тем, который идет после текущего
            assertEquals(statuses[(i + 1) % statuses.length], nextStatus);
        }
    }

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