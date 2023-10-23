package de.telran.pizza.domain.entity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    void roleEnumValues_AreCorrect() {
        assertEquals("ROLE_ADMIN", Role.ROLE_ADMIN.name());
        assertEquals("ROLE_MANAGER", Role.ROLE_MANAGER.name());
        assertEquals("ROLE_CUSTOMER", Role.ROLE_CUSTOMER.name());
    }
}