package com.project;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

public class MainTest {

    @Test
    public void testMain() {

        assertTrue(true);

    }

    @Test
    public void testHelloWorld() throws Exception {

        String text = tapSystemOut(() -> Main.main(new String[]{}));

        assertEquals("Hello World!", text.trim());

    }

}
