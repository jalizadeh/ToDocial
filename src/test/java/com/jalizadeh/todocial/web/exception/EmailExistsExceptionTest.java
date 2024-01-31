package com.jalizadeh.todocial.web.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailExistsExceptionTest {

    private final String EXCEPTION_MSG = "Custom error message";
    private final Throwable CAUSE = new RuntimeException("Root cause exception");

    @Test
    void testEmptyConstructor(){
        EmailExistsException exception1 = new EmailExistsException();
        assertNull(exception1.getMessage());
        assertNull(exception1.getCause());

        EmailExistsException e2 = assertThrows(EmailExistsException.class, () -> {
            throw new EmailExistsException();
        });
        assertNull(e2.getMessage());
        assertNull(e2.getCause());
    }

    @Test
    void testWithMessage(){
        EmailExistsException e1 = new EmailExistsException(EXCEPTION_MSG);

        assertEquals(EXCEPTION_MSG, e1.getMessage());
        assertNull(e1.getCause());

        EmailExistsException e2 = assertThrows(EmailExistsException.class, () -> {
            throw new EmailExistsException(EXCEPTION_MSG);
        });

        assertEquals(EXCEPTION_MSG, e2.getMessage());
        assertNull(e2.getCause());
    }

    @Test
    void testWithMessageAndCause(){
        EmailExistsException exception = new EmailExistsException(EXCEPTION_MSG, CAUSE);
        assertEquals(EXCEPTION_MSG, exception.getMessage());
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    void testWithCause(){
        EmailExistsException exception = new EmailExistsException(CAUSE);
        assertNull(exception.getMessage());
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    void testFullConstructor() {
        EmailExistsException exception = new EmailExistsException(EXCEPTION_MSG, CAUSE, true, true);

        assertEquals(EXCEPTION_MSG, exception.getMessage());
        assertEquals(CAUSE, exception.getCause());
    }
}