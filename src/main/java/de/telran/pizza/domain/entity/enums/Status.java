package de.telran.pizza.domain.entity.enums;

/**
 * Перечисление представляет статусы заказа в системе.
 */
public enum Status {

    /**
     * Новый заказ.
     */
    NEW,

    /**
     * Оплачен.
     */
    PAYED,

    /**
     * Подтверждение оплаты.
     */
    PAYMENT_CONFIRM,

    /**
     * Готовится.
     */
    COOKING,

    /**
     * В доставке.
     */
    DELIVERY,

    /**
     * Завершен.
     */
    DONE;

    /**
     * Массив всех статусов.
     */
    private static final Status[] statuses = values();

    /**
     * Получает следующий статус.
     *
     * @return Следующий статус после текущего.
     */
    public Status next() {
        return statuses[(this.ordinal() + 1) % statuses.length];
    }
}
