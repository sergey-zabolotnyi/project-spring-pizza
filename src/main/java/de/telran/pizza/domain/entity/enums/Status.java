package de.telran.pizza.domain.entity.enums;

/**
 * Enum representing the status of an order.
 */
public enum Status {
    /**
     * The order is new and has not been processed yet.
     */
    NEW,

    /**
     * The order has been paid for.
     */
    PAYED,

    /**
     * Payment for the order has been confirmed.
     */
    PAYMENT_CONFIRM,

    /**
     * The order is currently being prepared or cooked.
     */
    COOKING,

    /**
     * The order is out for delivery.
     */
    DELIVERY,

    /**
     * The order has been successfully completed.
     */
    DONE;

    /**
     * An array containing all possible order statuses.
     */
    private static final Status[] statuses = values();

    /**
     * Get the next status in the order lifecycle.
     *
     * @return The next status.
     */
    public Status next() {
        return statuses[(this.ordinal() + 1) % statuses.length];
    }
}
